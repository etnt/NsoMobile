// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.android.library") version "8.2.2" apply false
    kotlin("plugin.serialization") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.51" apply false
}

buildscript {
    repositories {
        // other repositories...
        google()
        mavenCentral()
    }
    dependencies {
        // other plugins...
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51")

    }
}