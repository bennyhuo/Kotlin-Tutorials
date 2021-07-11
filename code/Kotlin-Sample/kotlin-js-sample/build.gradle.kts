plugins {
    id("org.jetbrains.kotlin.js")
}

group = "com.bennyhuo.kotlin.hello"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(kotlin("stdlib-js"))
}

kotlin {
//    js(IR) {
//        moduleName = "kotlin-js-ir"
//        binaries.executable()
//        nodejs()
//    }

    js {
        moduleName = "kotlin-js-legacy"
        binaries.executable()
        nodejs()
    }
}