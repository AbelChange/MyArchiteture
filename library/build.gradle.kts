plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {

    compileSdk = Versions.COMPILE_SDK

    defaultConfig {
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api(libs.androidx.core)
    api(libs.androidx.appcompat)
    api(libs.androidx.annotation)
    api(libs.androidx.constraintlayout)
    api(libs.material)

    api(libs.androidx.fragment)
    api(libs.androidx.lifecycle.viewmodel)
    api(libs.androidx.lifecycle.runtime)

    api(libs.androidx.recyclerview)
    api(libs.androidx.startup)

    api(libs.hilt.android)
    api(libs.gson)
    api(libs.timber)


    api(libs.bundles.retrofit)

    api(libs.androidx.room.runtime)
    api(libs.androidx.room.ktx)

    api(libs.androidx.paging.runtime)
    api(libs.androidx.swiperefreshlayout)

    api(libs.androidx.navigation.fragment)
    api(libs.androidx.navigation.ui)
    api(libs.androidx.navigation.ui)
    api(libs.androidx.datastore.preferences)

    //glide
    api(Libs.glide)
    kapt(Libs.glideCompiler)


    //Router
    api("io.github.meituan-dianping:router:${Versions.wmRouter}")
//    //util
    api("com.blankj:utilcodex:1.31.0")
    api("io.github.jeremyliao:live-event-bus-x:${Versions.eventBus}")
    //brvah
    api("com.github.CymChad:BaseRecyclerViewAdapterHelper:${Versions.BRVAH_Version}")

    //适配
    api("me.jessyan:autosize:${Versions.autosizeVersion}")
    api("com.tencent:mmkv-static:${Versions.mmkvVersion}")
    //load
    api("com.kingja.loadsir:loadsir:1.3.8")

    //权限处理
    api("io.reactivex.rxjava3:rxjava:3.1.4")
    api("io.reactivex.rxjava3:rxandroid:3.0.0")
    api("com.github.tbruyelle:rxpermissions:0.12")
    //下拉刷新
    api("io.github.scwang90:refresh-layout-kernel:2.0.5")
    api("io.github.scwang90:refresh-header-classics:2.0.5")


}