import com.github.gradle.node.task.NodeTask
import com.github.lamba92.kotlingram.gradle.div
import com.github.lamba92.kotlingram.gradle.outputBundleFile
import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig
import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig.Mode.DEVELOPMENT
import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig.Mode.PRODUCTION
import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig.ResolveFallback.ModuleFallback
import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig.ResolveFallback.NoFallback
import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig.Target.NODE
import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig.Target.WEB
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
        useCommonJs()
    }
    sourceSets {
        all {
            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalStdlibApi")
            languageSettings.useExperimentalAnnotation("kotlin.time.ExperimentalTime")
        }
    }
}

node {
    download.set(true)
}

repositories {
    mavenCentral()
    jcenter()
    maven("https://kotlin.bintray.com/kotlinx")
}

dependencies {
    val ktorVersion: String by project
    val nodejsDeclarationsVersion: String by project
    implementation(project(":api:bot-builder"))
    implementation("io.ktor", "ktor-client-js", ktorVersion)
    implementation("io.ktor", "ktor-client-logging", ktorVersion)
    implementation("org.jetbrains.kotlinx", "kotlinx-nodejs", nodejsDeclarationsVersion)
}

val rootPackageJson by rootProject.tasks.getting(RootPackageJsonTask::class)

tasks {
    clean {
        delete("node_modules")
        delete("package-lock.json")
    }
    val compileKotlinJs by getting(Kotlin2JsCompile::class)

    val generateWebpackConfig by creating(GenerateWebpackConfig::class) {
        group = "distribution"
        target = NODE
        mode = DEVELOPMENT
        entryFile = compileKotlinJs.outputFile
        modulesFolder.set(listOf(rootPackageJson.rootPackageJson.parentFile / "node_modules"))
        outputBundleName = project.name + ".js"
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
