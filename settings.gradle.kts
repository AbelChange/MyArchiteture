pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
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
    }
    resolutionStrategy {
        //非官方插件
        eachPlugin {
            println("eachPluginId"+requested.id.id)
            when (requested.id.id) {
                "androidx.navigation.safeargs.kotlin" -> {
                    useModule("androidx.navigation:navigation-safe-args-gradle-plugin:${requested.version}")
                }
                "io.github.meituan-dianping"->{
                    useModule("io.github.meituan-dianping:plugin:${requested.version}")
                }
                //class path "com.example.plugin:test_plugin:1.0.1-snapshot"
                "com.example.plugin"->{
                    useModule("com.example.plugin:test_plugin:${requested.version}")
                }
                else -> {}
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()

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
        maven {
            name = "jitpack"
            url = uri("https://jitpack.io")
        }

    }
}

include(":app")
include(":library")
include(":module_base")

include(":module_compose")
include(":module_login")
include(":module_pay")
include(":module_native")

//include(":module_pay")

rootProject.name = "MyArchitecture"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":learn_note")
include(":test_plugin")
