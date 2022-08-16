pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.aliyun.com/repository/public")
        }
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.namespace) {
                "androidx.navigation.safeargs" -> {
                    useModule("androidx.navigation:navigation-safe-args-gradle-plugin:${requested.version}")
                }
                "WMRouter"->{
                    useModule("io.github.meituan-dianping:plugin:${requested.version}")
                }
                else -> {

                }
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            url = uri("https://maven.aliyun.com/repository/public")
        }
        google()
        mavenCentral()
        maven {
            name = "jitpack"
            url = uri("https://jitpack.io")
        }
    }
}


include(":library",":module_base",":app")
rootProject.name = "MyArchitecture"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")