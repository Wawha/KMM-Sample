plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
}

fun getNdkDirectory(): String {
    return "${android.ndkDirectory}"
}

fun getApiLevel(): String {
    return (System.getProperty("android.minSdk") as String? ?: "")
}

fun getOpenCVDir(): String {
    val userHome = System.getProperty("user.home")
    val openCVSdk = findProperty("opencvsdk") as String

    return "$userHome/$openCVSdk"
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmToolchain(11)
            }
        }
    }
    jvm("desktop") {
        jvmToolchain(11)
    }

    // iOS Generation: Temporary disable it to avoid gradle warnings
    // listOf(
    //     iosX64(),
    //     iosArm64(),
    //     iosSimulatorArm64()
    // ).forEach {
    //     it.binaries.framework {
    //         baseName = "shared"
    //     }
    // }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
            }
        }
    }

    project.buildDir = File("'${rootProject.buildDir}/gradle/common-kotlin")
}

android {
    namespace = "com.example.kmmsample"
    compileSdk = 33

    defaultConfig {
        minSdk = 30
    }
    signingConfigs {
        create("master") {
            keyAlias = "master"
            keyPassword = "testtest"
            storeFile = file("~/testandroid.jks")
            storePassword = "testtest"
        }
    }
    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
            ndk {
                // "x86_64" is required to be able to debug C++ Android code inside emulator
                abiFilters += listOf("x86_64", "arm64-v8a")
            }
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
            ndk {
                // "x86_64" is required to be able to debug C++ Android code inside emulator
                abiFilters += listOf("x86_64", "arm64-v8a")
            }
        }
        create("master") {
            initWith(getByName("release"))
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("master")
            ndk {
                abiFilters += "arm64-v8a"
            }
        }
    }
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
    buildFeatures {
        viewBinding = true
    }
    project.buildDir = File("${rootProject.buildDir}/gradle/common-kotlin")
}

dependencies {
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")
    debugImplementation("androidx.compose.ui:ui-tooling:1.4.3")
}
