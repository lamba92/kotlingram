package com.github.lamba92.kotlingram.gradle

import com.jfrog.bintray.gradle.BintrayPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
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

        val enableGithubPublications =
            searchPropertyOrNull("enableGithubPublications")?.toBoolean() == true
        val enableSonatypePublications =
            searchPropertyOrNull("enableSonatypePublications")?.toBoolean() == true

        configure<KotlinMultiplatformExtension> {

            jvm {
                compilations.all {
                    kotlinOptions.jvmTarget = "1.8"
                }
            }
            js(IR) {
                nodejs()
            }

            mingwX64()
            linuxX64()
            macosX64()

        }

        configure<SigningExtension> {
            val secretKey: String? = rootProject.file("secring.txt")
                .takeIf { it.exists() }
                ?.readText(Charsets.UTF_16LE)
                ?.drop(1) // mysterious unknown character needs to be dropped
            val password: String? = searchPropertyOrNull("SIGNING_PASSWORD")
            @Suppress("UnstableApiUsage")
            useInMemoryPgpKeys(secretKey, password)
        }

        val dokkaHtml by tasks.getting(DokkaTask::class)

        val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
            archiveClassifier.set("javadoc")
            from(dokkaHtml.outputDirectory)
        }

        configure<PublishingExtension> {
            repositories {
                if (enableGithubPublications)
                    maven {
                        name = "GitHubPackages"
                        url = uri("https://maven.pkg.github.com/lamba92/kotlingram")
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
                    artifact(javadocJar)
                    artifactId = "${rootProject.name}-$artifactId"
                    pom {
                        name.set(artifactId)
                        description.set("Telegram Bot APIs for Kotlin Multiplatform")
                        url.set("https://github.com/lamba92/kotlingram")
                        scm {
                            url.set("https://github.com/lamba92/kotlingram.git")
                        }
                        licenses {
                            license {
                                name.set("The Apache License, Version 2.0")
                                url.set("https://github.com/lamba92/kotlingram/blob/master/LICENSE")
                            }
                        }
                        developers {
                            developer {
                                id.set("lamba92")
                                name.set("Lamberto Basti")
                                email.set("basti.lamberto@gmail.com")
                            }
                        }
                    }
                    the<SigningExtension>().sign(this)
                }

//                val commonPublicationNames = listOf("metadata", "kotlinMultiplatform")
//
//                tasks.filterIsInstance<AbstractPublishToMaven>()
//                    .filter { it.publication.name in commonPublicationNames }
//                    .forEach { it.onlyIf { OperatingSystem.current().isWindows } }

            }
        }
    }

}
