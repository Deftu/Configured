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

package xyz.deftu.configured.gui.components

import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.components.UIContainer
import gg.essential.elementa.components.UIImage
import gg.essential.elementa.components.input.UIPasswordInput
import gg.essential.elementa.components.input.UITextInput
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.FillConstraint
import gg.essential.elementa.constraints.animation.Animations
import gg.essential.elementa.dsl.*
import gg.essential.elementa.effects.OutlineEffect
import gg.essential.elementa.state.BasicState
import xyz.deftu.configured.config.ConfiguredPalette
import java.awt.Color

open class InputBoxComponent(
    defaultValue: String = "",
    protected: Boolean = false,
    limit: Int = -1
) : UIContainer() {
    private var valueChanged: List<(String) -> Unit> = listOf()
    private val protectionState = BasicState(protected)

    private val background by UIBlock(ConfiguredPalette.getBackground2()).constrain {
        width = 100.percent
        height = 100.percent
    } effect OutlineEffect(
        color = ConfiguredPalette.getPrimary(),
        width = 2f
    ) childOf this
    private val protectedButtonContainer by UIContainer().constrain {
        x = 0.pixels(alignOpposite = true)
        y = CenterConstraint()
        width = 30.pixels
        height = 100.percent
    } childOf background
    private var protectedButton by createEyeIcon().styleProtectionButton()
    val textInput by when {
        protected -> UIPasswordInput(
            selectionBackgroundColor = ConfiguredPalette.getBackground2(),
            selectionForegroundColor = ConfiguredPalette.getPrimary(),
            cursorColor = ConfiguredPalette.getPrimary()
        )
        else -> UITextInput(
            selectionBackgroundColor = ConfiguredPalette.getBackground2(),
            selectionForegroundColor = ConfiguredPalette.getPrimary(),
            cursorColor = ConfiguredPalette.getPrimary()
        )
    }.constrain {
        x = 7.5.pixels
        y = CenterConstraint()
        height = 9.pixels
    } childOf background

    init {
        textInput.setText(defaultValue)
        textInput.onUpdate { text ->
            var text = text
            if (limit != -1 && text.length > limit) {
                textInput.setText(text.substring(0, limit).also {
                    text = it
                })
            }
            valueChanged.forEach { it(text) }
        }
        if (!protected) {
            protectedButtonContainer.hide()
            textInput.setMinWidth(95.percent)
            textInput.setMaxWidth(95.percent)
        } else {
            (textInput as UIPasswordInput).bindProtection(protectionState)
            protectedButtonContainer.unhide()
            textInput.setMinWidth(FillConstraint())
            textInput.setMaxWidth(FillConstraint())
        }

        protectedButtonContainer.onMouseEnter {
            protectedButton.animate {
                setColorAnimation(Animations.OUT_EXP, 0.5f, ConfiguredPalette.getPrimary().toConstraint())
            }
        }.onMouseLeave {
            protectedButton.animate {
                setColorAnimation(Animations.OUT_EXP, 0.5f, Color.WHITE.toConstraint())
            }
        }.onMouseClick {
            protectionState.set(!protectionState.get())
            protectedButtonContainer.removeChild(protectedButton)
            protectedButton = createEyeIcon(if (protectionState.get()) "" else "crossed").styleProtectionButton()
        }

        onMouseClick {
            textInput.grabWindowFocus()
        }.onFocus {
            textInput.setActive(true)
        }.onFocusLost {
            textInput.setActive(false)
        }
    }

    private fun UIImage.styleProtectionButton() = constrain {
        x = CenterConstraint()
        y = CenterConstraint()
        width = 15.pixels
        height = 10.5.pixels
    } childOf protectedButtonContainer

    fun onValueChanged(listener: (String) -> Unit) {
        valueChanged += listener
    }
}
