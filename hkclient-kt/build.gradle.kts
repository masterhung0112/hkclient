plugins {
    kotlin("plugin.serialization")
}

val ktor_version:String by project
val serialization_version:String by project
val coroutines_version:String by project
val redux_version:String by project

kotlin {
    js(LEGACY) {
        browser()
    }

    /* Targets configuration omitted.
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("io.ktor:ktor-client-json:$ktor_version")
                implementation("io.ktor:ktor-client-serialization:$ktor_version")
                implementation("org.reduxkotlin:redux-kotlin-threadsafe:$redux_version")
                implementation("org.reduxkotlin:redux-kotlin-thunk:$redux_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serialization_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:$serialization_version")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation("io.ktor:ktor-client-mock:$ktor_version")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutines_version")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
                implementation("io.ktor:ktor-client-apache:$ktor_version")
                implementation("io.ktor:ktor-client-json-jvm:$ktor_version")
                implementation("io.ktor:ktor-client-serialization-jvm:$ktor_version")
                implementation("org.reduxkotlin:redux-kotlin-threadsafe-jvm:$redux_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serialization_version") // JVM dependency

            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation("io.ktor:ktor-client-mock-jvm:$ktor_version")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version")
                implementation("io.ktor:ktor-client-json-jvm:$ktor_version")
                implementation("io.ktor:ktor-client-serialization-jvm:$ktor_version")
                implementation("org.reduxkotlin:redux-kotlin-threadsafe-jvm:$redux_version")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
                implementation("io.ktor:ktor-client-js:$ktor_version")
                implementation("io.ktor:ktor-client-json-js:$ktor_version")
                implementation("io.ktor:ktor-client-serialization-js:$ktor_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core-js:$serialization_version") // JVM dependency
                implementation(npm("text-encoding", "0.7.0"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")
                implementation("io.ktor:ktor-client-js:$ktor_version")
                implementation("io.ktor:ktor-client-json-js:$ktor_version")
                implementation("io.ktor:ktor-client-serialization-js:$ktor_version")
                implementation("io.ktor:ktor-client-mock-js:$ktor_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core-js:$serialization_version") // JVM dependency
                implementation(npm("text-encoding", "0.7.0"))
            }
        }
    }
}

val ngSrcDir = project.layout.projectDirectory.dir("../hkclient-ts/src/react")
val ngOutDir = project.layout.buildDirectory.dir("react")

// configure the kt2ts plugin
kt2ts {
//    jvmTargetName.set("jvm")
    nodeSrcDirectory.set(ngSrcDir)
    nodeOutDirectory.set(ngOutDir)

    classPatterns.set(listOf(
            "com.hungknow.*"
    ))
}