buildscript {
    repositories {
        maven {
            name = "jcenter托管"
            url = uri("https://maven.aliyun.com/repository/public")
        }
        maven {
            name = "google备选"
            url = uri("https://maven.aliyun.com/repository/google")
        }
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.androidGradlePlugin}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.dagger}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationVersion}")
        classpath("io.github.meituan-dianping:plugin:${Versions.wmRouter}")
    }
}

