plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("com.github.lamba92.gradle.telegram.parser.MainKt")
}

kotlin.target.compilations.all {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    val kotlinxCliVersion: String by project
    api("org.jetbrains.kotlinx", "kotlinx-cli", kotlinxCliVersion)
    api(project(":parser"))
}
