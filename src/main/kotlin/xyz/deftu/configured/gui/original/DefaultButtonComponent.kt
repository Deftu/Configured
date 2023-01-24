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
import gg.essential.elementa.components.UIText
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.ChildBasedSizeConstraint
import gg.essential.elementa.constraints.animation.Animations
import gg.essential.elementa.dsl.*
import gg.essential.elementa.effects.OutlineEffect
import xyz.deftu.configured.config.ConfiguredPalette
import xyz.deftu.configured.gui.ConfigOptionComponent
import xyz.deftu.configured.options.Option

class DefaultButtonComponent(
    override val option: Option
) : ConfigOptionComponent() {
    val text: String
        get() = option.attributes["text"]?.toString() ?: "Click me!"

    init {
        constrain {
            width = ChildBasedSizeConstraint()
            height = ChildBasedSizeConstraint()
        }

        val outline = OutlineEffect(
            color = ConfiguredPalette.getButton(),
            width = 1f
        )
        val background by UIBlock(ConfiguredPalette.getBackground()).constrain {
            width = 100.pixels
            height = 30.pixels
        } effect outline childOf this
        val text by UIText(this.text).constrain {
            x = CenterConstraint()
            y = CenterConstraint()
        } childOf background

        onMouseEnter {
            outline::color.animate(Animations.OUT_EXP, 0.5f, ConfiguredPalette.getButtonFaded())
        }.onMouseLeave {
            outline::color.animate(Animations.OUT_EXP, 0.5f, ConfiguredPalette.getButton())
        }.onMouseClick {
            option.invoke()
        }
    }
}
