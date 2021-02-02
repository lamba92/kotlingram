package com.github.lamba92.gradle.telegram.parser

import kotlinx.cli.ArgType
import java.io.File

object FolderArgType : ArgType<File>(false) {
    override val description = "{ String }"

    override fun convert(value: kotlin.String, name: kotlin.String) =
        File(value).also {
            if (it.exists())
                require(it.isDirectory) { "Provided $value is a file" }
        }

}
