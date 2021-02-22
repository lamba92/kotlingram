package com.github.lamba92.kotlingram.examples.native


actual fun getenv(name: String): String? =
    TODO("need macos to implement this")

actual fun getOsInfo(): String =
    "MacOs"
