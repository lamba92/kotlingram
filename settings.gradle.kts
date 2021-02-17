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
    ":api:bot-builder",
    ":api:core",
    ":examples:jvm-bot",
    ":examples:js-bot"
)

includeBuild("generator")
