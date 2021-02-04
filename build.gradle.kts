allprojects {
    version = System.getenv("GITHUB_REF")?.split("/")?.lastOrNull() ?: "0.0.1"
    group = "com.github.lamba92"
}
