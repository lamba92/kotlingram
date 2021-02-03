package com.github.lamba92.gradle.telegram.parser

import it.skrape.core.fetcher.HttpFetcher
import it.skrape.core.htmlDocument
import it.skrape.extract
import it.skrape.selects.DocElement
import it.skrape.selects.html5.*
import it.skrape.skrape
import java.io.File
import java.nio.file.Paths

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

val inputMediaTypes = listOf(
    "InputMediaAnimation",
    "InputMediaDocument",
    "InputMediaAudio",
    "InputMediaPhoto",
    "InputMediaVideo"
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

val responseTypeRegexes = listOf(
    Regex("An (.*) objects is returned"),
    Regex("Returns (\\w*) on success"),
    Regex("[Rr]eturns an? (.*?) object"),
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
    Regex("Returns the new invite link as (String) on success"),
    Regex("Returns (.*) on success"),
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
    "InputFile or String", "InputFile" -> "String"
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

val falsePositiveMethodWithoutBodyTitles = listOf(
    "InputMedia",
    "InputFile",
    "Sending files",
    "Inline mode objects",
    "Formatting options",
    "Inline mode methods",
    "InlineQueryResult",
    "InputMessageContent",
    "PassportElementError"
)

fun Sequence<DocElement>.filterMethodsWithoutBody() = sequence {
    var startFound = false
    val buffer = mutableListOf<DocElement>()
    forEach { currentElement ->
        if (currentElement.tagName == "h3" && currentElement.text == "Getting updates")
            startFound = true
        val tagNames = buffer.map { it.tagName }
        if (currentElement.tagName == "h4" && "h4" in tagNames) {
            if ("table" !in tagNames)
                yield(buffer.subList(buffer.indexOfLast { it.tagName == "h4" }, buffer.size).toList())
            buffer.clear()
        }
        if (startFound)
            when (currentElement.tagName) {
                "h4" -> if (currentElement.text.first().isLowerCase()) buffer.add(currentElement)
                else -> buffer.add(currentElement)
            }
    }
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

fun List<DocElement>.asTelegramMethodWithBody(): TelegramMethod {
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

fun List<DocElement>.asTelegramMethodWithoutBody(): TelegramMethod {
    val description = filter { it.tagName == "p" }
        .joinToString("\n") { it.text }

    val name = first { it.tagName == "h4" }.text

    return TelegramMethod(name, emptyList(), description)
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
    telegramClientPackage: String = "",
) = buildString {
    if (packageName.isNotEmpty())
        appendLine("package $packageName")
    appendLine()
    appendLine("import kotlinx.serialization.SerialName")
    appendLine("import kotlinx.serialization.Serializable")
    if (telegramClientPackage.isNotEmpty())
        appendLine("import $telegramClientPackage.*")
    appendLine()
    appendLine()
    data.forEach {
        appendLine(it.generateSourceCode())
    }
}

fun generateRequestWithBodyFile(
    data: List<TelegramMethod>,
    packageName: String = "",
    telegramClientPackage: String = "",
) = buildString {
    if (packageName.isNotEmpty())
        appendLine("package $packageName")
    appendLine()
    appendLine("import kotlinx.serialization.SerialName")
    appendLine("import kotlinx.serialization.Serializable")
    appendLine("import io.ktor.client.request.*")
    appendLine("import io.ktor.http.*")
    if (telegramClientPackage.isNotEmpty())
        appendLine("import $telegramClientPackage.TelegramBotApiClient")
    appendLine("import $telegramClientPackage.*")
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
        .filterNot { it.name in passportErrorTypes }
        .toList()
        .let { File(finalFolder, "Data.kt").writeText(generateDataFile(it, packages, telegramClientPackage)) }

    val methods = docs.filterTables()
        .filterMethodsWithBody()
        .map { it.asTelegramMethodWithBody() }
        .toList() + docs.filterMethodsWithoutBody()
        .map { it.asTelegramMethodWithoutBody() }
        .toList()

    File(finalFolder, "Methods.kt").writeText(generateRequestWithBodyFile(methods, packages, telegramClientPackage))

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
    })

}
