//public fun LibraryExtension.configCommon() {
//    compileSdk = Versions.COMPILE_SDK
//
//    defaultConfig {
//        minSdk = Versions.MIN_SDK
//        targetSdk = Versions.TARGET_SDK
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//
//    buildFeatures {
//        viewBinding = true
//    }
//
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
//}


fun Config_module_gradle.configModulePlugin() {
    if (Config.buildModule) {
        plugins {
            id("com.android.application")
            id("kotlin-android")
            id("kotlin-parcelize")
            id("kotlin-kapt")
            id("WMRouter")
        }
    } else {
        //集成运行
        plugins {
            id("com.android.library")
            id("kotlin-android")
            id("kotlin-parcelize")
            id("kotlin-kapt")
        }
    }
}