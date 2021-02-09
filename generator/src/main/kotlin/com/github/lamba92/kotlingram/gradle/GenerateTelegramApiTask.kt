package com.github.lamba92.kotlingram.gradle

import com.github.lamba92.kotlingram.parser.generateFiles
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.setValue
import java.io.File

open class GenerateTelegramApiTask : DefaultTask() {

    @get:Input
    var packageName by project.objects.property<String>()

    @get:Input
    var telegramClientPackage by project.objects.property<String>()

    @Suppress("UnstableApiUsage")
    @get:OutputDirectory
    var outputDirectory by project.objects.property<File>()

    @TaskAction
    fun generate() =
        generateFiles(outputDirectory, packageName, telegramClientPackage)

}
