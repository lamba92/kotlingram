@file:Suppress("UnstableApiUsage")

plugins {
    `gradle-enterprise`
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlwaysIf(System.getenv("CI") == "true")
    }
}

rootProject.name = "kotlingram"

include(
    ":api",
    ":api:kotlingram-bot-builder",
    ":api:kotlingram-core",
    ":examples:jvm-bot",
    ":examples:js-bot",
    ":examples:native-bots"
)

project(":api:kotlingram-bot-builder").projectDir = file("api/bot-builder")
project(":api:kotlingram-core").projectDir = file("api/core")

includeBuild("generator")
