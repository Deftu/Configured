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
import gg.essential.elementa.components.inspector.Inspector
import gg.essential.elementa.constraints.*
import gg.essential.elementa.constraints.animation.Animations
import gg.essential.elementa.dsl.*
import gg.essential.elementa.effects.OutlineEffect
import gg.essential.elementa.utils.withAlpha
import gg.essential.universal.GuiScale
import net.minecraft.client.resources.I18n
import xyz.deftu.configured.Config
import xyz.deftu.configured.Configured
import xyz.deftu.configured.config.ConfiguredPalette
import xyz.deftu.configured.gui.ConfigMenu
import xyz.deftu.configured.gui.ConfigOptionComponent
import xyz.deftu.configured.gui.components.*
import xyz.deftu.configured.gui.effects.RotateEffect
import xyz.deftu.configured.options.Option
import xyz.deftu.configured.options.OptionType
import java.awt.Color

class DefaultConfigMenu(
    val config: Config,
    title: String
) : ConfigMenu(
    newGuiScale = GuiScale.scaleForScreenSize().ordinal
) {
    private var currentCategory: String = ""

    private val background by UIBlock(ConfiguredPalette.getBackground()).constrain {
        x = CenterConstraint()
        y = CenterConstraint()
        width = 720.pixels
        height = 405.pixels
    } effect OutlineEffect(
        color = ConfiguredPalette.getPrimary(),
        width = 2f
    ) childOf window
    private val mainContainer by UIContainer().constrain {
        x = CenterConstraint()
        y = CenterConstraint()
        width = 720.pixels
        height = 405.pixels
    } childOf window

    private val gitInfo by UIText("${Configured.NAME} (${Configured.GIT_BRANCH}/${Configured.GIT_COMMIT})").constrain {
        x = 5.pixels
        y = 5.pixels(alignOpposite = true)
        color = ConfiguredPalette.getTextFaded().toConstraint()
    }.setTextScale(0.8.pixels) childOf window

    private val headerContainer by UIContainer().constrain {
        width = 100.percent
        height = 50.pixels
    } effect OutlineEffect(
        color = ConfiguredPalette.getPrimary(),
        width = 2f,
        sides = setOf(OutlineEffect.Side.Bottom)
    ) childOf mainContainer
    private val backButton by UIContainer().constrain {
        width = 39.5.pixels
        height = 100.percent
    } childOf headerContainer
    private val backButtonIcon by createCaretIcon().constrain {
        x = CenterConstraint()
        y = CenterConstraint()
        width = 22.pixels
        height = 11.5.pixels
        color = ConfiguredPalette.getText().toConstraint()
    } effect RotateEffect(90f) childOf backButton

    private val titleContainer by UIContainer().constrain {
        x = CenterConstraint()
        y = CenterConstraint()
        width = ChildBasedSizeConstraint()
        height = 100.percent
    } childOf headerContainer
    private val titleText by UIText(title).constrain {
        x = CenterConstraint()
        y = CenterConstraint()
        color = ConfiguredPalette.getText().toConstraint()
    }.setTextScale(1.875.pixels) childOf titleContainer

    private val searchContainer by UIContainer().constrain {
        x = 12.5.pixels(alignOpposite = true)
        y = CenterConstraint()
        width = ChildBasedSizeConstraint()
        height = 100.percent
    } childOf headerContainer
    private val searchIcon by createSearchIcon().constrain {
        x = 0.pixels(alignOpposite = true)
        y = CenterConstraint()
        width = 25.pixels
        height = 25.pixels
        color = ConfiguredPalette.getText().toConstraint()
    } childOf searchContainer
    private val searchInput by InputBoxComponent().constrain {
        x = SiblingConstraint(7.5f, alignOpposite = true)
        y = CenterConstraint()
        width = 125.pixels
        height = 25.pixels
    } childOf searchContainer

    private val contentContainer by UIContainer().constrain {
        y = SiblingConstraint()
        width = 100.percent
        height = FillConstraint()
    } childOf mainContainer

    private val categoryBar by CategoryBarComponent().constrain {
        y = 2.pixels
        width = 200.pixels
        height = 100.percent - 2.pixels
    } childOf contentContainer
    private val optionArea by OptionAreaComponent().constrain {
        x = SiblingConstraint(7.5f)
        y = 7.5.pixels
        width = FillConstraint() - 7.5.pixels
        height = 340.pixels
    } childOf contentContainer

    init {
        // Collect all the options.
        val options = config.collector.get()
        Inspector(window) childOf window

        // Animate the text of the back button.
        backButton.onMouseEnter {
            backButtonIcon.animate {
                setColorAnimation(Animations.OUT_EXP, 0.5f, ConfiguredPalette.getPrimary().toConstraint())
            }
        }.onMouseLeave {
            backButtonIcon.animate {
                setColorAnimation(Animations.OUT_EXP, 0.5f, Color.WHITE.toConstraint())
            }
        }.onMouseClick {
            restorePreviousScreen()
        }

        currentCategory = Option.DEFAULT_CATEGORY
        if (options.isNotEmpty() && options.none {
                it.category == Option.DEFAULT_CATEGORY
            }) {
            currentCategory = options.first().category
        }

        // Set up the option area.
        optionArea.setOptionCreateHandler(::createOptionComponent)
        optionArea.initialize(options.filter {
            it.category == currentCategory
        })

        // Set up the category bar.
        categoryBar.setCategoryHandler(::currentCategory)
        categoryBar.setCategorySwitchHandler(::switchCategory)
        categoryBar.initialize(options)

        // Set up the search input.
        searchInput.hide()
        searchInput.textInput.onActivate {
            if (currentCategory.isBlank()) throw IllegalStateException("No category is selected.")
            search(it)
            searchInput.hide()
        }
        searchIcon.onMouseEnter {
            searchIcon.animate {
                setColorAnimation(Animations.OUT_EXP, 0.5f, ConfiguredPalette.getPrimary().toConstraint())
            }
        }.onMouseLeave {
            searchIcon.animate {
                setColorAnimation(Animations.OUT_EXP, 0.5f, Color.WHITE.toConstraint())
            }
        }.onMouseClick {
            searchInput.unhide()
        }
    }

    private fun switchCategory(category: String) {
        categoryBar.switchCategory(category)
        currentCategory = category

        val options = config.collector.get().filter { option ->
            option.category == category
        }

        optionArea.reset()
        optionArea.initialize(options)
    }

    private fun search(filter: String) {
        val options = config.collector.get().filter { option ->
            if (option.category != currentCategory) return@filter false

            var filterMatch = filter.isBlank()
            if (!filterMatch) {
                filterMatch = option.localizedName.ifBlank {
                    option.name
                }.contains(filter, true) || option.tags.any { searchTag ->
                    searchTag.contains(filter, true)
                }
            }

            filterMatch
        }

        optionArea.reset()
        optionArea.initialize(options)
    }

    override fun createOptionComponent(option: Option) = when (option.type) {
        OptionType.BUTTON -> DefaultButtonComponent(option)
        OptionType.INTEGER -> DefaultIntegerComponent(option)
        OptionType.FILE -> DefaultFileComponent(option)
        OptionType.TEXT -> DefaultTextComponent(option)
        OptionType.DROPDOWN -> DefaultDropdownComponent(option)
        OptionType.PERCENTAGE -> DefaultPercentageComponent(option)
        OptionType.COLOR -> DefaultColorComponent(option)
        OptionType.SWITCH -> DefaultSwitchComponent(option)
    }

    override fun onScreenClose() {
        config.markDirty()
        config.save()
        super.onScreenClose()
    }

    override fun updateGuiScale() {
        newGuiScale = GuiScale.scaleForScreenSize().ordinal
        super.updateGuiScale()
    }
}
