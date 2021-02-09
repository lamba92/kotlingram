plugins {
    kotlin("jvm")
}

kotlin.target.compilations.all {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {

    val skrapeVersion: String by project
    val jsoupVersion: String by project
//    val kotlinPoetVersion: String by project
//    val kotlinxSerializationVersion: String by project

    implementation(kotlin("stdlib"))
    api("it.skrape", "skrapeit-core", skrapeVersion)
    api("org.jsoup", "jsoup", jsoupVersion)
//    api("com.squareup", "kotlinpoet", kotlinPoetVersion)
//    api("org.jetbrains.kotlinx", "kotlinx-serialization-core", kotlinxSerializationVersion)
}
