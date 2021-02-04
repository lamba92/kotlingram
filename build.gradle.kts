plugins {
    id("io.codearte.nexus-staging")

}

allprojects {
    version = System.getenv("GITHUB_REF")?.split("/")?.lastOrNull() ?: "0.0.1"
    group = "com.github.lamba92"
}

nexusStaging {
    username = "Lamba92"
    password = System.getenv("SONATYPE_PASSWORD") ?: ""
}
