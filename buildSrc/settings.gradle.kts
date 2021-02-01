@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {

        val kotlinVersion: String by settings

        kotlin("multiplatform") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
        kotlin("jvm") version kotlinVersion
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        jcenter()
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://plugins.gradle.org/m2/")
    }
}
