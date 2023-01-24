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

import gg.essential.elementa.components.*
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.SiblingConstraint
import gg.essential.elementa.dsl.*
import gg.essential.elementa.utils.withAlpha
import net.minecraft.client.resources.I18n
import xyz.deftu.configured.config.ConfiguredPalette
import xyz.deftu.configured.gui.ConfigOptionComponent
import xyz.deftu.configured.options.Option
import java.awt.Color

internal class OptionAreaComponent : UIContainer() {
    // Handlers
    private lateinit var optionCreateHandler: (Option) -> ConfigOptionComponent

    // Components
    private val container by ScrollComponent(
        pixelsPerScroll = 30f
    ).constrain {
        width = 100.percent
        height = 100.percent
    } childOf this

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
        setupContainer()
    }

    fun initialize(options: List<Option>) {
        options.forEach { option ->
            val background by UIBlock(ConfiguredPalette.getBackground2()).constrain {
                x = 7.5.pixels
                y = SiblingConstraint(7.5f)
                width = 492.5.pixels
                height = 71.pixels
            } childOf container
            val name by UIText(option.localizedName.ifBlank {
                option.name
            }).constrain {
                x = 7.5.pixels
                y = CenterConstraint()
                color = ConfiguredPalette.getText().toConstraint()
            }.setTextScale(1.5.pixels) childOf background
            if (option.description.isNotBlank()) {
                val description by UIWrappedText(I18n.format(option.description)).constrain {
                    x = 7.5.pixels
                    y = 47.pixels
                    width = 50.percent
                    color = ConfiguredPalette.getTextFaded().toConstraint()
                }.setTextScale(1.04.pixels) childOf background
                name.constrain {
                    y = (y boundTo description) - description.getHeight().pixels - 2.pixels
                }
            }
            val component by optionCreateHandler(option)
            component.constrain {
                x = 36.5.pixels(alignOpposite = true)
                y = CenterConstraint()
            } childOf background
        }
    }

    fun reset() {
        if (container.hasParent) container.scrollToTop(true)
        container.allChildren.forEach { component ->
            if (component !is UIBlock) return@forEach
            val componentChildren = component.children
            if (componentChildren.isEmpty() || componentChildren.none {
                    it is ConfigOptionComponent
                }) return@forEach
            (componentChildren.first {
                it is ConfigOptionComponent
            } as ConfigOptionComponent).closePopups()
        }

        container.clearChildren()
        setupContainer()
    }

    private fun setupContainer() {
        container.setVerticalScrollBarComponent(containerThumb, hideWhenUseless = true)
    }

    fun setOptionCreateHandler(handler: (Option) -> ConfigOptionComponent) {
        optionCreateHandler = handler
    }
}
