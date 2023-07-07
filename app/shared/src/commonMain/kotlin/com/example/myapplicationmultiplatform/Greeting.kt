package com.example.kmmsample

import com.example.kmmsample.AppBackend

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        var appBackend = AppBackend()
        return "Hello ${platform.name}!\n\nMessage from c++: '${appBackend.sayHello()}'"
    }
}
