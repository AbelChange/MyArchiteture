plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    resourcePrefix("module_login")

    namespace = "com.ablec.module_login"

    buildFeatures {
        viewBinding = true
    }

    kapt {
        arguments {
            arg("AROUTER_MODULE_NAME", project.name)
        }
    }
}


dependencies {
    implementation(projects.moduleBase)
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    kapt(libs.hilt.compiler)
    kapt(libs.arouter.compiler)
}