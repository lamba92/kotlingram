@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        jcenter()
        maven("https://kotlin.bintray.com/kotlinx")
    }
}

rootProject.name = "kotlingram"

include(":api", ":api:builder", ":api:core")

includeBuild("generator")
