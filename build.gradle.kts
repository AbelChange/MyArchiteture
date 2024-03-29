import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    val compose_version by extra("1.2.0")
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.4.1" apply false
    id("com.android.library") version "7.4.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.0" apply false
    id("org.jetbrains.kotlin.kapt") version "1.8.0" apply false
    id("org.jetbrains.kotlin.plugin.parcelize") version "1.8.0" apply false
    id("androidx.navigation.safeargs.kotlin") version "2.5.3" apply false
    id("com.google.dagger.hilt.android") version "2.43.2" apply false
    id("WMRouter") version Versions.wmRouter apply false
}


subprojects {
    plugins.withType<com.android.build.gradle.AppPlugin>().configureEach {
        (project as ExtensionAware).extensions.configure<com.android.build.gradle.internal.dsl.BaseAppModuleExtension>("android") {
            compileSdk = Versions.COMPILE_SDK
            defaultConfig {
                minSdk =  Versions.MIN_SDK
                vectorDrawables.useSupportLibrary = true
                ndk {
                    abiFilters += listOf(
                        "arm64-v8a",
                        "armeabi-v7a"
                    )
                }
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
            lint {
                abortOnError = true
                checkReleaseBuilds = false
            }
        }
    }

    plugins.withType<com.android.build.gradle.LibraryPlugin>().configureEach {
        (project as ExtensionAware).extensions.configure<com.android.build.gradle.LibraryExtension>("android") {
            compileSdk = Versions.COMPILE_SDK
            defaultConfig {
                minSdk =  Versions.MIN_SDK
                ndk {
                    abiFilters += listOf(
                        "arm64-v8a",
                        "armeabi-v7a"
                    )
                }
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    plugins.withId("maven-publish") {
        (project as ExtensionAware).extensions.configure<PublishingExtension>("publishing") {
            repositories {
                maven {
                    url = uri("https://maven.freemeos.com:13458/repository/cloud/")
                    val property = gradleLocalProperties(rootDir)
                    credentials {
                        username = property.getProperty("username")
                        password = property.getProperty("password")
                    }
                }
            }
            publications {
                maybeCreate<MavenPublication>("release").apply {
                    groupId = "com.xxxx.xxxx"
                }
            }
        }
    }
}



