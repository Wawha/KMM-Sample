package com.example.kmmsample

import java.io.File

class DesktopPlatform : Platform {
    override val name: String = System.getProperty("os.name") + " " + System.getProperty("os.version")
    override fun LoadLibrary(libraryName: String) {
        when (GetOS()) {
            OS.Linux -> LoadDesktopLibrary("libAppBackendCxx.so")
            OS.Windows -> LoadDesktopLibrary("AppBackendCxx.dll")
            else -> throw Exception("Error, load library not implement for that platform")
        }
    }

    override fun GetOS(): OS {
        val os = System.getProperty("os.name").lowercase()
        return when {
            os.contains("win") -> OS.Windows
            os.contains("linux") -> OS.Linux
            os.contains("mac") -> OS.Mac
            else -> throw Exception("Error, unknown platform '${os}'")
        }
    }

    private fun LoadDesktopLibrary(libraryFileName: String) {
        val jarPath = File(AppBackend::class.java.protectionDomain?.codeSource!!.location.toURI()).parent
        val os = System.getProperty("os.name").lowercase()
        val arch = System.getProperty("os.arch")
        val fullFileName = "${jarPath}/${os}-${arch}/${libraryFileName}"
        System.load(fullFileName)
    }
}

actual fun getPlatform(): Platform = DesktopPlatform()
