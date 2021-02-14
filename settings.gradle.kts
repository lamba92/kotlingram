@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        jcenter()
        maven("https://kotlin.bintray.com/kotlinx")
    }
}

rootProject.name = "kotlingram"

include(
    ":api",
    ":api:bot-builder",
    ":api:core",
    ":examples:jvm-bot",
    ":examples:js-bot"
)

includeBuild("generator")
