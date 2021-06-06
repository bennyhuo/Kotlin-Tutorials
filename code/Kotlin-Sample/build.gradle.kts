plugins {
    java
    kotlin("jvm") version "1.5.10"
}

group = "com.bennyhuo.kotlin.hello"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://mirrors.tencent.com/nexus/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.bennyhuo:portable-android-handler:1.0")
    implementation("io.reactivex.rxjava3:rxjava:3.0.13")


    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}