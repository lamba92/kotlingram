package com.github.lamba92.kotlingram.gradle

import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.BintrayPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import com.jfrog.bintray.gradle.tasks.BintrayUploadTask
import org.gradle.api.publish.maven.internal.artifact.FileBasedMavenArtifact
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.api.publish.maven.tasks.AbstractPublishToMaven
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.internal.os.OperatingSystem
import org.gradle.kotlin.dsl.*
import org.gradle.plugins.signing.SigningExtension
import org.gradle.plugins.signing.SigningPlugin
import org.jetbrains.dokka.gradle.DokkaPlugin
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper
import org.jetbrains.kotlinx.serialization.gradle.SerializationGradleSubplugin

open class KotlingramPublishedApiPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        apply<KotlinMultiplatformPluginWrapper>()
        apply<SerializationGradleSubplugin>()
        apply<DokkaPlugin>()
        apply<BintrayPlugin>()
        apply<MavenPublishPlugin>()
        apply<SigningPlugin>()

//        val isCi = System.getenv("CI") == "true"
        val enableGithubPublications =
            searchPropertyOrNull("enableGithubPublications")?.toBoolean() == true
        val enableSonatypePublications =
            searchPropertyOrNull("enableSonatypePublications")?.toBoolean() == true
        val enableBintrayPublications =
            searchPropertyOrNull("enableBintrayPublications")?.toBoolean() == true

        the<KotlinMultiplatformExtension> {

            jvm {
                compilations.all {
                    kotlinOptions.jvmTarget = "1.8"
                }
            }
            js {
                nodejs()
            }

//            mingwX64()
//            linuxX64()
//            macosX64()

            sourceSets {
                named("commonMain") {
                    dependencies {
                        val kotlinxSerializationVersion: String by project
                        val ktorVersion: String by project
                        api("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")
                        api("io.ktor:ktor-client-serialization:$ktorVersion")
                    }
                }
            }
        }
        the<SigningExtension> {
            val secretKey: String? = rootProject.file("secring.txt").takeIf { it.exists() }?.readText()
            val password: String? = searchPropertyOrNull("SIGNING_PASSWORD")
            val publicKeyId: String? = searchPropertyOrNull("SIGNING_PUBLIC_KEY_ID")?.takeLast(8)
            @Suppress("UnstableApiUsage")
            useInMemoryPgpKeys(publicKeyId, secretKey, password)
        }

        val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
            archiveClassifier.set("javadoc")
        }

        the<PublishingExtension> {
            repositories {
                if (enableGithubPublications)
                    maven {
                        name = "GitHubPackages"
                        url = uri("https://maven.pkg.github.com/lamba92/telegram-bot-kotlin-api")
                        credentials {
                            username = "lamba92"
                            password = searchPropertyOrNull("GITHUB_TOKEN")
                        }
                    }
                if (enableSonatypePublications)
                    maven {
                        name = "SonaType"
                        url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                        credentials {
                            username = "Lamba92"
                            password = searchPropertyOrNull("SONATYPE_PASSWORD")
                        }
                    }
            }
            publications {

                withType<MavenPublication> {
                    if (System.getenv("CI") == "true")
                        the<SigningExtension>().sign(this)
                    artifactId = "${rootProject.name}-$artifactId"
                    artifact(javadocJar)
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

                tasks.filterIsInstance<AbstractPublishToMaven>()
                    .filter { it.publication.name in commonPublicationNames }
                    .forEach { it.onlyIf { OperatingSystem.current().isWindows } }

            }
        }
        the<BintrayExtension> {
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
            setPublications {
                when {
                    OperatingSystem.current().isMacOsX ->
                        the<PublishingExtension>().publications.names.filter { "mac" in it }
                    else -> the<PublishingExtension>().publications.names
                }
            }
        }

        tasks {
            named("publish") {
                if (enableBintrayPublications)
                    finalizedBy("bintrayUpload")
            }
            withType<BintrayUploadTask> {
                doFirst {
                    the<PublishingExtension>().publications.withType<MavenPublication> {
                        buildDir.resolve("publications/$name/module.json").let {
                            if (it.exists())
                                artifact(object : FileBasedMavenArtifact(it) {
                                    override fun getDefaultExtension() = "module"
                                })
                        }
                    }
                }
            }
        }
    }

}
