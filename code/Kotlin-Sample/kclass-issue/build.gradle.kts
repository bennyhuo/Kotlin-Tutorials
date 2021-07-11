plugins {
    java
    kotlin("jvm")
}

group = "com.bennyhuo.kotlin.hello"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}