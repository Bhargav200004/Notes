// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.5.2" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"

    //For Dagger hilt for dependency injection
    id("com.google.dagger.hilt.android") version "2.48" apply false
    //for ksp tool (Kotlin Annotation Process)
    id("com.google.devtools.ksp") version "2.0.0-1.0.22" apply false

    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10"

}