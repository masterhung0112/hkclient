dependencies {
    // only need a direct dependency on these, the rest are transitively discovered
    nodeKotlin(project(":hkclient-kt"))
}

//println("project: %s".format(project.layout.buildDirectory))
// define these locations because they are used in multiple places
val ngSrcDir = project.layout.projectDirectory.dir("./")
val ngOutDir = project.layout.buildDirectory.dir("./")

kt2ts {
    nodeSrcDirectory.set(ngSrcDir)
    nodeOutDirectory.set(ngOutDir)

    // adding -PngProd to the gradle build command gives us a production build of the angular code
//    nodeBuildCommand.set(
//            if (project.hasProperty("prod")) {
//                listOf("build", "--prod", "--build=${ngOutDir.get()}/dist")
//            } else {
//                listOf("build","--build=${ngOutDir.get()}/dist")
//            }
//    )

    // the ':information' module and classes are accessed by reflection (during de/- serialisation)
//    dynamicImport.set(listOf(
//            "${project.group}:information"
//    ))


    // we use a different (to default) name for the kotlin-jvm target
//    jvmTargetName.set("jvm")
}

val ktor_version:String by project
val serialization_version:String by project
val coroutines_version:String by project
val redux_version:String by project

//
//// attach the build angular code as a 'resource' so it is added to the jar
//project.tasks.getByName("jvmProcessResources").dependsOn("nodeBuild")
kotlin {
    sourceSets {
        val jvmMain by getting {
            resources.srcDir(ngOutDir)
        }
    }
}
