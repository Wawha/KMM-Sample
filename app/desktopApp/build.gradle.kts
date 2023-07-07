import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("io.github.tomtzook.gradle-cmake").version("1.2.2")
}

val clangVersion = 10

cmake {
    targets {
        val AppBackendCxx by creating {
            cmakeLists.set(file("../shared/CMakeLists.txt"))
            targetMachines.add(machines.host)
            generator.set(generators.ninja)
            cmakeArgs.add("-DCMAKE_BUILD_TYPE=Debug")
            cmakeArgs.add("-DCMAKE_C_COMPILER=clang-$clangVersion")
            cmakeArgs.add("-DCMAKE_CXX_COMPILER=clang++-$clangVersion")
            cmakeArgs.add("-DGradleJDKDir=${System.getProperty("user.home")}/.jdks/corretto-11.0.19")
            // In order to check if the task is outdated, we create a fake variable which change every run
            // Disable for the moment
            // cmakeArgs.add("-DGradleCurrentDate=${System.currentTimeMillis()}")
        }
    }
}
tasks["clean"].dependsOn("cmakeClean")
tasks["assemble"].dependsOn("cmakeBuild")


// Copy C++ library next to the jar file
tasks.register<Copy>("copyAppBackendCxxLibrary") {
    val os = System.getProperty("os.name").toLowerCase()
    val inputDir = "$buildDir/cmake/AppBackendCxx/$os-amd64/AppBackendCxx"
    val outputDir = "$buildDir/../common-kotlin/libs/$os-amd64"
    val libraryExt = if (os.contains("linux")) "so" else "dll"

    from(inputDir)
    include("*.$libraryExt")
    into(outputDir)

    println("Task copyAppBackendCxxLibrary, copy '$inputDir/*.$libraryExt' to '$outputDir'")
}
tasks["cmakeBuild"].finalizedBy("copyAppBackendCxxLibrary")


kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":shared"))
            }
        }
    }
    project.buildDir = File("${rootProject.buildDir}/gradle/desktopApp")
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            outputBaseDir.set(File("${rootProject.buildDir}/gradle/desktopApp-Output"))
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "AppDesktop"
            packageVersion = "1.0.0"
        }
    }
}
