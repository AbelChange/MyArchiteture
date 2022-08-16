// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("io.github.meituan-dianping:plugin:1.2.1")
    }
}

plugins {
    id("com.android.application") version Versions.androidGradlePlugin apply false
    id("com.android.library") version Versions.androidGradlePlugin apply false
    kotlin("android") version Versions.kotlin apply false
    kotlin("kapt") version Versions.kotlin apply false
    kotlin("plugin.serialization") version Versions.kotlin apply false
    id("com.google.dagger.hilt.android") version Versions.dagger apply false
}

//task clean(type: Delete) {
//    delete rootProject.buildDir
//}





