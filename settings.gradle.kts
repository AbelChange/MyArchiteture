dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
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
        maven {
            name = "jitpack"
            url = uri("https://jitpack.io")
        }
    }
}

include(":app")
include(":library")
include(":module_base")

include(":module_login")
include(":module_pay")

//include(":module_pay")



rootProject.name = "MyArchitecture"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":learn_note")
