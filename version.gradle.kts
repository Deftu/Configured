import xyz.unifycraft.gradle.utils.GameSide

plugins {
    kotlin("jvm")
    java
    id("xyz.unifycraft.gradle.multiversion")
    id("xyz.unifycraft.gradle.tools")
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
    api(libs.versions.elementa.map {
        "gg.essential:elementa-${when(mcData.version) {
            11900 -> "1.18.1"
            else -> mcData.versionStr
        }}-${mcData.loader.name}:$it"
    }.get()) {
        exclude(module = "kotlin-reflect")
        exclude(module = "kotlin-stdlib-jdk8")
    }
}

afterEvaluate {
    publishing.publications.getByName<MavenPublication>("mavenJava") {
        group = modData.group
        artifactId = base.archivesName.get()
        version = modData.version
    }
}
