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