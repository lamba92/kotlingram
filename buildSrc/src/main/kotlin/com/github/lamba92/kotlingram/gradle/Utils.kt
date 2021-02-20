package com.github.lamba92.kotlingram.gradle

import com.github.lamba92.kotlingram.gradle.tasks.GenerateWebpackConfig
import com.jfrog.bintray.gradle.BintrayExtension
import org.gradle.api.PolymorphicDomainObjectContainer
import org.gradle.api.Project
import org.gradle.kotlin.dsl.closureOf
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.the
import java.io.File
import kotlin.reflect.KClass

fun Project.searchPropertyOrNull(name: String, vararg aliases: String): String? {

    fun searchEverywhere(name: String): String? =
        findProperty(name) as? String? ?: System.getenv(name)

    searchEverywhere(name)?.let { return it }

    aliases.forEach {
        searchEverywhere(it)?.let { return it }
    }

    return null
}

fun BintrayExtension.pkg(action: BintrayExtension.PackageConfig.() -> Unit) {
    pkg(closureOf(action))
}

fun BintrayExtension.PackageConfig.version(action: BintrayExtension.VersionConfig.() -> Unit) {
    version(closureOf(action))
}

fun BintrayExtension.setPublications(names: Collection<String>) =
    setPublications(*names.toTypedArray())

fun BintrayExtension.setPublications(action: () -> Collection<String>) =
    setPublications(action())

fun String.appendIfMissing(s: String) =
    if (endsWith(s)) this else this + s

val GenerateWebpackConfig.outputBundleFile
    get() = File(outputBundleFolder, outputBundleName)

fun File.child(name: String) =
    File(this, name)

operator fun File.div(name: String) =
    child(name)
