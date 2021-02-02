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

    val ideaExtPluginVersion: String by project

    implementation(kotlin("reflect"))
    api(
        "org.jetbrains.gradle.plugin.idea-ext",
        "org.jetbrains.gradle.plugin.idea-ext.gradle.plugin",
        ideaExtPluginVersion
    )
    api(project(":parser"))
}

gradlePlugin {
    plugins {
        create("telegramApiParserPlugin") {
            id = "com.github.lamba92.telegram-api-generator"
            displayName = "Telegram Api Parser Plugin for Kotlin"
            implementationClass = "com.github.lamba92.gradle.telegram.TelegramApiParserPlugin"
        }
    }
}

pluginBundle {
    website = "https://github.com/lamba92"
    vcsUrl = "https://github.com/lamba92"
    description = "Generates classes from the Telegram Bot API documentation for Kotlin"
    tags = listOf("telegram", "api", "kotlin", "multiplatform")
}
