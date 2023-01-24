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

import groovy.lang.MissingPropertyException

pluginManagement {
    repositories {
        // Repositories
        maven("https://maven.deftu.xyz/releases")
        maven("https://maven.fabricmc.net")
        maven("https://maven.architectury.dev/")
        maven("https://maven.minecraftforge.net")
        maven("https://repo.essential.gg/repository/maven-public")
        maven("https://server.bbkr.space/artifactory/libs-release/")
        maven("https://jitpack.io/")

        // Snapshots
        maven("https://maven.deftu.xyz/snapshots")
        mavenLocal()

        // Default repositories
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        val kotlin = "1.6.21"
        kotlin("jvm") version(kotlin)
        kotlin("plugin.serialization") version(kotlin)

        val dgt = "1.4.0"
        id("xyz.deftu.gradle.multiversion-root") version(dgt)
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