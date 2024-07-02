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
        eachPlugin {
            println("eachPluginId"+requested.id)
            when (requested.id.name) {
                "androidx.navigation" -> {
                    useModule("androidx.navigation:navigation-safe-args-gradle-plugin:")
                }
                //自定义插件实现 对应根目录下 插件声明
                "WMRouter"->{
                    useModule("io.github.meituan-dianping:plugin:1.2.1")
                }
                "test_plugin"->{
                    useModule("com.example.plugin:test_plugin:1.0.0-snapshot")
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
