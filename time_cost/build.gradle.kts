plugins {
    id("java-gradle-plugin") // 会自动引入 java-library、gradleApi()
    id("maven-publish") // 上传到 Maven 使用
    id("org.jetbrains.kotlin.jvm") // 如果是 Kotlin 插件
}

val sourceJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().java.srcDirs)
}

gradlePlugin {
    plugins {
        create("timeCostPlugin") {
            group = "com.ablec.plugin"
            id = "time_cost"
            version = "1.0.1-SNAPSHOT"
            implementationClass = "com.ablec.plugin.TimeCostPlugin"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("timeCostPlugin") {
            from(components["java"])
            groupId = "com.ablec.plugin"
            artifactId = "time_cost"
            version = "1.0.1-SNAPSHOT"
            artifact(sourceJar.get())
            pom {
                name.set("Time Cost Plugin")
                description.set("A Gradle plugin for time cost monitoring.")
                url.set("https://github.com/yourusername/time_cost")
                scm {
                    url.set("https://github.com/yourusername/time_cost")
                }
            }
        }
    }
}

dependencies {
    // gradle sdk
    implementation(gradleApi())
    // groovy sdk
    implementation(localGroovy())
    // Android Gradle Plugin
    implementation("com.android.tools.build:gradle:8.6.0")
    implementation("com.android.tools.build:gradle-api:8.6.0")
    // ASM 库，用于字节码操作
    implementation("org.ow2.asm:asm:9.1")
    implementation("org.ow2.asm:asm-util:9.1")
    implementation("org.ow2.asm:asm-commons:9.1")
}