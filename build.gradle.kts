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

plugins {
    id("xyz.deftu.gradle.multiversion-root")
}

preprocess {
    val forge11902 = createNode("1.19.2-forge", 11902, "srg")
    val fabric11902 = createNode("1.19.2-fabric", 11902, "yarn")
    val forge11802 = createNode("1.18.2-forge", 11802, "srg")
    val fabric11802 = createNode("1.18.2-fabric", 11802, "yarn")
    val forge11701 = createNode("1.17.1-forge", 11701, "yarn")
    val fabric11701 = createNode("1.17.1-fabric", 11701, "yarn")
    val fabric11605 = createNode("1.16.5-fabric", 11605, "yarn")
    val forge11605 = createNode("1.16.5-forge", 11605, "srg")
    val forge11502 = createNode("1.15.2-forge", 11502, "srg")
    val forge11202 = createNode("1.12.2-forge", 11202, "srg")
    val forge10809 = createNode("1.8.9-forge", 10809, "srg")

    forge11902.link(fabric11902)
    fabric11902.link(fabric11802)
    forge11802.link(fabric11802)
    fabric11802.link(fabric11701)
    forge11701.link(fabric11701)
    fabric11701.link(fabric11605)
    fabric11605.link(forge11605)
    forge11605.link(forge11502)
    forge11502.link(forge11202)
    forge11202.link(forge10809)
}
