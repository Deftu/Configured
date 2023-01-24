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

package xyz.deftu.configured.options

import java.awt.Color
import java.io.File

enum class OptionType(
    val type: Class<*>,
    val serializable: Boolean
) {
    SWITCH(Boolean::class.java, true),
    TEXT(String::class.java, true),
    PERCENTAGE(Float::class.java, true),
    INTEGER(Int::class.java, true),
    DROPDOWN(Int::class.java, true),
    COLOR(Color::class.java, true),
    FILE(File::class.java, true),
    BUTTON(Nothing::class.java, false)
}
