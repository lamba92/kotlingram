import java.util.Properties

plugins {
    kotlin("jvm")
    application
}

application {
    mainClass.set("com.github.lamba92.kotlingram.examples.jvm.MainKt")
}

kotlin {
    target {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    sourceSets {
        all {
            languageSettings.useExperimentalAnnotation("kotlin.ExperimentalStdlibApi")
        }
    }
}

dependencies {
    val ktorVersion: String by project
    val logbackVersion: String by project
    implementation(project(":api:kotlingram-bot-builder"))
    implementation("io.ktor", "ktor-client-cio", ktorVersion)
    implementation("io.ktor", "ktor-client-logging", ktorVersion)
    implementation("ch.qos.logback", "logback-classic", logbackVersion)
}

tasks {
    named<JavaExec>("run") {
        Properties().apply { load(rootProject.file("local.properties").bufferedReader()) }
            .map { it.key.toString() to it.value.toString() }
            .toMap()
            .let { environment(it) }
    }
}
