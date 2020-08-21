pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        google()
        jcenter()
    }
}

rootProject.name = "hkclient"
include("hkclient-kt")
include("hkclient-ts")