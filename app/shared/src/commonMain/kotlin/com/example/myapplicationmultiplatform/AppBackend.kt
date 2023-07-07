package com.example.kmmsample

import java.io.File

class AppBackend {

    external fun sayHello(): String

    companion object {
        init {
            getPlatform().LoadLibrary("AppBackendCxx")
        }
    }
}
