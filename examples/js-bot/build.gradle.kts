import com.github.gradle.node.task.NodeTask
import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig
import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig.Mode.DEVELOPMENT
import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig.Mode.PRODUCTION
import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig.Target.NODE
import org.jetbrains.kotlin.gradle.targets.js.npm.tasks.RootPackageJsonTask
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import java.util.*

plugins {
    kotlin("js")
    id("com.github.node-gradle.node")
}

kotlin {
    js {
        nodejs()
        useCommonJs()
    }
    sourceSets {
        all {
            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalStdlibApi")
        }
    }
}

dependencies {
    val ktorVersion: String by project
    val nodejsDeclarationsVersion: String by project
    val bufferutilVersion: String by project
    val utf8ValidateVersion: String by project
    implementation(project(":api:bot-builder"))
    implementation("io.ktor", "ktor-client-js", ktorVersion)
    implementation("io.ktor", "ktor-client-logging", ktorVersion)
    implementation("org.jetbrains.kotlinx", "kotlinx-nodejs", nodejsDeclarationsVersion)
    implementation(npm("bufferutil", bufferutilVersion))
    implementation(npm("utf-8-validate", utf8ValidateVersion))
}

val rootPackageJson by rootProject.tasks.getting(RootPackageJsonTask::class)

tasks {
    val compileKotlinJs by getting(Kotlin2JsCompile::class)

    val generateWebpackConfig by creating(GenerateWebpackConfig::class) {
        target = NODE
        mode = DEVELOPMENT
        entryFile = compileKotlinJs.outputFile
        modulesFolder.set(listOf(File(rootPackageJson.rootPackageJson.parentFile, "node_modules")))
        outputBundleName = project.name
        outputBundleFolder = file("$buildDir/distributions").absolutePath
    }

    val webpackExecutable by creating(NodeTask::class) {
        group = "distribution"
        dependsOn(generateWebpackConfig, compileKotlinJs, rootPackageJson, npmInstall)
        script.set(file("node_modules/webpack-cli/bin/cli.js"))
        args.set(listOf("-c", generateWebpackConfig.outputConfig.absolutePath))
    }

    create<NodeTask>("runWebpackExecutable") {
        group = "distribution"
        dependsOn(webpackExecutable)
        script.set(file("$buildDir/distributions/js-bot.js"))
        Properties().apply { load(rootProject.file("local.properties").bufferedReader()) }
            .map { it.key.toString() to it.value.toString() }
            .toMap()
            .let {
                @Suppress("UnstableApiUsage")
                environment.putAll(it)
            }
    }
}
