/*
 * This file is a part of the Configured library
 * Copyright (C) 2023 Deftu (https://deftu.xyz)
 *
 * DO NOT remove or alter copyright notices, or remove this file header.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

import xyz.deftu.gradle.utils.GameSide

plugins {
    `java-library`
    kotlin("jvm")
    `maven-publish`
    id("xyz.deftu.gradle.multiversion")
    id("xyz.deftu.gradle.tools")
    id("xyz.deftu.gradle.tools.loom")
    id("xyz.deftu.gradle.tools.blossom")
    id("xyz.deftu.gradle.tools.resources")
    id("xyz.deftu.gradle.tools.shadow")
}

loomHelper {
    useProperty("elementa.dev", "true", GameSide.CLIENT)
}

fun Dependency?.excludeVitals(): Dependency = apply {
    check(this is ExternalModuleDependency)
    exclude(module = "kotlin-stdlib")
    exclude(module = "kotlin-stdlib-common")
    exclude(module = "kotlin-stdlib-jdk8")
    exclude(module = "kotlin-stdlib-jdk7")
    exclude(module = "kotlin-reflect")
    exclude(module = "annotations")
    exclude(module = "fabric-loader")
}!!

val bundle by configurations.creating {
    if (mcData.isFabric) {
        extendsFrom(configurations["include"])
    } else extendsFrom(configurations["shade"])
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    modApi(bundle(libs.versions.universalcraft.map {
        "gg.essential:universalcraft-${when(mcData.version) {
            11802 -> "1.18.1"
            11605 -> "1.16.2"
            else -> mcData.versionStr
        }}-${mcData.loader.name}:$it"
    }.get()).excludeVitals())
    modApi(bundle(libs.versions.elementa.map {
        "gg.essential:elementa-${when(mcData.version) {
            11902, 11802 -> "1.18.1"
            11605 -> "1.16.2"
            else -> mcData.versionStr
        }}-${mcData.loader.name}:$it"
    }.get()).excludeVitals())

    if (mcData.version >= 11602) {
        val lwjglVersion = "3.2.2"
        bundle("org.lwjgl:lwjgl-tinyfd:$lwjglVersion")
    }

    // We need Fabric API for testing purposes.
    if (mcData.isFabric) modRuntimeOnly(modCompileOnly("net.fabricmc.fabric-api:fabric-api:${when (mcData.version) {
        11902 -> "0.64.0+1.19.2"
        11802 -> "0.59.1+1.18.2"
        11701 -> "0.46.1+1.17"
        11605 -> "0.42.0+1.16"
        11502 -> "0.28.5+1.15"
        else -> throw IllegalArgumentException("Invalid Minecraft version")
    }}")!!)
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}
