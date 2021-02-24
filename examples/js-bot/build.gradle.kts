import com.github.gradle.node.task.NodeTask
import com.github.lamba92.kotlingram.gradle.child
import com.github.lamba92.kotlingram.gradle.div
import com.github.lamba92.kotlingram.gradle.outputBundleFile
import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig
import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig.Mode.DEVELOPMENT
import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig.Mode.PRODUCTION
import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig.Target.NODE
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrLink
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.RootPackageJsonTask
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
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
    implementation(project(":api:kotlingram-bot-builder"))
    implementation("io.ktor", "ktor-client-js", ktorVersion)
    implementation("io.ktor", "ktor-client-logging", ktorVersion)
    implementation("org.jetbrains.kotlinx", "kotlinx-nodejs", nodejsDeclarationsVersion)
    implementation(devNpm("webpack", webpackVersion))
    implementation(devNpm("webpack-cli", webpackCliVersion))
}

val rootPackageJson by rootProject.tasks.getting(RootPackageJsonTask::class)

node {
    download.set(true)
    nodeProjectDir.set(rootPackageJson.rootPackageJson.parentFile / "node_modules")
}

tasks {

    val compileProductionExecutableKotlinJs by getting(KotlinJsIrLink::class)

    val generateWebpackConfig by creating(GenerateWebpackConfig::class) {
        group = "distribution"
        target = NODE
        mode = DEVELOPMENT // PRODUCTION will fail
        entryFile = compileProductionExecutableKotlinJs.outputFile
        modulesFolder.set(listOf(rootPackageJson.rootPackageJson.parentFile / "node_modules"))
        outputBundleName = project.name + ".js"
        outputBundleFolder = file("$buildDir/distributions").absolutePath
    }

    val webpackExecutable: NodeTask by creating(NodeTask::class) {
        group = "distribution"
        dependsOn(generateWebpackConfig, compileProductionExecutableKotlinJs, rootPackageJson)
        script.set(rootPackageJson.rootPackageJson.parentFile / "node_modules/webpack-cli/bin/cli.js")
        args.set(listOf("-c", generateWebpackConfig.outputConfig.absolutePath))
        doLast {
            // workaround for https://youtrack.jetbrains.com/issue/KTOR-2124
            generateWebpackConfig.outputBundleFile.apply {
                val tmpFile = File(parentFile, "tmp.js")
                if (tmpFile.exists())
                    tmpFile.delete()
                tmpFile.createNewFile()
                useLines {
                    it.map {
                        // when not minified
                        it.replace(
                            "tmp = jsRequireNodeFetch()(input, init);",
                            "tmp = jsRequireNodeFetch().default(input, init);"
                        )
                    }
                        .map {
                            // when minified
                            it.replace(
                                "return e}()(e,t)",
                                "return e}().default(e,t)"
                            )
                        }
                        .chunked(100)
                        .forEach { tmpFile.appendText(it.joinToString("\n")) }
                }
                delete()
                tmpFile.renameTo(this)
            }
        }
    }

    register<NodeTask>("runWebpackExecutable") {
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
