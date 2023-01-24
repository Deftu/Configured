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

package xyz.deftu.configured.gui.original

import gg.essential.elementa.constraints.ChildBasedSizeConstraint
import gg.essential.elementa.dsl.*
import xyz.deftu.configured.gui.ConfigOptionComponent
import xyz.deftu.configured.gui.components.InputBoxComponent
import xyz.deftu.configured.options.Option

class DefaultTextComponent(
    override val option: Option
) : ConfigOptionComponent() {
    var text: String
        get() = option.invoke().toString()
        set(value) = option.set(value)

    val protected: Boolean
        get() = option.attributes["protected"]?.toString()?.toBoolean() ?: false
    val limit: Int
        get() = option.attributes["limit"]?.toString()?.toInt() ?: -1

    init {
        constrain {
            width = ChildBasedSizeConstraint()
            height = ChildBasedSizeConstraint()
        }

        val input by InputBoxComponent(
            defaultValue = text,
            protected = protected,
            limit = limit
        ).constrain {
            width = 175.pixels
            height = 25.pixels
        } childOf this
        input.onValueChanged {
            text = it
        }
    }
}
