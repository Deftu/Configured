pluginManagement {
    repositories {
        // Default repositories
        gradlePluginPortal()
        mavenCentral()

        // Repositories
        maven("https://maven.unifycraft.xyz/releases")
        maven("https://maven.fabricmc.net")
        maven("https://maven.architectury.dev/")
        maven("https://maven.minecraftforge.net")
        maven("https://repo.essential.gg/repository/maven-public")
        maven("https://server.bbkr.space/artifactory/libs-release/")
        maven("https://jitpack.io/")

        // Snapshots
        maven("https://maven.unifycraft.xyz/snapshots")
        maven("https://s01.oss.sonatype.org/content/groups/public/")
        mavenLocal()
    }

    plugins {
        id("xyz.unifycraft.gradle.multiversion-root") version("1.10.1")
    }
}

val projectName: String = extra["mod.name"]?.toString()
    ?: throw groovy.lang.MissingPropertyException("mod.name is not set.")
rootProject.name = projectName
rootProject.buildFileName = "build.gradle.kts"

listOf(
    "1.8.9-forge",
    "1.12.2-forge",
    "1.15.2-forge",
    "1.16.2-forge",
    "1.16.2-fabric",
    "1.17.1-fabric",
    "1.17.1-forge",
    "1.18.1-fabric",
    "1.18.1-forge",
    "1.19-fabric"
).forEach { version ->
    include(":$version")
    project(":$version").apply {
        projectDir = file("versions/$version")
        buildFileName = "../../version.gradle.kts"
    }
}