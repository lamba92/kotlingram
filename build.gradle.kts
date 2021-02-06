plugins {
    id("io.codearte.nexus-staging")
    id("org.jetbrains.dokka")
}

allprojects {
    version = System.getenv("GITHUB_REF")?.split("/")?.lastOrNull() ?: "1.0.5"
    group = "com.github.lamba92"
}

nexusStaging {
    username = "Lamba92"
    password = System.getenv("SONATYPE_PASSWORD") ?: ""
    serverUrl = "https://s01.oss.sonatype.org/service/local"
}
