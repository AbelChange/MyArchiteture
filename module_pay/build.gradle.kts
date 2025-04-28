plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    resourcePrefix("module_pay")
    namespace = "com.ablec.module_pay"
    compileSdk = 35

    defaultConfig {
        minSdk = 28
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    kapt {
        arguments {
            arg("AROUTER_MODULE_NAME", project.name)
        }
    }

}


dependencies {
    implementation(projects.moduleBase)
    kapt(libs.arouter.compiler)
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
}