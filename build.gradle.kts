import com.github.lamba92.kotlingram.gradle.searchPropertyOrNull

plugins {
    id("io.codearte.nexus-staging")
}

allprojects {
    version = System.getenv("GITHUB_REF")?.split("/")?.lastOrNull() ?: "1.1.3"
    group = "com.github.lamba92"
}

nexusStaging {
    packageGroup = group as String
    username = "Lamba92"
    password = searchPropertyOrNull("SONATYPE_PASSWORD")
}
