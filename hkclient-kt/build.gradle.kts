plugins {
    kotlin("multiplatform") version "1.3.72"
    id("net.akehurst.kotlin.kt2ts") version("1.6.0")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    jcenter()
    maven("https://kotlin.bintray.com/kotlinx")
    maven("https://kotlin.bintray.com/ktor")
}

kotlin {
    js("js") {
        browser()
    }
    jvm() {

    }
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
    }
}

// configure the kt2ts plugin
kt2ts {
    classPatterns.set(listOf(
            "hkclient.*"
    ))
}