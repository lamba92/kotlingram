package com.github.lamba92.gradle.telegram.parser

import it.skrape.core.fetcher.HttpFetcher
import it.skrape.core.htmlDocument
import it.skrape.extract
import it.skrape.selects.DocElement
import it.skrape.selects.html5.div
import it.skrape.skrape
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import java.io.File
import java.nio.file.Paths


fun main(args: Array<String>) {

    val parser = ArgParser("TelegramApiParser")

    val outputFolderPath by parser.option(
        FolderArgType,
        shortName = "o",
        description = "Output folder for generated APIs"
    ).default(File(".").absoluteFile)

    val packages by parser.option(
        ArgType.String,
        shortName = "p",
        description = "Dot separated package namespace"
    ).default("com.github.lamba92.telegram.api.generated.data")

    parser.parse(args)

    generateFiles(outputFolderPath.absoluteFile, packages)

}

fun generateFiles(outputFolderPath: File, packages: String) {

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
        .filterTables()

    docs.filterObjects()
        .map { it.asTelegramObject() }
        .toList()
        .let { File(finalFolder, "Data.kt").writeText(generateKotlinCode(it, packages)) }

    File(finalFolder, "Data.kt").appendText(buildString {
        appendLine()
        appendLine(generateInputMessageContentInterfaceFix())
        appendLine()
        appendLine(generateCallbackGameFix())
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
