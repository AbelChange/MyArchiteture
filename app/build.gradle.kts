import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    id("com.android.application")
    id("WMRouter")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.ablec.myarchitecture"
    compileSdk = Versions.COMPILE_SDK
    defaultConfig {
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders["APP_NAME"] = "appname"
        manifestPlaceholders["UMENG_CHANNEL_NAME"] = "common"

        ndk {
            // Specifies the ABI configurations of your native
            // libraries Gradle should build and package with your app.
            abiFilters += listOf(
                "arm64-v8a",
                "armeabi-v7a"
            )
        }
    }

    signingConfigs {
        create("release") {
            val keystorePropertiesFile = file("keystore/keystore.properties")
            val keystoreProperties = Properties()
            keystoreProperties.load(keystorePropertiesFile.inputStream())
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
            storeFile = file(keystoreProperties.getProperty("storeFile"))
            storePassword = keystoreProperties.getProperty("storePassword")
        }
    }

    buildTypes {
        all {
            signingConfig = signingConfigs.getByName("release")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    flavorDimensions .apply{
        add("server")
        add("market")
    }

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

    androidComponents {
        beforeVariants { builder ->
            val flavorName = builder.flavorName

        }
        afterEvaluate {

        }
    }

    applicationVariants.all {
        outputs.map { it as BaseVariantOutputImpl }
            .forEach { output ->
                val outputFileName = "${"appName"}_${baseName}_${versionName}.${versionCode}.apk"
                output.outputFileName = outputFileName
            }
    }

}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    implementation(projects.moduleBase)
//集成模式
    if (!Config.buildModule) {
        implementation(projects.moduleLogin)
        implementation(project(":module_pay"))
    }
    kapt(libs.hilt.compiler)
    kapt(libs.glide.compiler)
    kapt(Libs.routerCompiler)
    implementation(libs.hilt.android)

    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.10")
    //仿weibo可展开折叠texview
    implementation("com.github.MZCretin:ExpandableTextView:1.6.1")

    implementation("com.google.android.exoplayer:exoplayer-core:2.18.5")

    debugImplementation("com.github.chuckerteam.chucker:library:3.5.2")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:3.5.2")
}