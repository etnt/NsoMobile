// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.android.library") version "8.2.2" apply false
    kotlin("plugin.serialization") version "1.8.10" apply false
}

buildscript {
    extra["kotlin_version"] = "1.9.22"
    repositories {
        // other repositories...
        mavenCentral()
    }
    dependencies {
        // other plugins...

    }
}