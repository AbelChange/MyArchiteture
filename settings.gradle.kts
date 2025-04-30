pluginManagement {
    repositories {
        mavenLocal {
            name = "local"
            url = uri("./repo")
        }
        gradlePluginPortal()
        maven {
            name = "jcenter托管"
            url = uri("https://maven.aliyun.com/repository/public")
        }
        maven {
            name = "google备选"
            url = uri("https://maven.aliyun.com/repository/google")
        }
        google{
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
    }
    resolutionStrategy {
        //非官方插件
        eachPlugin {
            println("eachPlugin" + requested.id.id + { requested.version.toString() })
            when (requested.id.id) {
                "androidx.navigation.safeargs.kotlin" -> {
                    useModule("androidx.navigation:navigation-safe-args-gradle-plugin:${requested.version}")
                }
                "com.ablec.time_cost"->{
                    useModule("com.ablec.plugin:time_cost:${requested.version}")
                }
                else -> {}
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal {
            name = "local"
            url = uri("./repo")
        }
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
include(":time_cost")
