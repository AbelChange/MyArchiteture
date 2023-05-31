import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version Versions.androidGradlePlugin apply false
    id("com.android.library") version Versions.androidGradlePlugin apply false
    id("org.jetbrains.kotlin.android") version Versions.kotlin apply false
    id("org.jetbrains.kotlin.jvm") version Versions.kotlin apply false
    id("org.jetbrains.kotlin.kapt") version Versions.kotlin apply false
    id("org.jetbrains.kotlin.plugin.parcelize") version Versions.kotlin apply false
    id("androidx.navigation.safeargs") version Versions.navigationVersion apply false
    id("com.google.dagger.hilt.android") version Versions.dagger apply false
    id("WMRouter") version Versions.wmRouter apply false
}


subprojects {
    plugins.withType<com.android.build.gradle.AppPlugin>().configureEach {
        (project as ExtensionAware).extensions.configure<com.android.build.gradle.internal.dsl.BaseAppModuleExtension>("android") {
            compileSdk = Versions.COMPILE_SDK
            defaultConfig {
                minSdk =  Versions.MIN_SDK
                vectorDrawables.useSupportLibrary = true
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
        project.dependencies {
            add("implementation", "androidx.core:core-ktx:1.9.0")
        }
    }

    plugins.withType<com.android.build.gradle.LibraryPlugin>().configureEach {
        (project as ExtensionAware).extensions.configure<com.android.build.gradle.LibraryExtension>("android") {
            compileSdk = Versions.COMPILE_SDK
            defaultConfig {
                minSdk =  Versions.MIN_SDK
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
        }
        project.dependencies {
            add("implementation", "androidx.core:core-ktx:1.9.0")
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


