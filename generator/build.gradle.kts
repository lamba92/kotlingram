plugins {
    `kotlin-dsl`
    kotlin("jvm")
    id("maven-publish")
    id("com.gradle.plugin-publish") version "0.12.0"
}

allprojects {
    repositories {
        mavenCentral()
        jcenter()
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://plugins.gradle.org/m2/")
    }
}

kotlin.target.compilations.all {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {

    val dokkaVersion: String by project
    val nexusPublishPluginVersion: String by project
    val nodePluginVersion: String by project
    val downloadPluginVersion: String by project
    val kotlinxDatetimeVersion: String by project

    api(kotlin("gradle-plugin"))
    api(kotlin("serialization"))
    api(project(":parser"))
    api(kotlin("reflect"))
    api("org.jetbrains.dokka", "dokka-gradle-plugin", dokkaVersion)
    api("io.github.gradle-nexus", "publish-plugin", nexusPublishPluginVersion)
    api("com.github.node-gradle", "gradle-node-plugin", nodePluginVersion)
    api("de.undercouch", "gradle-download-task", downloadPluginVersion)
    api("org.jetbrains.kotlinx", "kotlinx-datetime", kotlinxDatetimeVersion)

}


gradlePlugin {
    plugins {
        create("telegramApiParserPlugin") {
            id = "com.github.lamba92.kotlingram-api-generator"
            displayName = "Telegram Api Parser Plugin for Kotlin"
            implementationClass = "com.github.lamba92.kotlingram.gradle.TelegramApiParserPlugin"
        }
    }
    plugins {
        create("kotlingram") {
            id = "com.github.lamba92.kotlingram-plugin"
            displayName = "Telegram Api Parser Plugin for Kotlin"
            implementationClass = "com.github.lamba92.kotlingram.gradle.KotlingramPublishedApiPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/lamba92/kotlingram"
    vcsUrl = "https://github.com/lamba92/kotlingram"
    description = "Generates classes from the Telegram Bot API documentation for Kotlin"
    tags = listOf("telegram", "api", "kotlin", "multiplatform")
}
