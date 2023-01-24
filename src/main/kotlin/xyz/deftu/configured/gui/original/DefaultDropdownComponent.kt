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

import gg.essential.elementa.components.ScrollComponent
import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.components.UIText
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.SiblingConstraint
import gg.essential.elementa.dsl.*
import gg.essential.elementa.effects.OutlineEffect
import gg.essential.elementa.state.BasicState
import xyz.deftu.configured.config.ConfiguredPalette
import xyz.deftu.configured.gui.ConfigOptionComponent
import xyz.deftu.configured.gui.components.createCaretIcon
import xyz.deftu.configured.gui.effects.RotateEffect
import xyz.deftu.configured.options.Option

class DefaultDropdownComponent(
    override val option: Option
) : ConfigOptionComponent() {
    var value: Int
        get() = option.invoke().toString().toInt()
        set(value) = option.set(value)

    val options: List<String>
        get() = (option.attributes["options"] as? List<String>) ?: listOf()

    var expandedBackground: UIBlock
    private val expandedState = BasicState(false)

    init {
        constrain {
            width = 81.pixels
            height = 25.pixels
        }

        val background by UIBlock(ConfiguredPalette.getBackground2()).constrain {
            width = 100.percent
            height = 100.percent
        } effect OutlineEffect(
            color = ConfiguredPalette.getPrimary(),
            width = 2f
        ) childOf this
        val caretRotation = RotateEffect(0f)
        val caret by createCaretIcon().constrain {
            x = 0.pixels(alignOpposite = true) - 9.5.pixels
            y = CenterConstraint()
            width = 12.5.pixels
            height = 6.5.pixels
            color = ConfiguredPalette.getText().toConstraint()
        } effect caretRotation childOf background
        val text by UIText(options[value]).constrain {
            x = 12.pixels
            y = CenterConstraint()
            color = ConfiguredPalette.getText().toConstraint()
        }.setTextScale(0.8.pixels) childOf background
        background.onMouseClick {
            expandedState.set(!expandedState.get())
        }

        val expandedBackground by UIBlock(ConfiguredPalette.getBackground2()).constrain {
            y = SiblingConstraint()
            width = 100.percent
            height = 51.5.pixels
        } effect OutlineEffect(
            color = ConfiguredPalette.getPrimary(),
            width = 2f
        ) childOf this
        expandedBackground.hide()
        caretRotation.angle = 180f
        expandedState.onSetValue {
            if (it) {
                expandedBackground.unhide()
                caretRotation.angle = 0f
            } else {
                expandedBackground.hide()
                caretRotation.angle = 180f
            }
            expandedBackground.setFloating(it)
        }
        this.expandedBackground = expandedBackground
        val expandedScroller by ScrollComponent().constrain {
            width = 100.percent
            height = 100.percent
        } childOf expandedBackground

        options.forEach { optionName ->
            val option by UIBlock(ConfiguredPalette.getBackground2()).constrain {
                y = SiblingConstraint()
                width = 100.percent
                height = 25.pixels
            } effect OutlineEffect(
                color = ConfiguredPalette.getPrimary(),
                width = 2f
            ) childOf expandedScroller
            val optionText by UIText(optionName).constrain {
                x = CenterConstraint()
                y = CenterConstraint()
                color = ConfiguredPalette.getText().toConstraint()
            }.setTextScale(0.8.pixels) childOf option
            option.onMouseClick {
                value = options.indexOf(optionName)
                (text as UIText).setText(optionName)
                expandedState.set(false)
            }
        }
    }
}
