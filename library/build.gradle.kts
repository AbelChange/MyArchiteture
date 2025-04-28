plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kapt)
}

android {
    namespace = "com.ablec.library"
    compileSdk = 35

    defaultConfig {
        minSdk = 28
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.androidx.annotation)
    api(libs.androidx.constraintlayout)
    api(libs.material)
    api(libs.androidx.activity)
    api(libs.androidx.fragment)
    api(libs.androidx.lifecycle.viewmodel)
    api(libs.androidx.lifecycle.runtime)

    api(libs.androidx.recyclerview)
    api(libs.androidx.startup.runtime)

    api(libs.hilt.android)
    api(libs.gson)
    api(libs.timber)

//    api(platform(libs.kotlin.bom))

    api(libs.bundles.retrofit)

    api(libs.androidx.room.runtime)
    api(libs.androidx.room.ktx)

    api(libs.androidx.paging.runtime)
    api(libs.androidx.swiperefreshlayout)

    api(libs.androidx.navigation.fragment)
    api(libs.androidx.navigation.ui.ktx)
    api(libs.androidx.datastore.preferences)

    //glide
    api(libs.glide)
    kapt(libs.glide.compiler)
    //util
    api("com.blankj:utilcodex:1.31.1")

    api("io.github.jeremyliao:live-event-bus-x:1.8.0")
    //brvah
    api("com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.6")

    api("com.tencent:mmkv-static:1.2.10")
    //load
    api("com.kingja.loadsir:loadsir:1.3.8")

    //下拉刷新
    api("io.github.scwang90:refresh-layout-kernel:2.0.5")
    api("io.github.scwang90:refresh-header-classics:2.0.5")
    //权限处理
    api("io.reactivex.rxjava3:rxjava:3.1.4")
    api("io.reactivex.rxjava3:rxandroid:3.0.0")
    api("com.guolindev.permissionx:permissionx:1.6.3")

}
