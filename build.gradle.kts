import com.github.lamba92.kotlingram.gradle.GITHUB_SHA
import com.github.lamba92.kotlingram.gradle.getVersioningUTCDate
import com.github.lamba92.kotlingram.gradle.searchPropertyOrNull
import kotlinx.datetime.Clock

plugins {
    id("io.codearte.nexus-staging")
}

allprojects {
    version = System.getenv("GITHUB_REF")?.split("/")?.lastOrNull().takeIf { it != "master" }
        ?: GITHUB_SHA?.let { "${it.take(7)}-SNAPSHOT" } //ex ffac537e6cbbf934b08745a378932722df287a53
        ?: Clock.System.now().getVersioningUTCDate(true) //ex 2020.01.28-12.13-SNAPSHOT

    group = "com.github.lamba92"
    repositories {
        mavenCentral()
        jcenter()
        maven("https://kotlin.bintray.com/kotlinx")
        maven("https://dl.bintray.com/kodein-framework/kodein-dev")
    }
}

nexusStaging {
    packageGroup = group as String
    username = "Lamba92"
    password = searchPropertyOrNull("SONATYPE_PASSWORD")
}

tasks.closeRepository {
    dependsOn(":api:core:publishToSonatype", ":api:bot-builder:publishToSonatype")
}

println("Project version is $version")
