pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    val kotlinVersion = extra["kotlin.version"] as String
    val agpVersion = extra["agp.version"] as String
    val composeVersion = extra["compose.version"] as String

    plugins {
        kotlin("jvm").version(kotlinVersion)
        kotlin("multiplatform").version(kotlinVersion)
        kotlin("android").version(kotlinVersion)

        id("com.android.application").version(agpVersion)
        id("com.android.library").version(agpVersion)
        id("org.jetbrains.compose").version(composeVersion)
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "KMM-Sample"

include("androidApp")
project(":androidApp").projectDir = file("app/androidApp")

include("desktopApp")
project(":desktopApp").projectDir = file("app/desktopApp")

include("shared")
project(":shared").projectDir = file("app/shared")
