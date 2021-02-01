package com.github.lamba92.gradle.telegram.parser

import it.skrape.selects.DocElement
import it.skrape.selects.html5.*
import kotlinx.cli.ArgType
import java.io.File

data class TelegramField(val name: String, val type: String, val description: String)

data class TelegramObject(val name: String, val fields: List<TelegramField>, val description: String) {
    init {
        val counting =
            fields.groupBy { it.name }
                .filter { (_, v) -> v.size > 1 }

        require(counting.isEmpty()) {
            buildString {
                appendLine("Following names are duplicated for object $name:")
                counting.forEach { (k, _) ->
                    appendLine(" - $k")
                }
            }

        }

    }
}

data class TelegramParameter(val name: String, val type: String, val required: String, val description: String)

data class TelegramMethod(val name: String, val parameters: List<TelegramParameter>, val description: String)

fun TelegramObject.asDataClassSourceCode() = buildString {
    appendLine("/**")
    description.split("\n").forEach {
        appendLine(" * $it")
    }
    fields.filter { it.description.isNotEmpty() }.forEach {
        appendLine(" * @param ${it.name.toCamelCase()} ${it.description}")
    }
    appendLine(" */")
    appendLine("@Serializable")
    appendLine("data class $name(")
    fields.forEachIndexed { index, field ->
        append("\t")
        if ("_" in field.name)
            append("@SerialName(\"${field.name}\") ")

        append("val ${field.name.toCamelCase()}: ")
        val isOptional = field.description.startsWith("Optional")
        val arrayCount = field.type.split(" ").count { it == "Array" }
        val type = when {
            arrayCount > 0 -> ("List<" * arrayCount) + resolveType(field.type.removePrefixRecursive("Array of "),
                false) + (">" * arrayCount) + " = emptyList()"
            else -> resolveType(field.type, isOptional)
        }
        append(type)
        if (index != fields.lastIndex)
            append(",")
        appendLine()
    }
    append(")")
    if (name.startsWith("Input") && name.endsWith("Content"))
        append(" : InputMessageContent()")
    appendLine()
}

fun resolveType(typeString: String, isOptional: Boolean) = when (typeString) {
    "Integer" -> "Int"
    "Float number" -> "Float"
    "True" -> "Boolean"
    "False" -> "Boolean"
    "InputFile or String" -> "String"
    else -> typeString
} + if (isOptional) "? = null" else ""

operator fun String.times(repetitions: Int) = buildString {
    repeat(repetitions) {
        append(this@times)
    }
}

tailrec fun String.removePrefixRecursive(prefix: String): String =
    if (!startsWith(prefix))
        this
    else
        substring(prefix.length).removePrefixRecursive(prefix)

fun Sequence<DocElement>.filterTables() = sequence {
    val buffer = mutableListOf<DocElement>()
    forEach { currentElement ->
        val tagNames = buffer.map { it.tagName }
        if (currentElement.tagName == "h4" && "h4" in tagNames && "table" in tagNames) {
            val tableIndex = buffer.indexOfFirst { it.tagName == "table" }
            val h4Index = buffer.dropLast(buffer.lastIndex - tableIndex).indexOfLast { it.tagName == "h4" }
            yield(buffer.subList(h4Index, buffer.size).toList())
            buffer.clear()
        }
        buffer.add(currentElement)
    }
    val tagNames = buffer.map { it.tagName }
    if ("h4" in tagNames && "table" in tagNames)
        yield(buffer.toList())
}

fun Sequence<List<DocElement>>.filterObjects() = filter {
    it.first { it.tagName == "table" }.thead {
        tr {
            th {
                findFirst { text == "Field" }
            }
        }
    } && it.first { it.tagName == "h4" }.text.first().isUpperCase()
}

fun Sequence<List<DocElement>>.filterMethods() = filter {
    it.first { it.tagName == "table" }.thead {
        tr {
            th {
                findFirst { text == "Parameter" }
            }
        }
    } && it.first { it.tagName == "h4" }.text.first().isLowerCase()
}

fun List<DocElement>.asTelegramMethod(): TelegramMethod {
    val tableLines = first { it.tagName == "table" }.tbody {
        tr {
            findAll {
                map {
                    it.td {
                        findAll {
                            TelegramParameter(get(0).text, get(1).text, get(2).text, get(3).text)
                        }
                    }
                }
            }
        }
    }
    val description = filter { it.tagName == "p" || it.tagName == "blockquote" }
        .joinToString("\n") { it.text }

    val name = first { it.tagName == "h4" }.text

    return TelegramMethod(name, tableLines, description)
}

fun List<DocElement>.asTelegramObject(): TelegramObject {
    val tableLines = first { it.tagName == "table" }.tbody {
        tr {
            findAll {
                map {
                    it.td {
                        findAll {
                            TelegramField(get(0).text, get(1).text, get(2).text)
                        }
                    }
                }
            }
        }
    }
    val description = filter { it.tagName == "p" || it.tagName == "blockquote" }
        .joinToString("\n") { it.text }

    val name = first { it.tagName == "h4" }.text

    return TelegramObject(name, tableLines, description)
}

fun String.toCamelCase() =
    toLowerCase()
        .split("_")
        .let { it.first() + it.drop(1).joinToString("") { it.capitalize() } }

fun generateKotlinCode(
    data: List<TelegramObject>,
    packageName: String = "package com.github.lamba92.telegram.api.generated.data"
) = buildString {
    append("package ")
    appendLine(packageName)
    appendLine()
    appendLine("import kotlinx.serialization.SerialName")
    appendLine("import kotlinx.serialization.Serializable")
    appendLine()
    appendLine()
    data.forEach {
        appendLine(it.asDataClassSourceCode())
    }
}

fun generateKotlinCode(
    data: TelegramObject,
    packageName: String = "package com.github.lamba92.telegram.api.generated.data"
) = buildString {
    append("package ")
    appendLine(packageName)
    appendLine()
    appendLine("import kotlinx.serialization.SerialName")
    appendLine("import kotlinx.serialization.Serializable")
    appendLine()
    appendLine()
    appendLine(data.asDataClassSourceCode())
}

object FolderArgType : ArgType<File>(false) {
    override val description = "{ String }"

    override fun convert(value: kotlin.String, name: kotlin.String) =
        File(value).also {
            if (it.exists())
                require(it.isDirectory) { "Provided $value is a file" }
        }

}

fun StringBuilder.appendLine(): StringBuilder =
    append("\n")

fun StringBuilder.appendLine(value: Any): StringBuilder =
    append(value).append("\n")
