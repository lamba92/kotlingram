plugins {
    kotlin("jvm")
}

kotlin.target.compilations.all {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {

    val skrapeVersion: String by project
    val jsoupVersion: String by project

    implementation(kotlin("stdlib"))
    api("it.skrape", "skrapeit-core", skrapeVersion)
    api("org.jsoup", "jsoup", jsoupVersion)
}
