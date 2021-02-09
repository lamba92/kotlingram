
allprojects {
    version = System.getenv("GITHUB_REF")?.split("/")?.lastOrNull() ?: "0.0.3"
    group = "com.github.lamba92"
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
