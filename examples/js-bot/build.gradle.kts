import com.github.gradle.node.task.NodeTask
import com.github.lamba92.kotlingram.gradle.tasks.outputBundleFile
import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig
import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig.Mode
import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig.Mode.*
import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig.Target.*
import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig.TerserPluginSettings
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrLink
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.RootPackageJsonTask
import java.util.*

plugins {
    kotlin("js")
    id("com.github.node-gradle.node")
}

kotlin {
    js(IR) {
        nodejs()
        binaries.executable()
    }
    sourceSets {
        all {
            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalStdlibApi")
            languageSettings.useExperimentalAnnotation("kotlin.time.ExperimentalTime")
        }
    }
}

repositories {
    mavenCentral()
    jcenter()
    maven("https://kotlin.bintray.com/kotlinx")
}

dependencies {

    val ktorVersion: String by project
    val nodejsDeclarationsVersion: String by project
    val webpackVersion: String by project
    val webpackCliVersion: String by project
    val ttyVersion: String by project
    val terserVersion: String by project

    implementation(project(":api:kotlingram-bot-builder"))
    implementation("io.ktor", "ktor-client-js", ktorVersion)
    implementation("io.ktor", "ktor-client-logging", ktorVersion)
    implementation("org.jetbrains.kotlinx", "kotlinx-nodejs", nodejsDeclarationsVersion)
    implementation(devNpm("webpack", webpackVersion))
    implementation(devNpm("webpack-cli", webpackCliVersion))
    implementation(devNpm("terser-webpack-plugin", terserVersion))
    implementation(devNpm("tty", ttyVersion))
}

val rootPackageJson by rootProject.tasks.getting(RootPackageJsonTask::class)

node {
    download.set(true)
    nodeProjectDir.set(rootPackageJson.rootPackageJson.parentFile.normalize())
}

tasks {

    val compileProductionExecutableKotlinJs by getting(KotlinJsIrLink::class)

    val fixNodeFetchForWebpack by creating(Copy::class) {
        dependsOn(compileProductionExecutableKotlinJs)
        from(compileProductionExecutableKotlinJs.outputFile) {
            filter { line ->
                line.replace(
                    "tmp = jsRequireNodeFetch()(input, init);",
                    "tmp = jsRequireNodeFetch().default(input, init);"
                )
            }
            rename { "fixed.js" }
        }
        into("$buildDir/fix")
    }

    Mode.values()
        .map { it to it.name.toLowerCase().capitalize() }
        .forEach { (mode, modeName) ->
            val generateWebpackConfig =
                create<GenerateWebpackConfig>("generate${modeName}WebpackConfig") {
                    dependsOn(fixNodeFetchForWebpack)
                    group = "other"
                    target = NODE
                    this.mode = mode // PRODUCTION will fail
                    entryFile = fixNodeFetchForWebpack.destinationDir / "fixed.js"
                    modulesFolder.set(listOf(rootPackageJson.rootPackageJson.parentFile / "node_modules"))
                    outputBundleName = buildString {
                        append(project.name)
                        when (mode) {
                            PRODUCTION -> append("-prod")
                            DEVELOPMENT -> append("-dev")
                        }
                        append(".js")
                    }
                    outputBundleFolder = file("$buildDir/distributions").absolutePath
                    outputConfig = file("$buildDir/webpack/webpack.${modeName.toLowerCase()}.js")
                    if (mode == PRODUCTION)
                        terserSettings.set(
                            TerserPluginSettings(
                                parallel = true,
                                terserOptions = TerserPluginSettings.Options(
                                    mangle = true,
                                    sourceMaps = false,
                                    keepClassnames = Regex("AbortSignal"),
                                    keepFileNames = Regex("AbortSignal")
                                )
                            )
                        )
                }

            val webpackExecutable = create<NodeTask>("${modeName.toLowerCase()}WebpackExecutable") {
                group = "distribution"
                dependsOn(generateWebpackConfig)
                script.set(rootPackageJson.rootPackageJson.parentFile / "node_modules/webpack-cli/bin/cli.js")
                args.set(listOf("-c", generateWebpackConfig.outputConfig.absolutePath))


                setNodeModulesPath(rootPackageJson.rootPackageJson.parentFile / "node_modules")

                inputs.file(generateWebpackConfig.outputConfig)
                inputs.file(fixNodeFetchForWebpack.destinationDir / "fixed.js")
                outputs.file(generateWebpackConfig.outputBundleFile)
            }

            register<NodeTask>("run${modeName}WebpackExecutable") {
                group = "application"
                dependsOn(webpackExecutable)
                script.set(generateWebpackConfig.outputBundleFile)
                Properties().apply { load(rootProject.file("local.properties").bufferedReader()) }
                    .entries.toList()
                    .associate { it.key.toString() to it.value.toString() }
                    .let {
                        @Suppress("UnstableApiUsage")
                        environment.putAll(it)
                    }
            }
        }

}

@Suppress("UnstableApiUsage")
fun NodeTask.setNodeModulesPath(path: String) =
    environment.put("NODE_PATH", path)

@Suppress("UnstableApiUsage")
fun NodeTask.setNodeModulesPath(folder: File) =
    environment.put("NODE_PATH", folder.normalize().absolutePath)

fun File.child(name: String) =
    File(this, name)

operator fun File.div(name: String) =
    child(name)
