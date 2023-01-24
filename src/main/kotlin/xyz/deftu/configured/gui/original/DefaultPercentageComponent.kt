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
import xyz.deftu.configured.gui.components.NumericInputBoxComponent
import xyz.deftu.configured.options.Option
import java.text.DecimalFormat

/**
 * Maths for slider functionality adapted from EvergreenHUD under GPL 3.0
 * https://github.com/isXander/EvergreenHUD/blob/86b881510698d9e75b519ada0747da12d89037ff/LICENSE.md
 */
class DefaultPercentageComponent(
    override val option: Option
) : ConfigOptionComponent() {
    private val numberFormat = DecimalFormat("0.00")

    var number: Float
        get() = option.invoke().toString().toFloat()
        set(value) = option.set(value)

    val minimum: Int
        get() = option.attributes["min"]?.toString()?.toInt() ?: 0
    val maximum: Int
        get() = option.attributes["max"]?.toString()?.toInt() ?: Int.MAX_VALUE

    private var draggingOffset = 0f
    private var mouseDown = false

    init {
        constrain {
            width = ChildBasedSizeConstraint()
            height = 25.pixels
        }

        val slider by UIContainer().constrain {
            y = CenterConstraint()
            width = 150.pixels
            height = 12.5.pixels
        } childOf this
        val track by UIBlock(ConfiguredPalette.getBackground2()).constrain {
            y = CenterConstraint()
            width = 150.pixels
            height = 5.pixels
        } effect OutlineEffect(
            color = ConfiguredPalette.getPrimary(),
            width = 2f
        ) childOf slider
        val thumb by UIBlock(ConfiguredPalette.getBackground2()).constrain {
            x = basicXConstraint {
                (track.getLeft() + track.getWidth() * (number / 100)) - (it.getWidth() / 2)
            }
            y = CenterConstraint()
            width = 12.5.pixels
            height = 12.5.pixels
        } effect OutlineEffect(
            color = ConfiguredPalette.getPrimary(),
            width = 2f
        ) childOf slider

        val input by NumericInputBoxComponent(
            value = numberFormat.format(number).toFloat(),
            min = minimum,
            max = maximum
        ).constrain {
            x = SiblingConstraint(12.5f)
            width = 35.pixels
            height = 25.pixels
        } childOf this
        input.textInput.onUpdate {
            val num = it.toFloatOrNull() ?: return@onUpdate
            number = num
        }

        thumb.onMouseClick {
            draggingOffset = it.relativeX - thumb.getWidth() / 2
            mouseDown = true
            it.stopPropagation()
        }.onMouseRelease {
            draggingOffset = 0f
            mouseDown = false
        }.onMouseDrag { mouseX, _, _ ->
            if (!mouseDown) return@onMouseDrag
            val clamped = (mouseX + thumb.getLeft() - draggingOffset).coerceIn(track.getLeft()..track.getRight())
            val percentage = ((clamped - track.getLeft()) / track.getWidth()).coerceIn(0f..1f)
            number = percentage * 100
            input.apply(numberFormat.format(number))
        }
    }
}
