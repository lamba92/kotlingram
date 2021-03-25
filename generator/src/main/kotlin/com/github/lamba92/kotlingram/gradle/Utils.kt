package com.github.lamba92.kotlingram.gradle

import com.github.gradle.node.task.NodeTask
import org.gradle.api.Project
import java.io.File
import kotlinx.datetime.*

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

fun File.child(name: String) =
    File(this, name)

operator fun File.div(name: String) =
    child(name)

val isCI
    get() = System.getenv("CI") == "true"

val GITHUB_SHA: String?
    get() = System.getenv("GITHUB_SHA")

fun Instant.getVersioningUTCDate(isSnapshot: Boolean = false) =
    toLocalDateTime(TimeZone.currentSystemDefault())
        .run {
            "$year.$monthNumber.$dayOfMonth" + if (isSnapshot)
                "-${hour.toString().padStart(2, '0')}.${
                    minute.toString().padStart(2, '0')
                }-SNAP" else ""
        }
