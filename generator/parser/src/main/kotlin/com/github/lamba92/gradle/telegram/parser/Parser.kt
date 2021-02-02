package com.github.lamba92.gradle.telegram.parser

import it.skrape.core.fetcher.HttpFetcher
import it.skrape.core.htmlDocument
import it.skrape.extract
import it.skrape.selects.DocElement
import it.skrape.selects.html5.*
import it.skrape.skrape
import java.io.File
import java.nio.file.Paths

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

data class TelegramMethodWithBody(val name: String, val parameters: List<TelegramParameter>, val description: String)

val inlineQueryResultTypes = listOf(
    "InlineQueryResultCachedAudio",
    "InlineQueryResultCachedDocument",
    "InlineQueryResultCachedGif",
    "InlineQueryResultCachedMpeg4Gif",
    "InlineQueryResultCachedPhoto",
    "InlineQueryResultCachedSticker",
    "InlineQueryResultCachedVideo",
    "InlineQueryResultCachedVoice",
    "InlineQueryResultArticle",
    "InlineQueryResultAudio",
    "InlineQueryResultContact",
    "InlineQueryResultGame",
    "InlineQueryResultDocument",
    "InlineQueryResultGif",
    "InlineQueryResultLocation",
    "InlineQueryResultMpeg4Gif",
    "InlineQueryResultPhoto",
    "InlineQueryResultVenue",
    "InlineQueryResultVideo",
    "InlineQueryResultVoice"
)

val passportErrorTypes = listOf(
    "PassportElementErrorDataField",
    "PassportElementErrorFrontSide",
    "PassportElementErrorReverseSide",
    "PassportElementErrorSelfie",
    "PassportElementErrorFile",
    "PassportElementErrorFiles",
    "PassportElementErrorTranslationFile",
    "PassportElementErrorTranslationFiles",
    "PassportElementErrorUnspecified"
)

fun TelegramObject.generateSourceCode() = buildString {
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
    if (inlineQueryResultTypes.any { it in name })
        append(" : InlineQueryResult()")
    if (passportErrorTypes.any { it in name })
        append(" : PassportElementError()")
    if (listOf("InlineKeyboardMarkup", "ReplyKeyboardMarkup", "ReplyKeyboardRemove", "ForceReply").any { it in name })
        append(" : ReplyMarkup")
    if (listOf("InputMediaAudio", "InputMediaDocument", "InputMediaPhoto", "InputMediaVideo").any { it in name })
        append(" : InputMedia()")
    appendLine()
}

fun TelegramMethodWithBody.generateSourceCode() = buildString {
    if (parameters.isNotEmpty()) {
        appendLine("/**")
        appendLine(" * Request body for [$name]")
        parameters.filter { it.description.isNotEmpty() }.forEach {
            appendLine(" * @param ${it.name.toCamelCase()} ${it.description}")
        }
        appendLine(" */")
        appendLine("@Serializable")
        appendLine("data class ${name.capitalize()}Request(")
        parameters.forEachIndexed { index, field ->
            append("\t")
            if ("_" in field.name)
                append("@SerialName(\"${field.name}\") ")

            append("val ${field.name.toCamelCase()}: ")
            val isOptional = field.required == "Optional"
            val arrayCount = field.type.split(" ").count { it == "Array" || it == "array" }
            val type = when {
                arrayCount > 0 -> ("List<" * arrayCount) + resolveType(field.type.removePrefixRecursive("Array of ")
                    .removePrefixRecursive("array of"),
                    false) + (">" * arrayCount) + " = emptyList()"
                else -> resolveType(field.type, isOptional)
            }
            append(type)
            if (index != parameters.lastIndex)
                append(",")
            appendLine()
        }
        appendLine(")")
    }
    appendLine()
    val responseType = findResponseTypeFromDescription(description)
    val arrayCount = responseType.split(" ").count { it == "Array" || it == "array" }
    val type = when {
        arrayCount > 0 -> ("List<" * arrayCount) + resolveType(responseType.removePrefixRecursive("Array of ")
            .removePrefixRecursive("array of "),
            false).removeSuffix("s") + (">" * arrayCount)
        else -> resolveType(responseType, false)
    }

    appendLine("/**")
    description.split("\n").forEach {
        appendLine(" * $it")
    }
    appendLine(" */")
    append("suspend fun TelegramBotApiClient.$name(")
    appendLine("body: ${name.capitalize()}Request): TelegramResponse<$type> {")
    appendLine("    TODO()")
    appendLine("}")
    appendLine()
    appendLine()
}

val responseTypeRegexes = listOf(
    Regex("An (.*) objects is returned"),
    Regex("Returns (\\w*) on success"),
    Regex("[Rr]eturns an? (.*) object"),
    Regex("in form of a (\\w*) object"),
    Regex("[Oo]n success, (\\w*) is returned"),
    Regex("the sent (\\w*) is returned"),
    Regex("[Oo]n success, an? (.*) objects?"),
    Regex("On success, an (.*) that were"),
    Regex("On success, the stopped (\\w*) with the final results is returned"),
    Regex("the \\w* (\\w*) is returned"),
    Regex("Returns the (\\w*) of the"),
    Regex("returns the edited (\\w*),"),
    Regex("Returns the uploaded (\\w*) on success"),
    Regex("as (\\w*) on success"),
)

fun findResponseTypeFromDescription(description: String): String {
    responseTypeRegexes.forEach { regex ->
        regex.find(description)?.groupValues?.get(1)?.let { return it }
    }
    error("Type not found in description:\n____\n${description}\n____")
}

fun resolveType(typeString: String, isOptional: Boolean) = when (typeString) {
    "Integer", "String or Integer", "Integer or String" -> "Int"
    "Float number" -> "Float"
    "True" -> "Boolean"
    "Media" -> "InputMedia"
    "False" -> "Boolean"
    "InputFile or String", "InputFile", "File" -> "String"
    "InlineKeyboardMarkup or ReplyKeyboardMarkup or ReplyKeyboardRemove or ForceReply" -> "ReplyMarkup"
    "InputMediaAudio, InputMediaDocument, InputMediaPhoto and InputMediaVideo" -> "InputMedia"
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

fun Sequence<List<DocElement>>.filterMethodsWithBody() = filter {
    it.first { it.tagName == "table" }.thead {
        tr {
            th {
                findFirst { text == "Parameter" }
            }
        }
    } && it.first { it.tagName == "h4" }.text.first().isLowerCase()
}

fun List<DocElement>.asTelegramMethod(): TelegramMethodWithBody {
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

    return TelegramMethodWithBody(name, tableLines, description)
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

fun generateDataFile(
    data: List<TelegramObject>,
    packageName: String = "",
) = buildString {
    if (packageName.isNotEmpty())
        appendLine("package $packageName")
    appendLine()
    appendLine("import kotlinx.serialization.SerialName")
    appendLine("import kotlinx.serialization.Serializable")
    appendLine()
    appendLine()
    data.forEach {
        appendLine(it.generateSourceCode())
    }
}

fun generateRequestWithBodyFile(
    data: List<TelegramMethodWithBody>,
    packageName: String = "",
    telegramClientPackage: String = "",
) = buildString {
    if (packageName.isNotEmpty())
        appendLine("package $packageName")
    appendLine()
    appendLine("import kotlinx.serialization.SerialName")
    appendLine("import kotlinx.serialization.Serializable")
    if (telegramClientPackage.isNotEmpty())
        appendLine("import $telegramClientPackage.TelegramBotApiClient")
    appendLine("import $telegramClientPackage.TelegramResponse")
    appendLine()
    appendLine()
    data.forEach {
        appendLine(it.generateSourceCode())
    }
}

fun generateFiles(outputFolderPath: File, packages: String, telegramClientPackage: String) {

    val finalFolder = outputFolderPath.absoluteFile.toPath()
        .resolve(Paths.get(packages.split(".").joinToString("/")))
        .normalize()
        .toFile()

    finalFolder.mkdirs()

    val docs = skrape(HttpFetcher) {
        request {
            url = "https://core.telegram.org/bots/api"
        }
        extract {
            htmlDocument {
                div {
                    withId = "dev_page_content"
                    findFirst { this }
                }
            }
        }
    }.element.children()
        .asSequence()
        .map { DocElement(it) }

    docs.filterTables()
        .filterObjects()
        .map { it.asTelegramObject() }
        .toList()
        .let { File(finalFolder, "Data.kt").writeText(generateDataFile(it, packages)) }

    docs.filterTables()
        .filterMethodsWithBody()
        .map { it.asTelegramMethod() }
        .toList()
        .let {
            File(finalFolder, "MethodsWithBody.kt")
                .writeText(generateRequestWithBodyFile(it, packages, telegramClientPackage))
        }


    File(finalFolder, "Data.kt").appendText(buildString {
        appendLine()
        appendLine(generateInputMessageContentInterfaceFix())
        appendLine()
        appendLine(generateCallbackGameFix())
        appendLine()
        appendLine(generateReplyMarkupFix())
        appendLine()
        appendLine(generateMediaFix())
        appendLine()
        appendLine(generateInlineQueryResultFix())
        appendLine()
        appendLine(generatePassportElementErrorFix())
    })

}

fun generateInputMessageContentInterfaceFix() = buildString {
    appendLine("/**")
    appendLine(" * This object represents the content of a message to be sent as a result of an inline query. " +
        "Telegram clients currently support the following 4 types:")
    appendLine(" * - [InputTextMessageContent]")
    appendLine(" * - [InputLocationMessageContent]")
    appendLine(" * - [InputVenueMessageContent]")
    appendLine(" * - [InputContactMessageContent]")
    appendLine(" */ ")
    appendLine("@Serializable")
    appendLine("sealed class InputMessageContent")
}

fun generateCallbackGameFix() = buildString {
    appendLine("/**")
    appendLine(" * A placeholder, currently holds no information. Use BotFather to set up your game.")
    appendLine(" */ ")
    appendLine("typealias CallbackGame = String")
}

fun generateReplyMarkupFix() = buildString {
    appendLine("/**")
    appendLine(" * ")
    appendLine(" */ ")
    appendLine("interface ReplyMarkup")
}

fun generateMediaFix() = buildString {
    appendLine("/**")
    appendLine(" * ")
    appendLine(" */ ")
    appendLine("@Serializable")
    appendLine("sealed class InputMedia")
}

fun generateInlineQueryResultFix() = buildString {
    appendLine("/**")
    appendLine(" * This object represents one result of an inline query. Telegram clients currently support results of the following 20 types:")
    appendLine(" * [InlineQueryResultCachedAudio]")
    appendLine(" * [InlineQueryResultCachedDocument]")
    appendLine(" * [InlineQueryResultCachedGif]")
    appendLine(" * [InlineQueryResultCachedMpeg4Gif]")
    appendLine(" * [InlineQueryResultCachedPhoto]")
    appendLine(" * [InlineQueryResultCachedSticker]")
    appendLine(" * [InlineQueryResultCachedVideo]")
    appendLine(" * [InlineQueryResultCachedVoice]")
    appendLine(" * [InlineQueryResultArticle]")
    appendLine(" * [InlineQueryResultAudio]")
    appendLine(" * [InlineQueryResultContact]")
    appendLine(" * [InlineQueryResultGame]")
    appendLine(" * [InlineQueryResultDocument]")
    appendLine(" * [InlineQueryResultGif]")
    appendLine(" * [InlineQueryResultLocation]")
    appendLine(" * [InlineQueryResultMpeg4Gif]")
    appendLine(" * [InlineQueryResultPhoto]")
    appendLine(" * [InlineQueryResultVenue]")
    appendLine(" * [InlineQueryResultVideo]")
    appendLine(" * [InlineQueryResultVoice]")
    appendLine(" */ ")
    appendLine("@Serializable")
    appendLine("sealed class InlineQueryResult")
}

fun generatePassportElementErrorFix() = buildString {
    appendLine("/**")
    appendLine(" * This object represents an error in the Telegram Passport element which was submitted that should be resolved by the user. It should be one of::")
    appendLine(" * - [PassportElementErrorDataField]")
    appendLine(" * - [PassportElementErrorFrontSide]")
    appendLine(" * - [PassportElementErrorReverseSide]")
    appendLine(" * - [PassportElementErrorSelfie]")
    appendLine(" * - [PassportElementErrorFile]")
    appendLine(" * - [PassportElementErrorFiles]")
    appendLine(" * - [PassportElementErrorTranslationFile]")
    appendLine(" * - [PassportElementErrorTranslationFiles]")
    appendLine(" * - [PassportElementErrorUnspecified]")
    appendLine(" */ ")
    appendLine("@Serializable")
    appendLine("sealed class PassportElementError")
}
