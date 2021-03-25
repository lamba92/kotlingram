plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.4.31"
}

repositories {
    mavenCentral()
    jcenter()
    gradlePluginPortal()
    maven("https://kotlin.bintray.com/kotlinx")
}


dependencies {

    val dokkaVersion: String by project
    val nexusPublishPluginVersion: String by project
    val nodePluginVersion: String by project

    api(kotlin("gradle-plugin"))
    api("org.jetbrains.dokka", "dokka-gradle-plugin", dokkaVersion)
    api("io.github.gradle-nexus", "publish-plugin", nexusPublishPluginVersion)
    api("com.github.node-gradle", "gradle-node-plugin", nodePluginVersion)

}
