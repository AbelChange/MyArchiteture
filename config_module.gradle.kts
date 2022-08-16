if (Config.buildModule) {
    plugins {
        id("com.android.library")
        id("kotlin-android")
        id("kotlin-parcelize")
        kotlin("kapt")
    }
} else {
    //集成运行
    plugins {
        id("com.android.application")
        id("WMRouter")
        id("com.android.library")
        id("kotlin-android")
        id("kotlin-parcelize")
        kotlin("kapt")
    }
}



android {

    compileSdk = Versions.COMPILE_SDK

    defaultConfig {
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    sourceSets {
        main {
            jniLibs.srcDirs = ["libs"]
            if (Config.buildModule) {
                //独立运行
                manifest.srcFile("src/main/debug/AndroidManifest.xml")
            } else {
                //合并到主工程
                manifest.srcFile("src/main/AndroidManifest.xml")
                resources {
                    exclude("src/main/debug/*")
                }
            }
        }
    }

}


