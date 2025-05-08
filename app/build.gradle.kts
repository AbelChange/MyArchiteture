import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.safeargs)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
    //自定义插件
    id("time_cost") // ← 注意：要和插件模块中 gradlePlugin {} 中的 id 保持一致
}

timeCostConfig {
    packageNames = listOf("com.ablec.myarchitecture") // 设置要插桩的包名
    methodNames = listOf("onCreate", "onResume")    // 设置要插桩的方法名
    logTag = "TAG_TimeCost"                           // 设置日志 tag
    thresholdTime = 500L                          // 慢函数时间阈值
}


android {

    namespace = "com.ablec.myarchitecture"
    compileSdk = 35
    defaultConfig {
        minSdk = 28
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

        kapt {
            arguments {
                arg("AROUTER_MODULE_NAME", project.name)
            }
        }

    }
    sourceSets {
        getByName("main") {
            aidl.srcDirs("src/main/aidl")
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
        aidl = true
        viewBinding = true
        buildConfig = true
    }

    flavorDimensions.apply {
        add("server")
        add("market")
    }
    //ARouter & Hilt https://github.com/alibaba/ARouter/issues/1051
    hilt {
        enableExperimentalClasspathAggregation = true
        enableAggregatingTask = false
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
    val buildModule = providers.gradleProperty("buildModule").get().toBoolean()
    if (!buildModule) {
        implementation(projects.moduleLogin)
        implementation(project(":module_pay"))
        implementation(project(":module_compose"))
        implementation(project(":module_native"))
        implementation(project(":module_login"))
    }
    kapt(libs.glide.compiler)
    kapt(libs.arouter.compiler)
    kapt(libs.hilt.compiler)

    implementation(libs.hilt.android)

    implementation("com.github.markzhai:blockcanary-android:1.5.0")

    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.10")
    releaseImplementation("com.squareup.leakcanary:leakcanary-android-process:2.10")
    //仿weibo可展开折叠texview
    implementation("com.google.android.exoplayer:exoplayer-core:2.18.5")

    debugImplementation("com.github.chuckerteam.chucker:library:3.5.2")
    releaseImplementation("com.github.chuckerteam.chucker:library-no-op:3.5.2")
    implementation ("jp.wasabeef:recyclerview-animators:4.0.2")
    implementation ("com.bytedance.tools.codelocator:codelocator-core:2.0.3")
    implementation("com.squareup.okhttp3:mockwebserver:4.10.0")
}