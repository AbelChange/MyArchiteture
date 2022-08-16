plugins {
    id("com.android.application")
    id("WMRouter")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.parcelize")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin") version Versions.navigationVersion
}
// 应用WMRouter插件
apply(plugin = "WMRouter")

android {

    compileSdk = Versions.COMPILE_SDK

    defaultConfig {
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK
        applicationId = "com.ablec.myarchitecture"
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders["qqappid"] = "12220"
        manifestPlaceholders["VIVO_APPID"] = "105489822"
        manifestPlaceholders["VIVO_APPKEY"] = "423a6e7778dacef8a5be2dc5cded70e8"
        manifestPlaceholders["APP_NAME"] = "appname"
        manifestPlaceholders["UMENG_CHANNEL_NAME"] = "common"

        ndk {
            // Specifies the ABI configurations of your native
            // libraries Gradle should build and package with your app.
            abiFilters += listOf(
                "arm64-v8a",
                "armeabi-v7a",
                "x86"
            )
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = false
        }
        debug {
            isMinifyEnabled = false
        }
    }

    flavorDimensions += listOf("server", "market")

    productFlavors {
        //server维度
        create("intranet") {
            dimension = "server"
            buildConfigField("String", "BASE_URL", "\"http://10.20.40.244:39097/\"")
        }
        create("online") {
            dimension = "server"
            buildConfigField("String", "BASE_URL", "\"https://shopping-api.droigroup.com/\"")
        }

        file("./channel").readLines().forEach {
            if (it == "atest") {
                create(it) {
                    dimension = "market"
                    manifestPlaceholders["UMENG_CHANNEL_NAME"] = "test"
                }
            } else {
                create(it) {
                    dimension = "market"
                    manifestPlaceholders["UMENG_CHANNEL_NAME"] = it
                }
            }
        }

    }

}

//尝试抽离
dependencies {

    api(projects.moduleBase)
//    //集成模式
    if (!Config.buildModule) {
//        implementation(project(":module_login"))
//        implementation(projects.module_pay)
//        implementation(projects.module_web)
//        implementation(projects.module_push)
    }
//
    kapt(libs.hilt.compiler)
    kapt(Libs.glideCompiler)
    kapt(Libs.routerCompiler)
    implementation(libs.hilt.android)

    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.7")
    //仿weibo可展开折叠texview
    implementation("com.github.MZCretin:ExpandableTextView:v1.6.1")

    implementation("com.google.android.exoplayer:exoplayer-core:2.15.0")
    //


    debugImplementation("com.github.chuckerteam.chucker:library:3.5.2")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:3.5.2")
}