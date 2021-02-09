plugins {
    `kotlin-dsl`
}

kotlin.target.compilations.all {
    kotlinOptions.jvmTarget = "1.8"
}

gradlePlugin {
    plugins {
        create("kotlingram") {
            id = "com.github.lamba92.kotlingram-plugin"
            displayName = "Telegram Api Parser Plugin for Kotlin"
            implementationClass = "com.github.lamba92.kotlingram.gradle.KotlingramPublishedApiPlugin"
        }
    }
}

repositories {
    mavenCentral()
    jcenter()
    gradlePluginPortal()
}


dependencies {

    val dokkaVersion: String by project
    val bintrayPluginVersion: String by project

    api(kotlin("gradle-plugin"))
    api(kotlin("serialization"))
    api(kotlin("stdlib"))
    api("com.jfrog.bintray.gradle", "gradle-bintray-plugin", bintrayPluginVersion)
    api("org.jetbrains.dokka", "dokka-gradle-plugin", dokkaVersion)

}
