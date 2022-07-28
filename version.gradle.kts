import xyz.unifycraft.gradle.utils.GameSide

plugins {
    kotlin("jvm")
    java
    id("xyz.unifycraft.gradle.multiversion")
    id("xyz.unifycraft.gradle.tools")
    id("xyz.unifycraft.gradle.tools.resources")
    id("xyz.unifycraft.gradle.tools.blossom")
    `maven-publish`
}

base.archivesName.set("${modData.name}-${mcData.versionStr}-${mcData.loader.name}".toLowerCase())

loomHelper {
    disableRunConfigs(GameSide.SERVER)
    useProperty("elementa.dev", "true", GameSide.CLIENT)
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    modApi(libs.versions.elementa.map {
        "gg.essential:elementa-${when(mcData.version) {
            11900 -> "1.18.1"
            else -> mcData.versionStr
        }}-${mcData.loader.name}:$it"
    }.get()) {
        exclude(module = "kotlin-reflect")
        exclude(module = "kotlin-stdlib-jdk8")
    }

    // We need Fabric API for testing purposes.
    if (mcData.isFabric) modRuntimeOnly(modCompileOnly("net.fabricmc.fabric-api:fabric-api:${when (mcData.version) {
        11404 -> "0.4.3+build.247-1.14"
        11502 -> "0.5.1+build.294-1.15"
        11601 -> "0.14.0+build.371-1.16"
        11602 -> "0.17.1+build.394-1.16"
        11701 -> "0.38.1+1.17"
        11801 -> "0.46.4+1.18"
        11900 -> "0.57.0+1.19"
        else -> throw IllegalArgumentException("Invalid Minecraft version")
    }}")!!)
}

afterEvaluate {
    publishing.publications.getByName<MavenPublication>("mavenJava") {
        group = modData.group
        artifactId = base.archivesName.get()
        version = modData.version
    }
}
