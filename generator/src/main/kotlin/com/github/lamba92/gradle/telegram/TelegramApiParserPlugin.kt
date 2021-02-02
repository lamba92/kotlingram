package com.github.lamba92.gradle.telegram

import com.github.lamba92.gradle.telegram.parser.generateFiles
import org.gradle.api.*
import org.gradle.api.model.ObjectFactory
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.*
import org.jetbrains.gradle.ext.RunConfiguration
import java.io.File

open class TelegramApiParserPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        val e = extensions.create<TelegramApiParserExtension>("telegramApiParser")
        tasks.register<GenerateTelegramApiTask>("generateTelegramApi") {
            group = "telegram"
            packageName = e.packageName
            outputDirectory = e.outputDirectory
            telegramClientPackage = e.telegramClientPackage
        }
    }
}

open class TelegramApiParserExtension(objects: ObjectFactory) {

    var packageName by objects.property<String>()
        .apply { set("") }

    var telegramClientPackage by objects.property<String>()
        .apply { set("") }

    @Suppress("UnstableApiUsage")
    var outputDirectory by objects.property<File>()
        .apply { set(File(".")) }

}

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

fun org.jetbrains.gradle.ext.ProjectSettings.codeStyle(
    configuration: org.jetbrains.gradle.ext.CodeStyleConfig.() -> Unit
) =
    (this as ExtensionAware).configure(configuration)

fun org.jetbrains.gradle.ext.ProjectSettings.copyright(
    configuration: org.jetbrains.gradle.ext.CopyrightConfiguration.() -> Unit
) =
    (this as ExtensionAware).configure(configuration)

fun org.jetbrains.gradle.ext.ProjectSettings.taskTriggers(
    configuration: org.jetbrains.gradle.ext.TaskTriggersConfig.() -> Unit
) =
    (this as ExtensionAware).configure(configuration)

fun org.jetbrains.gradle.ext.ProjectSettings.delegateActions(
    configuration: org.jetbrains.gradle.ext.ActionDelegationConfig.() -> Unit
) =
    (this as ExtensionAware).configure(configuration)

fun org.jetbrains.gradle.ext.ProjectSettings.encodings(
    configuration: org.jetbrains.gradle.ext.EncodingConfiguration.() -> Unit
) =
    (this as ExtensionAware).configure(configuration)

fun org.jetbrains.gradle.ext.ProjectSettings.runConfigurations(
    configuration: PolymorphicDomainObjectContainer<RunConfiguration>.() -> Unit
) =
    (this as ExtensionAware).configure<NamedDomainObjectContainer<RunConfiguration>> {
        (this as PolymorphicDomainObjectContainer<RunConfiguration>).apply(configuration)
    }

fun org.gradle.plugins.ide.idea.model.IdeaProject.settings(
    configuration: org.jetbrains.gradle.ext.ProjectSettings.() -> Unit
) =
    (this as ExtensionAware).configure(configuration)
