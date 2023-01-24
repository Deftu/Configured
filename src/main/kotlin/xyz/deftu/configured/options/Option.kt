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

import net.minecraft.client.resources.I18n

data class Option(
    val name: String,
    private val localizedNameKey: String,
    val description: String,
    val category: String,
    private val localizedCategoryKey: String,
    val default: Any,
    var hidden: Boolean,
    val tags: List<String>,
    val type: OptionType,
    val attributes: Map<String, Any>,
    private val getter: () -> Any,
    private val setter: (Any) -> Unit
) {
    val localizedName: String
        get() = I18n.format(localizedNameKey)
    val localizedCategory: String
        get() = I18n.format(localizedCategoryKey)

    fun invoke() =
        getter()
    fun set(value: Any) =
        setter(value)

    companion object {
        const val DEFAULT_CATEGORY = "General"
        const val DEFAULT_HIDDEN = false
    }
}
