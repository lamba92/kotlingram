@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {

        val kotlinVersion: String by settings
        val dokkaVersion: String by settings
        val nexusStagingVersion: String by settings

        kotlin("multiplatform") version kotlinVersion
        id("org.jetbrains.dokka") version dokkaVersion
        kotlin("plugin.serialization") version kotlinVersion
        kotlin("jvm") version kotlinVersion
        id("io.codearte.nexus-staging") version nexusStagingVersion
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        jcenter()
        maven("https://kotlin.bintray.com/kotlinx")
    }
}

rootProject.name = "telegram-bot-kotlin-api"

include(":generated-apis")

includeBuild("generator")
