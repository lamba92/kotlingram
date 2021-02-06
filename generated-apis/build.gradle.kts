import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.BintrayExtension.PackageConfig
import com.jfrog.bintray.gradle.tasks.BintrayUploadTask
import org.gradle.api.publish.maven.internal.artifact.FileBasedMavenArtifact
import org.gradle.internal.os.OperatingSystem
import org.jetbrains.kotlin.util.collectionUtils.filterIsInstanceAnd

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.jetbrains.dokka")
    id("com.github.lamba92.telegram-api-generator")
    id("com.jfrog.bintray")
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
                val coroutinesVersion: String by project
                val ktorVersion: String by project
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                api("io.ktor:ktor-client-core:$ktorVersion")
                api("io.ktor:ktor-client-serialization:$ktorVersion")
            }
        }
    }

}

signing {
    val secretKey: String? = rootProject.file("secring.txt").takeIf { it.exists() }?.readText()
    val password: String? = searchPropertyOrNull("SIGNING_PASSWORD")
    val publicKeyId: String? = searchPropertyOrNull("SIGNING_PUBLIC_KEY_ID")?.takeLast(8)
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
        if (searchPropertyOrNull("enableGithubPublications")?.toBoolean() == true)
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/lamba92/telegram-bot-kotlin-api")
                credentials {
                    username = "lamba92"
                    password = searchPropertyOrNull("GITHUB_TOKEN")
                }
            }
        if (searchPropertyOrNull("enableOssPublications")?.toBoolean() == true)
            maven {
                name = "SonaType"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = "Lamba92"
                    password = searchPropertyOrNull("SONATYPE_PASSWORD").also {
                        if (it == null)
                            logger.warn("SonaType password missing.")
                    }
                }
            }
    }
    publications {

        withType<MavenPublication> {
            signing.sign(this)
            artifactId = rootProject.name + artifactId.removePrefix(project.name)
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

tasks {
    publish {
        if (searchPropertyOrNull("enableBintrayPublications")?.toBoolean() == true)
            finalizedBy(bintrayUpload)
    }
}

bintray {
    user = "lamba92"
    key = searchPropertyOrNull("bintrayApiKey", "BINTRAY_API_KEY")
    pkg {
        version {
            name = project.version as String
        }
        repo = "com.github.lamba92"
        name = rootProject.name
        setLicenses("Apache-2.0")
        vcsUrl = "https://github.com/lamba92/${rootProject.name}"
        issueTrackerUrl = "https://github.com/lamba92/${rootProject.name}/issues"
    }
    publish = true
    setPublications(when {
        OperatingSystem.current().isMacOsX -> publishing.publications.names.filter { "mac" in it }
        else -> publishing.publications.names
    })
}

tasks.withType<BintrayUploadTask> {
    doFirst {
        publishing.publications.withType<MavenPublication> {
            buildDir.resolve("publications/$name/module.json").let {
                if (it.exists())
                    artifact(object : FileBasedMavenArtifact(it) {
                        override fun getDefaultExtension() = "module"
                    })
            }
        }
    }
}

fun BintrayExtension.pkg(action: PackageConfig.() -> Unit) {
    pkg(closureOf(action))
}

fun PackageConfig.version(action: BintrayExtension.VersionConfig.() -> Unit) {
    version(closureOf(action))
}

fun searchPropertyOrNull(name: String, vararg aliases: String): String? {

    fun searchEverywhere(name: String): String? =
        findProperty(name) as? String? ?: System.getenv(name)

    searchEverywhere(name)?.let { return it }

    aliases.forEach {
        searchEverywhere(it)?.let { return it }
    }

    return null
}

fun BintrayExtension.setPublications(names: Collection<String>) =
    setPublications(*names.toTypedArray())
