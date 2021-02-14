package com.github.lamba92.kotlingram.gradle.tasks

import com.github.lamba92.kotlingram.gradle.appendIfMissing
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.*
import java.io.File
import java.io.Serializable

open class GenerateWebpackConfig : DefaultTask() {

    sealed class ResolveFallback : Serializable {
        abstract val moduleName: String

        data class ModuleFallback(
            override val moduleName: String,
            val resolveModuleName: String
        ) : ResolveFallback(), Serializable

        data class NoFallback(override val moduleName: String) : ResolveFallback(), Serializable
    }

    enum class Target {
        NODE, WEB
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
                modules: [%%%MODULES_FOLDER%%%],
                fallback: {
                    %%%FALLBACKS%%%
                }
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

    @get:Input
    var fallbacks = project.objects.listProperty(ResolveFallback::class)

    init {
        with(project) {
            outputBundleFolder = file("$buildDir\\bundle").absolutePath
            outputBundleName = "bundle.js"
            modulesFolder.set(listOf(file("node_modules")))
            outputConfig = file("$buildDir/config/webpack.config.js")
            fallbacks.set(emptyList())
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
                .replace("%%%FALLBACKS%%%", buildString {
                    fallbacks.get().forEachIndexed { index, f: ResolveFallback ->
                        when (f) {
                            is ResolveFallback.ModuleFallback ->
                                append("'${f.moduleName}': require.resolve('${f.resolveModuleName}')")
                            is ResolveFallback.NoFallback ->
                                append("'${f.moduleName}': false")
                        }
                        if (index != fallbacks.get().lastIndex)
                            append(",").append("\n            ")
                    }
                })
        )
    }
}
