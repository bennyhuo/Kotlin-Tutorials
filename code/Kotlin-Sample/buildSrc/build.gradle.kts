plugins {
    `kotlin-dsl`
    //region
    //id("org.gradle.kotlin.kotlin-dsl") version "2.1.4"
    //endregion
}

repositories {
    maven("https://mirrors.tencent.com/nexus/repository/maven-public/")
}

gradlePlugin {
    plugins {
        create("TestPlugin") {
            id = "com.bennyhuo.test-plugin"
            implementationClass = "TestPlugin"
        }
    }
}