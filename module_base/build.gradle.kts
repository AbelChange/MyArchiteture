plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.ablec.module_base"
    defaultConfig {
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true",
                    "room.expandProjection" to "true"
                )
            }
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    api(projects.library)
    kapt(libs.androidx.room.compiler)
    kapt(libs.hilt.compiler)
    kapt(libs.glide.compiler)
    kapt(Libs.routerCompiler)
    //umeng
    api("com.umeng.umsdk:common:9.4.2")
    api("com.umeng.umsdk:asms:1.4.1")

    api("com.umeng.umsdk:share-core:7.1.6")
    api("com.umeng.umsdk:share-wx:7.1.6")
    //QQ官方sdk 3.53及之后版本需要集成okhttp3.x，必选)
    //    qq and wechat
    api("com.tencent.mm.opensdk:wechat-sdk-android-without-mta:6.7.9")

    //腾讯bugly
    api("com.tencent.bugly:crashreport:3.4.4")
    api("com.tencent.bugly:nativecrashreport:3.9.2")

    //x5WebView
    api (Libs.x5WebView)
}