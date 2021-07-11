plugins {
    kotlin("multiplatform")
}

group = "com.bennyhuo.kotlin.hello"
version = "1.0-SNAPSHOT"

kotlin {
    /* Targets configuration omitted. 
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        jvm()
        js{
            nodejs()
        }
        mingwX64 {
            binaries {
                executable {
                    // Binary configuration.
                    entryPoint = "com.bennyhuo.kotlin.sample.main"
                }
            }
        }
    }
}