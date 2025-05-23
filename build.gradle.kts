import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kapt) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlin.safeargs) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("time_cost") version "1.0.1-SNAPSHOT" apply false
}




subprojects {

    afterEvaluate {
        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8)
            }
        }
    }

    plugins.withType<com.android.build.gradle.AppPlugin>().configureEach {
        (project as ExtensionAware).extensions.configure<com.android.build.gradle.internal.dsl.BaseAppModuleExtension>("android") {
            compileSdk = 35
            defaultConfig {
                minSdk =  28
                ndk {
                    //noinspection ChromeOsAbiSupport
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
            compileSdk = 35
            defaultConfig {
                minSdk =  28
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



    plugins.withId("maven-publish") {
        (project as ExtensionAware).extensions.configure<PublishingExtension>("publishing") {
            repositories {
                mavenLocal{
                    name = "local"
                    url = uri("../repo")
                }
                maven {
                    name = "GithubRepo"
                    url = uri("xxxxx")
                    isAllowInsecureProtocol = true
                    credentials {
                        username = providers.gradleProperty("MAVENUSER").get()
                        password = providers.gradleProperty("MAVENPASSWORD").get()
                    }
                }
            }
        }
    }
}



