plugins {
    `kotlin-dsl`
    kotlin("jvm")
    id("maven-publish")
    id("com.gradle.plugin-publish") version "0.12.0"
}

dependencies {

    val skrapeVersion: String by project
    val kotlinxSerializationVersion: String by project
    val kotlinxCliVersion: String by project
    val jsoupVersion: String by project
    val junitVersion: String by project
//    val ideaExtPluginVersion: String by project

    implementation("it.skrape", "skrapeit-core", skrapeVersion)
    implementation("org.jetbrains.kotlinx", "kotlinx-cli", kotlinxCliVersion)
    implementation("org.jetbrains.kotlinx", "kotlinx-serialization-core", kotlinxSerializationVersion)
    implementation("org.jsoup", "jsoup", jsoupVersion)
//    implementation(
//        "org.jetbrains.gradle.plugin.idea-ext",
//        "org.jetbrains.gradle.plugin.idea-ext.gradle.plugin",
//        ideaExtPluginVersion
//    )

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter", "junit-jupiter-api", junitVersion)
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", junitVersion)

    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))
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
