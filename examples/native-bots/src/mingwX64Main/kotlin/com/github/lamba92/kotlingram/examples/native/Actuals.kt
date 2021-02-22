package com.github.lamba92.kotlingram.examples.native

import kotlinx.cinterop.*
import platform.windows.DWORD
import platform.windows.GetVersion

actual fun getenv(name: String): String? =
    platform.posix.getenv(name)?.toKString()

// https://docs.microsoft.com/en-us/windows/win32/api/sysinfoapi/nf-sysinfoapi-getversion
actual fun getOsInfo() = buildString {
    val version = GetVersion()
    append("Windows ${version.lowWord.lowByte.toInt()}.${version.lowWord.highByte.toInt()}")
    if (version < 0x80000000.toUInt())
        append(version.highWord.toInt())
}

val DWORD.lowWord: DWORD
    get() = and(0xFFFF.toUInt())

val DWORD.highWord: DWORD
    get() = shr(16)

val DWORD.lowByte: DWORD
    get() = and(0xFF.toUInt())

val DWORD.highByte: DWORD
    get() = shr(8)
