rootProject.name = "hkclient"
pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        google()
        jcenter()
    }
}

include("hkclient-kt")
include("hkclient-ts")