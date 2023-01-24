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
import gg.essential.elementa.constraints.ChildBasedSizeConstraint
import gg.essential.elementa.constraints.animation.Animations
import gg.essential.elementa.dsl.*
import gg.essential.elementa.effects.OutlineEffect
import xyz.deftu.configured.config.ConfiguredPalette
import xyz.deftu.configured.gui.ConfigOptionComponent
import xyz.deftu.configured.options.Option

class DefaultSwitchComponent(
    override val option: Option
) : ConfigOptionComponent() {
    var toggle: Boolean
        get() = option.invoke().toString().toBoolean()
        set(value) = option.set(value)

    init {
        constrain {
            width = ChildBasedSizeConstraint()
            height = ChildBasedSizeConstraint()
        }

        val background by UIBlock(ConfiguredPalette.getBackground2()).constrain {
            width = 37.5.pixels
            height = 18.75.pixels
        } effect OutlineEffect(
            color = fetchToggleColor(),
            width = 2f
        ) childOf this
        val thumb by UIBlock(fetchToggleColor()).constrain {
            x = 1.pixel(alignOpposite = toggle)
            y = 1.pixel
            width = 50.percent - 2.pixels
            height = 100.percent - 2.pixels
        } childOf background

        onMouseClick {
            toggle = !toggle
            thumb.animate {
                (background.effects[0] as OutlineEffect)::color.animate(Animations.OUT_EXP, 0.5f, fetchToggleColor())
                setColorAnimation(Animations.OUT_EXP, 0.5f, fetchToggleColor().toConstraint())
                setXAnimation(Animations.OUT_EXP, 0.5f, 1.pixel(alignOpposite = toggle))
            }
        }
    }

    private fun fetchToggleColor() =
        if (toggle) ConfiguredPalette.getPrimary() else ConfiguredPalette.getDisabled()
}
