import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm")
}

group = "com.bennyhuo"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://mirrors.tencent.com/nexus/repository/maven-public/")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

tasks.getByName<KotlinCompile>("compileKotlin") {
    kotlinOptions.freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}