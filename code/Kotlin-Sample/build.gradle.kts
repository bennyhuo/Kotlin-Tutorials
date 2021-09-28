import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        maven("https://mirrors.tencent.com/nexus/repository/maven-public/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

plugins {
    java
    kotlin("jvm") version kotlinVersion
    id("test-plugin")
    id("com.bennyhuo.test-plugin")
}

group = "com.bennyhuo.kotlin.hello"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        maven("https://mirrors.tencent.com/nexus/repository/maven-public/")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.5.0")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.2.1")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.bennyhuo:portable-android-handler:1.0")
    implementation("io.reactivex.rxjava3:rxjava:3.0.13")

    implementation(project(":opt-in-sample"))
    implementation(project(":deprecated-sample"))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn", "-Xextended-compiler-checks")
    jvmTarget = "16"
    useIR = true
}

java {
    targetCompatibility = JavaVersion.VERSION_16
    sourceCompatibility = JavaVersion.VERSION_16
}

tasks.withType<JavaCompile>().forEach {
    it.options.compilerArgs.add("--enable-preview")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}