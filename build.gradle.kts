import com.github.lamba92.kotlingram.gradle.getVersioningUTCDate
import com.github.lamba92.kotlingram.gradle.isCI
import com.github.lamba92.kotlingram.gradle.searchPropertyOrNull
import kotlinx.datetime.Clock

plugins {
    id("io.codearte.nexus-staging")
}

allprojects {
    version = System.getenv("GITHUB_REF")?.split("/")?.lastOrNull()
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

println("CI: $isCI")
