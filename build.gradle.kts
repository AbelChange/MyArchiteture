import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.android.build.gradle.LibraryExtension

buildscript {
    repositories {
        maven {
            name = "jcenter托管"
            url = uri("https://maven.aliyun.com/repository/public")
        }
        maven {
            name = "google备选"
            url = uri("https://maven.aliyun.com/repository/google")
        }
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.androidGradlePlugin}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.dagger}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationVersion}")
        classpath("io.github.meituan-dianping:plugin:${Versions.wmRouter}")
    }
}

//plugins {
//    id("com.android.application") version Versions.androidGradlePlugin apply false
//    id("com.android.library") version Versions.androidGradlePlugin apply false
//    kotlin("android") version Versions.kotlin apply false
//    kotlin("kapt") version Versions.kotlin apply false
//    kotlin("plugin.serialization") version Versions.kotlin apply false
//    id("com.google.dagger.hilt.android") version Versions.dagger apply false
//}

project.allprojects {
    plugins.withId("com.android.library") {
        (project as ExtensionAware).extensions.configure(
            "android",
            Action<LibraryExtension> {
                compileSdk = Versions.COMPILE_SDK
                defaultConfig {
                    minSdk = Versions.MIN_SDK
                    targetSdk = Versions.TARGET_SDK
                }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }
            })
    }

    plugins.withId("com.android.application") {
        (project as ExtensionAware).extensions.configure(
            "android",
            Action<BaseAppModuleExtension> {
                compileSdk = Versions.COMPILE_SDK
                defaultConfig {
                    minSdk = Versions.MIN_SDK
                    targetSdk = Versions.TARGET_SDK
                }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }
            })
    }

}





