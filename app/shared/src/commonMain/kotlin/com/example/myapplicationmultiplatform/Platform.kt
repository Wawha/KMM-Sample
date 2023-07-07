package com.example.kmmsample


enum class OS {
    Windows, Linux, Mac, Android, Ios
}

interface Platform {
    val name: String

    fun GetOS(): OS

    fun LoadLibrary(libraryName: String)
}

expect fun getPlatform(): Platform
