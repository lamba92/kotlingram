import org.gradle.internal.os.OperatingSystem

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.github.lamba92.telegram-api-generator")
    `maven-publish`
}

telegramApiParser {
    outputDirectory = file("src/commonMain/kotlin")
    packageName = "com.github.lamba92.telegram.api.generated"
    telegramClientPackage = "com.github.lamba92.telegram.api"
}

kotlin {
    when {
        OperatingSystem.current().isWindows -> {
            jvm {
                compilations.all {
                    kotlinOptions.jvmTarget = "1.8"
                }
            }
            js(BOTH) {
                nodejs()
            }
            mingwX64()
            linuxX64()
        }
        OperatingSystem.current().isMacOsX -> {
            ios()
            watchos()
            tvos()
        }
    }

    targets.all {
        compilations.all {
            compileKotlinTaskProvider {
                dependsOn(tasks.generateTelegramApi)
            }
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                val kotlinxSerializationVersion: String by project
                val ktorVersion: String by project
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")
                api("io.ktor:ktor-client-core:$ktorVersion")
                api("io.ktor:ktor-client-serialization:$ktorVersion")
            }
        }
    }

}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/lamba92/telegram-bot-kotlin-api")
            credentials {
                username = "lamba92"
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

tasks.withType<AbstractPublishToMaven>()
    .first { it.publication == publishing.publications["metadata"] }
    .onlyIf { OperatingSystem.current().isWindows }
