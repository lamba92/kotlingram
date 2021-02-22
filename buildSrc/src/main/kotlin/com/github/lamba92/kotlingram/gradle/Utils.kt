package com.github.lamba92.kotlingram.gradle

import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig
import org.gradle.api.Project
import java.io.File

fun Project.searchPropertyOrNull(name: String, vararg aliases: String): String? {

    fun searchEverywhere(name: String): String? =
        findProperty(name) as? String? ?: System.getenv(name)

    searchEverywhere(name)?.let { return it }

    aliases.forEach {
        searchEverywhere(it)?.let { return it }
    }

    return null
}

fun String.appendIfMissing(s: String) =
    if (endsWith(s)) this else this + s

val GenerateWebpackConfig.outputBundleFile
    get() = File(outputBundleFolder, outputBundleName)

fun File.child(name: String) =
    File(this, name)

operator fun File.div(name: String) =
    child(name)

