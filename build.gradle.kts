import com.github.lamba92.kotlingram.gradle.searchPropertyOrNull

plugins {
    id("io.codearte.nexus-staging")
}

allprojects {
    version = System.getenv("GITHUB_REF")?.split("/")?.lastOrNull() ?: "1.2.0-alpha"
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

evaluationDependsOnChildren()

tasks.closeRepository {
    dependsOn(":api:core:publishToSonatype", ":api:bot-builder:publishToSonatype")
}
