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

import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.components.UIContainer
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.ChildBasedSizeConstraint
import gg.essential.elementa.constraints.SiblingConstraint
import gg.essential.elementa.dsl.*
import gg.essential.elementa.effects.OutlineEffect
import xyz.deftu.configured.config.ConfiguredPalette
import xyz.deftu.configured.gui.ConfigOptionComponent
import xyz.deftu.configured.gui.components.createCaretIcon
import xyz.deftu.configured.gui.components.NumericInputBoxComponent
import xyz.deftu.configured.gui.effects.RotateEffect
import xyz.deftu.configured.options.Option

class DefaultIntegerComponent(
    override val option: Option
) : ConfigOptionComponent() {
    // Data
    var number: Int
        get() = option.invoke().toString().toInt()
        set(value) = option.set(value)

    val minimum: Int
        get() = option.attributes["min"]?.toString()?.toInt() ?: 0
    val maximum: Int
        get() = option.attributes["max"]?.toString()?.toInt() ?: Int.MAX_VALUE

    // Components
    private lateinit var inputBox: NumericInputBoxComponent

    init {
        constrain {
            width = ChildBasedSizeConstraint()
            height = 32.5.pixels
        }

        val operationContainer by UIContainer().constrain {
            width = 25.pixels
            height = 32.5.pixels
        } childOf this

        val addBackground by UIBlock(ConfiguredPalette.getBackground2()).constrain {
            width = 25.pixels
            height = 12.5.pixels
        } effect OutlineEffect(
            color = ConfiguredPalette.getPrimary(),
            width = 2f
        ) childOf operationContainer
        addBackground.onMouseClick {
            if (number >= maximum) return@onMouseClick
            number += 1
            val numStr = number.toString()
            inputBox.validate(numStr)
            inputBox.apply(numStr)
        }
        val addCaret by createCaretIcon().constrain {
            x = CenterConstraint()
            y = CenterConstraint()
            width = 12.pixels
            height = 6.5.pixels
            color = ConfiguredPalette.getText().toConstraint()
        } effect RotateEffect(180f) childOf addBackground

        val removeBackground by UIBlock(ConfiguredPalette.getBackground2()).constrain {
            y = SiblingConstraint(7.5f)
            width = 25.pixels
            height = 12.5.pixels
        } effect OutlineEffect(
            color = ConfiguredPalette.getPrimary(),
            width = 2f
        ) childOf operationContainer
        removeBackground.onMouseClick {
            if (number <= minimum) return@onMouseClick
            number -= 1
            val numStr = number.toString()
            inputBox.validate(numStr)
            inputBox.apply(numStr)
        }
        val removeCaret by createCaretIcon().constrain {
            x = CenterConstraint()
            y = CenterConstraint()
            width = 12.pixels
            height = 6.5.pixels
            color = ConfiguredPalette.getText().toConstraint()
        } childOf removeBackground

        val input by NumericInputBoxComponent(
            value = number,
            min = minimum,
            max = maximum,
        ).constrain {
            x = SiblingConstraint(6f)
            y = CenterConstraint()
            width = 35.pixels
            height = 25.pixels
        } childOf this
        input.textInput.onUpdate {
            val num = it.toIntOrNull() ?: return@onUpdate
            number = num
        }
        inputBox = input
    }
}
