package com.github.lamba92.kotlingram.examples.mingwx64

import com.github.lamba92.kotlingram.examples.mingwx64.WindowsVersion.Name.*
import kotlinx.cinterop.*
import platform.windows.DWORD
import platform.windows.DWORDVar
import platform.windows.GetProductInfo
import platform.windows.PDWORD

data class WindowsVersion(val name: Name, val majorVersion: Int, val minorVersion: Int) {

    companion object {
        fun getCurrent() = memScoped {
            val dwOSMajorVersion: DWORD = 0.toUInt()
            val dwOSMinorVersion: DWORD = 0.toUInt()
            val dwSpMajorVersion: DWORD = 0.toUInt()
            val dwSpMinorVersion: DWORD = 0.toUInt()
            val pdwReturnedProductType = allocPointerTo<DWORDVar>()
            val r = GetProductInfo(
                dwOSMajorVersion,
                dwOSMinorVersion,
                dwSpMajorVersion,
                dwSpMinorVersion,
                pdwReturnedProductType.value
            ) != 0

            require(r) { "Error while retrieving version" }
            WindowsVersion(
                when (dwOSMajorVersion.toInt()) {
                    6 -> when (dwOSMinorVersion.toInt()) {
                        0 -> VISTA
                        1 -> `7`
                        3 -> `10`
                        else -> error("Unknown windows version ${dwOSMajorVersion.toInt()}.${dwOSMinorVersion.toInt()}")
                    }
                    else -> error("Unknown windows version ${dwOSMajorVersion.toInt()}.${dwOSMinorVersion.toInt()}")
                },
                dwSpMajorVersion.toInt(),
                dwOSMinorVersion.toInt()
            )
        }
    }

    enum class Name {
        `10`, `8.1`, `8`, `7`, VISTA, XP, UKNOWNK
    }

}
