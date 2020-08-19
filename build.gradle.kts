group = "org.example"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("multiplatform") version "1.4.0" apply false
    kotlin("plugin.serialization") version "1.4.0" apply false
    id("net.akehurst.kotlin.kt2ts") version "1.6.0" apply false
}

repositories {
    google()
    mavenCentral()
    jcenter()
    maven("https://kotlin.bintray.com/kotlinx")
    maven("https://kotlin.bintray.com/ktor")
}