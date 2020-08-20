group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
    jcenter()
    maven("https://kotlin.bintray.com/kotlinx")
    maven("https://kotlin.bintray.com/ktor")
}

plugins {
    kotlin("multiplatform") version "1.4.0" apply false
    kotlin("plugin.serialization") version "1.4.0" apply false
    id("net.akehurst.kotlin.kt2ts") version "1.6.1" apply false
}

allprojects {

    repositories {
        mavenCentral()
        jcenter()
    }

    val version_project: String by project
    val group_project = "com.hungknow"

    group = group_project
    version = version_project

    buildDir = File(rootProject.projectDir, ".gradle-build/${project.name}")

}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.multiplatform")
    apply(plugin = "net.akehurst.kotlin.kt2ts")

    configure<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension> {
        sourceSets {
            all {
                languageSettings.apply {
                    useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
                }
            }
        }

        // we want to build for a JS target
        js("js") {
            browser()
        }
        // we want to build for a jvm target
        jvm("jvm") {
            // by default kotlin uses JavaVersion 1.6
//            val main by compilations.getting {
//                kotlinOptions {
//                    jvmTarget = JavaVersion.VERSION_1_8.toString()
//                    freeCompilerArgs = kotlin.collections.listOf("-Xinline-classes")
//                }
//            }
//            val test by compilations.getting {
//                kotlinOptions {
//                    jvmTarget = JavaVersion.VERSION_1_8.toString()
//                }
//            }
        }
    }
}