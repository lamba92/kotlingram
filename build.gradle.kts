import com.github.lamba92.kotlingram.gradle.getVersioningUTCDate
import com.github.lamba92.kotlingram.gradle.searchPropertyOrNull
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

plugins {
    id("io.codearte.nexus-staging")
}

val versioningFile = file(System.getenv("VERSIONING_FILE_PATH") ?: "$buildDir/versioning.txt")

allprojects {
    version = System.getenv("GITHUB_REF")?.split("/")?.lastOrNull().takeIf { it != "master" }
        ?: versioningFile.takeIf { it.exists() }?.let { "SNAP.${it.readText()}" } //ex SNAP:20210223.141449
            ?: Clock.System.now().getVersioningUTCDate(true) //ex 2020.01.28-12.13-SNAPSHOT

    group = "com.github.lamba92"
}

nexusStaging {
    packageGroup = group as String
    username = "Lamba92"
    password = searchPropertyOrNull("SONATYPE_PASSWORD")
}

tasks.closeRepository {
    dependsOn(":api:kotlingram-core:publishToSonatype", ":api:kotlingram-bot-builder:publishToSonatype")
}

println("Project version is $version")

task("generateProjectVersionFile") {
    group = "ci"
    doLast {
        with(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())) {
            versioningFile.writeText(
                    year.toString() + monthNumber.toString().padStart(0, '0') +
                        dayOfMonth.toString().padStart(0, '0') +
                        "." + hour.toString().padStart(0, '0') +
                        minute.toString().padStart(0, '0') +
                        second.toString().padStart(0, '0')
                )
        }
    }
}
