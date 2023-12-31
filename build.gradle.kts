plugins {
    id("com.android.application").apply(false)
    id("com.android.library").apply(false)
    kotlin("android").apply(false)
    kotlin("multiplatform").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
