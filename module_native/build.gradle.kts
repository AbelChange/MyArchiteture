plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.ablec.module_native"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
        externalNativeBuild {
            cmake {
                cppFlags("-std=c++14")
                abiFilters += listOf(
                    "arm64-v8a",
                    "armeabi-v7a"
                )
            }
        }
    }

    kapt {
        arguments {
            arg("AROUTER_MODULE_NAME", project.name)
        }
    }

    externalNativeBuild {
        cmake {
            path("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }

    resourcePrefix("module_native")

    buildFeatures {
        viewBinding = true
    }
}


dependencies {
    implementation(projects.moduleBase)
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    kapt(libs.arouter.compiler)
}


