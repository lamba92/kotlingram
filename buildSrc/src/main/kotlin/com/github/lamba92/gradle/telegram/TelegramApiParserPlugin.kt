package com.github.lamba92.gradle.telegram

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.*
import java.io.File
import com.github.lamba92.gradle.telegram.parser.*
import org.gradle.plugins.ide.idea.model.IdeaModel
import org.jetbrains.gradle.ext.IdeaExtPlugin

open class TelegramApiParserPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val e = extensions.create<TelegramApiParserExtension>("telegramApiParser")
        val t = tasks.register<GenerateTelegramApiTask>("generateTelegramApi") {
            group = "telegram"
            packageName = e.packageName
            outputDirectory = e.outputDirectory
        }
//        apply<IdeaExtPlugin>()
//        the<IdeaModel>()
    }
}

open class TelegramApiParserExtension(objects: ObjectFactory) {

    var packageName by objects.property<String>()
        .apply { set("com.github.lamba92.telegram.api.generated.data") }

    @Suppress("UnstableApiUsage")
    var outputDirectory by objects.property<File>()
        .apply { set(File(".")) }

}

open class GenerateTelegramApiTask : DefaultTask() {

    @get:Input
    var packageName by project.objects.property<String>()

    @Suppress("UnstableApiUsage")
    @get:OutputDirectory
    var outputDirectory by project.objects.property<File>()

    @TaskAction
    fun generate() =
        generateFiles(outputDirectory, packageName)

}
