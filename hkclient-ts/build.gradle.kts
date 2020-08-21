dependencies {
    nodeKotlin(project(":hkclient-kt"))
}

//println("project: %s".format(project.layout.buildDirectory))
// define these locations because they are used in multiple places
val ngSrcDir = project.layout.projectDirectory.dir("./")
val ngOutDir = project.layout.buildDirectory.dir("./")

val coroutines_version:String by project

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

        generateThirdPartyModules {
        register("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version") {
            includeOnly.set(listOf("org.jetbrains.kotlinx:kotlinx-coroutines-core"))
            moduleGroup.set("")
            moduleName.set("kotlinx-coroutines-core")
            mainFileName.set("kotlinx-coroutines-core.js")
            tgtName.set("kotlinx-coroutines-core")
            classPatterns.set(listOf(
                    "kotlinx.coroutines.internal.OpDescriptor",
                    "kotlinx.coroutines.internal.AtomicOp",
                    "kotlinx.coroutines.internal.AtomicDesc",
                    "kotlinx.coroutines.DisposableHandle",
                    "kotlinx.coroutines.channels.Channel",
                    "kotlinx.coroutines.channels.SendChannel",
                    "kotlinx.coroutines.channels.ReceiveChannel",
                    "kotlinx.coroutines.channels.ChannelIterator",
                    "kotlinx.coroutines.selects.SelectInstance",
                    "kotlinx.coroutines.selects.SelectClause1",
                    "kotlinx.coroutines.selects.SelectClause2"
            ))
        }
    }
}

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
