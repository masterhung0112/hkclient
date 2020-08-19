dependencies {
    // only need a direct dependency on these, the rest are transitively discovered
//    project(":hkclient-kt")
}

plugins {
    id("net.akehurst.kotlin.kt2ts")
}

// define these locations because they are used in multiple places
val ngSrcDir = project.layout.projectDirectory.dir("./")
val ngOutDir = project.layout.buildDirectory.dir("./")

//kt2ts {
//    nodeSrcDirectory.set(ngSrcDir)
//    nodeOutDirectory.set(ngOutDir)
//
//}

//
//// attach the build angular code as a 'resource' so it is added to the jar
//project.tasks.getByName("jvmProcessResources").dependsOn("nodeBuild")
//kotlin {
//    sourceSets {
//        val jvm8Main by getting {
//            resources.srcDir(ngOutDir)
//        }
//    }
//}