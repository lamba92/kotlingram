@file:Suppress("UnstableApiUsage")

rootProject.name = "kotlingram"

include(
    ":api",
    ":api:bot-builder",
    ":api:core",
    ":examples:jvm-bot",
    ":examples:js-bot"
)

includeBuild("generator")
