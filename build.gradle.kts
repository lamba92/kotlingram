plugins {
    id("io.codearte.nexus-staging")
    id("org.jetbrains.dokka")
}

allprojects {
    version = System.getenv("GITHUB_REF")?.split("/")?.lastOrNull() ?: "0.0.3"
    group = "com.github.lamba92"
}

nexusStaging {
    username = "Lamba92"
    password = System.getenv("SONATYPE_PASSWORD") ?: ""
    serverUrl = "https://s01.oss.sonatype.org/service/local/"
    packageGroup = group as String
}

tasks.closeAndReleaseRepository {
    onlyIf { searchPropertyOrNull("enableOssPublications")?.toBoolean() == true }
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
