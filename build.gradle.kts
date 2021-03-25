import java.time.LocalDateTime

plugins {
    id("io.github.gradle-nexus.publish-plugin")
}

val versioningFile = file(System.getenv("VERSIONING_FILE_PATH") ?: "$buildDir/versioning.txt")

nexusPublishing {
    repositories {
        sonatype {
            username.set("Lamba92")
            password.set(System.getenv("SONATYPE_PASSWORD"))
        }
    }
}

allprojects {
    version = System.getenv("GITHUB_REF")?.split("/")?.lastOrNull().takeIf { it != "master" }
        ?: versioningFile.takeIf { it.exists() }?.let { "SNAP.${it.readText()}" } //ex SNAP:20210223.141449
                ?: with(LocalDateTime.now()) {
            "$year.$monthValue.$dayOfMonth-${hour.toString().padStart(2, '0')}.${
                minute.toString().padStart(2, '0')
            }-SNAP"
        } //ex 2020.01.28-12.13-SNAPSHOT

    group = "com.github.lamba92"
}


task("generateProjectVersionFile") {
    group = "ci"
    doLast {
        with(LocalDateTime.now()) {
            versioningFile.writeText(
                year.toString() + monthValue.toString().padStart(0, '0') +
                        dayOfMonth.toString().padStart(0, '0') +
                        "." + hour.toString().padStart(0, '0') +
                        minute.toString().padStart(0, '0') +
                        second.toString().padStart(0, '0')
            )
        }
    }
}
