package com.example.kmmsample

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
    override fun GetOS(): OS {
        return OS.Android
    }

    override fun LoadLibrary(libraryName: String) {
        System.loadLibrary(libraryName)
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()
