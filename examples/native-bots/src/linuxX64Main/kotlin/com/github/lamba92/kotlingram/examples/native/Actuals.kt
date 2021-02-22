package com.github.lamba92.kotlingram.examples.native

import kotlinx.cinterop.*


actual fun getenv(name: String): String? =
    platform.posix.getenv(name)?.toKString()

// TODO find a better solution
actual fun getOsInfo(): String =
    "Linux"
