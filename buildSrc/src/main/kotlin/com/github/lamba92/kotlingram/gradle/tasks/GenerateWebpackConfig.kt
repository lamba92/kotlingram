package com.github.lamba92.kotlingram.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.*
import java.io.File

open class GenerateWebpackConfig : DefaultTask() {

    enum class Target {
        NODE, BROWSER
    }

    enum class Mode {
        PRODUCTION, DEVELOPMENT
    }

    private val template = """
        module.exports = [{
            mode: '%%%MODE%%%',
            name: 'server',
            entry: '%%%ENTRY%%%',
            target: '%%%Mode%%%', // <-- importat part!
            output: {
                path: '%%%OUTPUT_PATH%%%',
                filename: '%%%OUTPUT_NAME%%%',
            },
            resolve: {
                modules: [%%%MODULES_FOLDER%%%]
            }
        }];
    """.trimIndent()

    @get:InputFile
    var entryFile by project.objects.property<File>()

    @get:Input
    var target: Target = Target.NODE

    @get:Input
    var mode: Mode = Mode.DEVELOPMENT

    @get:Input
    var outputBundleFolder by project.objects.property<String>()

    @get:Input
    var outputBundleName by project.objects.property<String>()

    @get:InputFiles
    var modulesFolder = project.objects.listProperty(File::class)

    @get:OutputFile
    var outputConfig by project.objects.property<File>()

    init {
        with(project) {
            outputBundleFolder = file("$buildDir\\bundle").absolutePath
            outputBundleName = "bundle.js"
            modulesFolder.set(listOf(file("node_modules")))
            outputConfig = file("$buildDir/config/webpack.config.js")
        }
    }

    @TaskAction
    fun buildFile() {
        outputConfig.writeText(
            template.replace("%%%ENTRY%%%", entryFile.absolutePath.replace("\\", "\\\\"))
                .replace("%%%Mode%%%", target.name.toLowerCase())
                .replace("%%%OUTPUT_PATH%%%", outputBundleFolder.replace("\\", "\\\\"))
                .replace("%%%OUTPUT_NAME%%%", outputBundleName.appendIfMissing(".js"))
                .replace("%%%MODE%%%", mode.name.toLowerCase())
                .replace(
                    "%%%MODULES_FOLDER%%%",
                    modulesFolder.get().joinToString(",") { "'${it.absolutePath.replace("\\", "\\\\")}'" }
                )
        )
    }
}

fun String.appendIfMissing(s: String) =
    if (endsWith(s)) this else this + s
