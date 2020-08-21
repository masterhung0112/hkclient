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

val version_project: String by project
val group_project = "${rootProject.name}"

allprojects {

    repositories {
        mavenCentral()
        jcenter()
    }

    group = group_project
    version = version_project

    buildDir = File(rootProject.projectDir, ".gradle-build/${project.name}")

}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.multiplatform")
    apply(plugin = "net.akehurst.kotlin.kt2ts")
    apply(from = "../workaround_to_use_1_4_libs_in_1_3.gradle.kts")

    group = group_project
    version = version_project

    configure<org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension> {
        sourceSets {
            all {
                languageSettings.apply {
                    useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
                }
            }
        }

        // we want to build for a JS target
        js() {
            browser()
            binaries.executable()
        }
        // we want to build for a jvm target
        jvm() {
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