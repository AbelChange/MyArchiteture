plugins {
    id("java-gradle-plugin") //会自动引入java-library、gradleApi()
    id("maven-publish") // 上传aar到maven使用
    id("org.jetbrains.kotlin.jvm")
}

gradlePlugin {
    plugins {
        create("myplugin") {
            group = "com.example.plugin"
            id = "test_plugin"
            version = "1.0.1-snapshot"
            implementationClass = "com.example.plugin.MyPlugin"
        }
    }
}
//项目级别配置即可
//afterEvaluate {
// publishing {
// repositories {
// mavenLocal()
// }
// }
//}

dependencies {
//gradle sdk
    implementation(gradleApi())
//groovy sdk
    implementation(localGroovy())
    implementation("com.android.tools.build:gradle:7.4.1")
    implementation("com.android.tools.build:gradle-api:7.4.1")
    implementation("org.ow2.asm:asm:9.1")
    implementation("org.ow2.asm:asm-util:9.1")
    implementation("org.ow2.asm:asm-commons:9.1")
}