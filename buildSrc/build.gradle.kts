plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.4.30"
}

kotlin.target.compilations.all {
    kotlinOptions.jvmTarget = "1.8"
}

gradlePlugin {
    plugins {
        create("kotlingram") {
            id = "com.github.lamba92.kotlingram-plugin"
            displayName = "Telegram Api Parser Plugin for Kotlin"
            implementationClass = "com.github.lamba92.kotlingram.gradle.KotlingramPublishedApiPlugin"
        }
    }
}

repositories {
    mavenCentral()
    jcenter()
    gradlePluginPortal()
    maven("https://kotlin.bintray.com/kotlinx")
}


dependencies {

    val dokkaVersion: String by project
    val nexusStagingPluginVersion: String by project
    val nexusPublishPluginVersion: String by project
    val nodePluginVersion: String by project
    val downloadPluginVersion: String by project
    val kotlinxDatetimeVersion: String by project

    api(kotlin("gradle-plugin"))
    api(kotlin("serialization"))
    api(kotlin("stdlib"))
    api("org.jetbrains.dokka", "dokka-gradle-plugin", dokkaVersion)
    api("io.codearte.gradle.nexus", "gradle-nexus-staging-plugin", nexusStagingPluginVersion)
    api("com.github.node-gradle", "gradle-node-plugin", nodePluginVersion)
    api("de.undercouch", "gradle-download-task", downloadPluginVersion)
    api("de.marcphilipp.gradle", "nexus-publish-plugin", nexusPublishPluginVersion)
    api("org.jetbrains.kotlinx", "kotlinx-datetime", kotlinxDatetimeVersion)
}
