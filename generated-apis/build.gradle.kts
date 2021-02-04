import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.util.collectionUtils.filterIsInstanceAnd

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.github.lamba92.telegram-api-generator")
    `maven-publish`
    signing
}

telegramApiParser {
    outputDirectory = file("src/commonMain/kotlin")
    packageName = "com.github.lamba92.telegram.api.generated"
    telegramClientPackage = "com.github.lamba92.telegram.api"
}

kotlin {
    if (System.getenv("CI") == "true")
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
            OperatingSystem.current().isMacOsX -> macosX64()
        }
    else {
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
        macosX64()
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

signing {
    val secretKey: String? = rootProject.file("secring.txt").readText().takeIf { it.isNotEmpty() }
    val password: String? = System.getenv("SIGNING_PASSWORD")
    val publicKeyId: String? = System.getenv("SIGNING_PUBLIC_KEY_ID")?.takeLast(8)
    if (secretKey == null || password == null || publicKeyId == null) {
        logger.warn(buildString {
            appendln("Signing info missing:")
            appendln(" - secretKey ${if (secretKey == null) "NULL" else "OK"}")
            appendln(" - password ${if (password == null) "NULL" else "OK"}")
            appendln(" - publicKeyId ${if (publicKeyId == null) "NULL" else "OK"}")
        })
    }
    @Suppress("UnstableApiUsage")
    useInMemoryPgpKeys(publicKeyId, secretKey, password)
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
        maven {
            name = "SonaType"
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = "Lamba92"
                password = System.getenv("SONATYPE_PASSWORD").also {
                    if (it == null)
                        logger.warn("SonaType password missing.")

                }
            }
        }
    }

    publications {

        withType<MavenPublication> {
            signing.sign(this)
            pom {
                description.set("Telegram Bot APIs for Kotlin Multiplatform")
                url.set("https://github.com/lamba92/telegram-bot-kotlin-api")
                scm {
                    url.set("https://github.com/lamba92/telegram-bot-kotlin-api.git")
                }
                licenses {
                    name.set("The Apache License, Version 2.0")
                    url.set("https://github.com/lamba92/telegram-bot-kotlin-api/blob/master/LICENSE")
                }
                developers {
                    developer {
                        id.set("lamba92")
                        name.set("Lamberto Basti")
                        email.set("basti.lamberto@gmail.com")
                    }
                }
            }
        }

        val commonPublicationNames = listOf("metadata", "kotlinMultiplatform")

        tasks.filterIsInstanceAnd<AbstractPublishToMaven> { it.publication.name in commonPublicationNames }
            .forEach { it.onlyIf { OperatingSystem.current().isWindows } }

    }
}
