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
import gg.essential.elementa.components.UIContainer
import gg.essential.elementa.components.UIWrappedText
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.SiblingConstraint
import gg.essential.elementa.constraints.animation.Animations
import gg.essential.elementa.dsl.*
import gg.essential.elementa.effects.OutlineEffect
import xyz.deftu.configured.config.ConfiguredPalette
import xyz.deftu.configured.options.Option
import java.awt.Color

internal class CategoryBarComponent : UIContainer() {
    // Handlers
    private lateinit var categoryHandler: () -> String
    private lateinit var categorySwitchHandler: (String) -> Unit

    // Components
    private val background by UIBlock(ConfiguredPalette.getBackground3()).constrain {
        width = 100.percent
        height = 100.percent
    } effect OutlineEffect(
        color = ConfiguredPalette.getPrimary(),
        width = 2f,
        sides = setOf(OutlineEffect.Side.Right)
    ) childOf this
    private val container by ScrollComponent().constrain {
        y = 7.5.pixels
        width = 100.percent
        height = 100.percent - 15.pixels
    } childOf background

    private val containerTrack by UIBlock(ConfiguredPalette.getBackground2()).constrain {
        x = 0.pixels(true)
        width = 12.5.pixels
        height = 100.percent
    } childOf container
    private val containerThumb by UIBlock(ConfiguredPalette.getPrimary()).constrain {
        x = CenterConstraint()
        width = 9.5.pixels
    } childOf containerTrack

    init {
        containerThumb.animateBeforeHide {
            onComplete {
                containerTrack.hide()
            }
        }
        container.setVerticalScrollBarComponent(containerThumb, hideWhenUseless = true)
    }

    fun initialize(options: List<Option>) {
        val used = mutableSetOf<String>()
        options.forEach { option ->
            val category = option.localizedCategory.ifBlank {
                option.category
            }
            if (used.contains(category)) return@forEach

            val categoryText by UIWrappedText(category).constrain {
                x = 14.pixels
                y = SiblingConstraint(7.5f)
                width = 100.percent - 14.pixels
            }.setTextScale(1.45.pixels) childOf container
            categoryText.onMouseEnter {
                categoryText.animate {
                    setColorAnimation(Animations.OUT_EXP, 0.5f, ConfiguredPalette.getPrimary().toConstraint())
                }
            }.onMouseLeave {
                categoryText.animate {
                    setColorAnimation(Animations.OUT_EXP, 0.5f, (if (categoryHandler() == option.category) ConfiguredPalette.getPrimary() else Color.WHITE).toConstraint())
                }
            }.onMouseClick {
                categorySwitchHandler(option.category)
            }
            used.add(category)
        }

        categorySwitchHandler(categoryHandler())
    }

    fun switchCategory(category: String) {
        container.children.forEach { child ->
            if (child !is UIWrappedText) return@forEach
            child.animate {
                setColorAnimation(Animations.OUT_EXP, 0.5f, (if (categoryHandler() == category) ConfiguredPalette.getPrimary() else Color.WHITE).toConstraint())
            }
        }
    }

    fun setCategoryHandler(handler: () -> String) {
        categoryHandler = handler
    }

    fun setCategorySwitchHandler(handler: (String) -> Unit) {
        categorySwitchHandler = handler
    }
}
