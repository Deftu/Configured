import groovy.lang.MissingPropertyException

pluginManagement {
    repositories {
        // Repositories
        maven("https://maven.enhancedpixel.xyz/releases")
        maven("https://maven.fabricmc.net")
        maven("https://maven.architectury.dev/")
        maven("https://maven.minecraftforge.net")
        maven("https://repo.essential.gg/repository/maven-public")
        maven("https://server.bbkr.space/artifactory/libs-release/")
        maven("https://jitpack.io/")

        // Snapshots
        maven("https://maven.enhancedpixel.xyz/snapshots")
        mavenLocal()

        // Default repositories
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        val kotlin = "1.6.21"
        kotlin("jvm") version(kotlin)
        kotlin("plugin.serialization") version(kotlin)

        val egpt = "1.13.8"
        id("xyz.enhancedpixel.gradle.multiversion-root") version(egpt)
    }
}

val projectName: String = extra["mod.name"]?.toString() ?: throw MissingPropertyException("mod.name is not set.")
rootProject.name = projectName
rootProject.buildFileName = "build.gradle.kts"

listOf(
    "1.8.9-forge",
    "1.12.2-forge",
    "1.15.2-forge",
    "1.16.5-forge",
    "1.16.5-fabric",
    "1.17.1-fabric",
    "1.17.1-forge",
    "1.18.2-fabric",
    "1.18.2-forge",
    "1.19.2-fabric",
    "1.19.2-forge"
).forEach { version ->
    include(":$version")
    project(":$version").apply {
        projectDir = file("versions/$version")
        buildFileName = "../../version.gradle.kts"
    }
}