dependencies {
    // only need a direct dependency on these, the rest are transitively discovered
    nodeKotlin(project(":hkclient-kt", configuration = "default"))
}


// define these locations because they are used in multiple places
val ngSrcDir = project.layout.projectDirectory.dir("src/react")
val ngOutDir = project.layout.buildDirectory.dir("react")

kt2ts {
    nodeSrcDirectory.set(ngSrcDir)
    nodeOutDirectory.set(ngOutDir)

    // adding -PngProd to the gradle build command gives us a production build of the angular code
    nodeBuildCommand.set(
            if (project.hasProperty("prod")) {
                listOf("build", "--prod", "--build=${ngOutDir.get()}/dist")
            } else {
                listOf("build","--build=${ngOutDir.get()}/dist")
            }
    )

    // we use a different (to default) name for the kotlin-jvm target
    jvmTargetName.set("jvm")
}

val ktor_version:String by project
val serialization_version:String by project
val coroutines_version:String by project
val redux_version:String by project

//
//// attach the build angular code as a 'resource' so it is added to the jar
project.tasks.getByName("jvmProcessResources").dependsOn("nodeBuild")
kotlin {

    sourceSets {
        val jvmMain by getting {
            resources.srcDir(ngOutDir)
        }
    }

}