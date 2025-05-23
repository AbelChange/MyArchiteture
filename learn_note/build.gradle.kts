plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}



dependencies {
    implementation(kotlin("reflect"))
    implementation("com.google.code.gson:gson:2.10.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.3")
}