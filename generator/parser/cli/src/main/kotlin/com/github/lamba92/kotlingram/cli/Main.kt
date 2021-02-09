package com.github.lamba92.kotlingram.cli

import com.github.lamba92.kotlingram.parser.generateFiles
import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import java.io.File


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

    val telegramClientPackage by parser.option(
        ArgType.String,
        shortName = "tp",
        description = "Dot separated package namespace"
    ).default("com.github.lamba92.telegram.api.data")

    parser.parse(args)

    generateFiles(outputFolderPath.absoluteFile, packages, telegramClientPackage)

}


