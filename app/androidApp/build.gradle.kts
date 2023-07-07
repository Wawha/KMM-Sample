plugins {
    id("com.android.application")
    kotlin("android")
}

fun getNdkDirectory(): String {
    return "${android.ndkDirectory}"
}

fun getApiLevel(): String {
    return (findProperty("android.minSdk") as String? ?: "")
}

fun getBaseOutputDir(): String {
    return "../build"
}

android {
    namespace = "com.example.kmmsample.android"
    compileSdk = 33
    ndkVersion = "25.1.8937393"
    buildToolsVersion = "30.0.3"
    project.buildDir = File("${rootProject.buildDir}/gradle/androidApp")

    signingConfigs {
        create("master") {
            keyAlias = "master"
            keyPassword = "...."
            storeFile = file("~/testandroid.jks")
            storePassword = "...."
        }
    }
    defaultConfig {
        applicationId = "com.example.kmmsample.android"
        minSdk = 30
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        externalNativeBuild {
            cmake {
                val arch = System.getProperty("os.arch")
                cppFlags += "-std=c++17"
                arguments += "-DAndroidApiLevel=${getApiLevel()}"
                arguments += "-DNdkDir=${getNdkDirectory()}"
            }
        }
    }
    externalNativeBuild {
        cmake {
            path = File("../shared/CMakeLists.txt")
            version = "3.22.1"
            buildStagingDirectory(File("${rootProject.buildDir}/gradle-cxx"))
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        val composeVersion = extra["compose.version"] as String
        kotlinCompilerExtensionVersion = composeVersion
    }
    buildTypes {
        debug {
            ndk {
                // "x86_64" is required to be able to debug C++ Android code inside emulator
                abiFilters += listOf("x86_64", "arm64-v8a")
            }
        }
        release {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
            ndk {
                // "x86_64" is required to be able to debug C++ Android code inside emulator
                abiFilters += listOf("x86_64", "arm64-v8a")
            }
            externalNativeBuild {
                cmake {
                    arguments += "-DCMAKE_BUILD_TYPE=RelWithDebInfo"
                }
            }
        }
        create("master") {
            initWith(getByName("release"))
            isMinifyEnabled = true
            isDebuggable = false
            signingConfig = signingConfigs.getByName("master")
            ndk {
                abiFilters += "arm64-v8a"
            }
            externalNativeBuild {
                cmake {
                    arguments += "-DCMAKE_BUILD_TYPE=Release"
                }
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.4.3")
    implementation("androidx.compose.ui:ui-tooling:1.4.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")
    implementation("androidx.compose.foundation:foundation:1.4.3")
    implementation("androidx.compose.material:material:1.4.3")
    implementation("androidx.activity:activity-compose:1.7.2")
}
