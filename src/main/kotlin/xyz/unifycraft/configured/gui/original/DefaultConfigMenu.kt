package xyz.unifycraft.configured.gui.original

import gg.essential.elementa.components.*
import gg.essential.elementa.components.input.UITextInput
import gg.essential.elementa.components.inspector.Inspector
import gg.essential.elementa.constraints.*
import gg.essential.elementa.constraints.animation.Animations
import gg.essential.elementa.dsl.*
import gg.essential.elementa.effects.OutlineEffect
import gg.essential.elementa.utils.withAlpha
import gg.essential.universal.GuiScale
import xyz.unifycraft.configured.Config
import xyz.unifycraft.configured.Configured
import xyz.unifycraft.configured.gui.ConfigMenu
import xyz.unifycraft.configured.gui.ConfiguredPalette
import xyz.unifycraft.configured.gui.components.*
import xyz.unifycraft.configured.gui.effects.RotateEffect
import xyz.unifycraft.configured.options.Option
import xyz.unifycraft.configured.options.OptionType
import java.awt.Color

class DefaultConfigMenu(
    val config: Config,
    title: String
) : ConfigMenu(
    newGuiScale = GuiScale.scaleForScreenSize().ordinal
) {
    private var currentCategory: String = ""

    private val background by UIBlock(ConfiguredPalette.background).constrain {
        x = CenterConstraint()
        y = CenterConstraint()
        width = 720.pixels
        height = 405.pixels
    } effect OutlineEffect(
        color = ConfiguredPalette.main,
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
        color = Color.WHITE.withAlpha(128).toConstraint()
    }.setTextScale(0.8.pixels) childOf window

    private val headerContainer by UIContainer().constrain {
        width = 100.percent
        height = 50.pixels
    } effect OutlineEffect(
        color = ConfiguredPalette.main,
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
    } effect RotateEffect(90f) childOf backButton
    private val categoryDropdownButton by UIContainer().constrain {
        x = SiblingConstraint()
        width = 39.5.pixels
        height = 100.percent
    } childOf headerContainer
    private val categoryDropdownButtonIcon by createCaretIcon().constrain {
        x = CenterConstraint()
        y = CenterConstraint()
        width = 22.pixels
        height = 11.5.pixels
    } effect RotateEffect(180f) childOf categoryDropdownButton

    private val titleContainer by UIContainer().constrain {
        x = CenterConstraint()
        y = CenterConstraint()
        width = ChildBasedSizeConstraint()
        height = 100.percent
    } childOf headerContainer
    private val titleText by UIText(title).constrain {
        x = CenterConstraint()
        y = CenterConstraint()
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

    private val categoryAreaContainer by UIBlock(ConfiguredPalette.background).constrain {
        y = 2.pixels
        width = 200.pixels
        height = 100.percent - 2.pixels
    } effect OutlineEffect(
        color = ConfiguredPalette.main,
        width = 2f,
        sides = setOf(OutlineEffect.Side.Right)
    ) childOf contentContainer
    private val categoryContainer by ScrollComponent().constrain {
        y = 7.5.pixels
        width = 200.pixels
        height = 100.percent
    } childOf categoryAreaContainer
    private val categoryContainerScrollbarTrack by UIBlock(ConfiguredPalette.backgroundVariant).constrain {
        x = 0.pixels(alignOpposite = true)
        y = 2.pixels
        width = 12.5.pixels
        height = 100.percent - 2.pixels
    } childOf categoryAreaContainer
    private val categoryContainerScrollbarThumb by UIBlock(ConfiguredPalette.main).constrain {
        width = 12.5.pixels
    } childOf categoryContainerScrollbarTrack

    private val optionsContainer by ScrollComponent(
        pixelsPerScroll = 30f
    ).constrain {
        y = 7.5.pixels
        width = 100.percent
        height = 340.pixels
    } childOf contentContainer
    private val optionsContainerScrollbarTrack by UIBlock(ConfiguredPalette.backgroundVariant).constrain {
        x = 0.pixels(alignOpposite = true)
        y = 2.pixels
        width = 12.5.pixels
        height = 100.percent - 2.pixels
    } childOf contentContainer
    private val optionsContainerScrollbarThumb by UIBlock(ConfiguredPalette.main).constrain {
        width = 12.5.pixels
    } childOf optionsContainerScrollbarTrack

    init {
        // Collect all the options.
        val options = config.collector.get()
        Inspector(window) childOf window

        // Animate the text of the back button.
        backButton.onMouseEnter {
            backButtonIcon.animate {
                setColorAnimation(Animations.OUT_EXP, 0.5f, ConstantColorConstraint(ConfiguredPalette.main))
            }
        }.onMouseLeave {
            backButtonIcon.animate {
                setColorAnimation(Animations.OUT_EXP, 0.5f, ConstantColorConstraint(Color.WHITE))
            }
        }.onMouseClick {
            restorePreviousScreen()
        }

        // Animate the text of the category dropdown button.
        categoryDropdownButton.onMouseEnter {
            categoryDropdownButtonIcon.animate {
                setColorAnimation(Animations.OUT_EXP, 0.5f, ConstantColorConstraint(ConfiguredPalette.main))
            }
        }.onMouseLeave {
            categoryDropdownButtonIcon.animate {
                setColorAnimation(Animations.OUT_EXP, 0.5f, ConstantColorConstraint(Color.WHITE))
            }
        }

        // The category dropdown isn't shown by default.
        categoryContainerScrollbarThumb.animateBeforeHide {
            onComplete {
                categoryContainerScrollbarTrack.hide()
            }
        }
        categoryAreaContainer.hide()
        categoryAreaContainer.onMouseClick {
            it.stopImmediatePropagation()
        }
        categoryContainer.setVerticalScrollBarComponent(categoryContainerScrollbarThumb, true)
        categoryDropdownButton.onMouseClick {
            val rotation: RotateEffect = categoryDropdownButtonIcon.effects.firstOrNull {
                it is RotateEffect
            } as? RotateEffect
                ?: throw IllegalStateException("There should be a rotation effect for the dropdown icon.")
            if (contentContainer.children.contains(categoryAreaContainer)) {
                categoryAreaContainer.setFloating(false)
                categoryAreaContainer.hide()
                rotation.angle = 180f
            } else {
                categoryAreaContainer.setFloating(true)
                categoryAreaContainer.unhide()
                rotation.angle = 0f
            }
        }
        val categoriesAdded = mutableSetOf<String>()
        options.forEach { option ->
            if (categoriesAdded.contains(option.category)) return@forEach
            categoriesAdded.add(option.category)
            val category by UIWrappedText(
                text = option.category,
                centered = false,
                trimText = true
            ).constrain {
                x = 14.pixels
                y = SiblingConstraint(7.5f)
                width = 100.percent - 14.pixels
            }.setTextScale(1.45.pixels) childOf categoryContainer
            category.onMouseEnter {
                category.animate {
                    setColorAnimation(Animations.OUT_EXP, 0.5f, ConstantColorConstraint(ConfiguredPalette.main))
                }
            }.onMouseLeave {
                category.animate {
                    setColorAnimation(Animations.OUT_EXP, 0.5f, ConstantColorConstraint(Color.WHITE))
                }
            }.onMouseClick {
                switchCategory(option.category)
                categoryAreaContainer.setFloating(false)
                categoryAreaContainer.hide()
            }
        }

        // Switch to the category of the initial option in the list.
        switchCategory(options.first().category)

        // Setup the search input.
        searchInput.hide()
        searchInput.textInput.onActivate {
            if (currentCategory.isBlank()) throw IllegalStateException("No category is selected.")
            switchCategory(currentCategory, it)
            searchInput.hide()
        }
        searchIcon.onMouseEnter {
            searchIcon.animate {
                setColorAnimation(Animations.OUT_EXP, 0.5f, ConstantColorConstraint(ConfiguredPalette.main))
            }
        }.onMouseLeave {
            searchIcon.animate {
                setColorAnimation(Animations.OUT_EXP, 0.5f, ConstantColorConstraint(Color.WHITE))
            }
        }.onMouseClick {
            searchInput.unhide()
        }

        // When we click outside the category dropdown, hide it.
        contentContainer.onMouseClick {
            if (!contentContainer.children.contains(categoryAreaContainer) || it.target == categoryAreaContainer) return@onMouseClick
            categoryAreaContainer.setFloating(false)
            categoryAreaContainer.hide()
            it.stopPropagation()
        }
    }

    private fun switchCategory(category: String, filter: String = "") {
        currentCategory = category
        optionsContainer.clearChildren()
        optionsContainer.setVerticalScrollBarComponent(optionsContainerScrollbarThumb, false)
        val options = config.collector.get().filter { option ->
            val categoryMatch = option.category == category
            var filterMatch = filter.isBlank()
            if (!filterMatch) {
                filterMatch = option.localizedName.ifBlank {
                    option.name
                }.contains(filter, true) || option.tags.any { searchTag ->
                    searchTag.contains(filter, true)
                }
            }
            categoryMatch && filterMatch
        }
        options.forEach { option ->
            val background by UIBlock(ConfiguredPalette.backgroundVariant).constrain {
                x = 7.5.pixels
                y = SiblingConstraint(7.5f)
                width = 692.5.pixels
                height = 95.5.pixels
            } childOf optionsContainer
            val name by UIText(option.localizedName.ifBlank {
                option.name
            }).constrain {
                x = 36.5.pixels
                y = CenterConstraint()
            }.setTextScale(1.5.pixels) childOf background
            val component by createOptionComponent(option)
            component.constrain {
                x = 36.5.pixels(alignOpposite = true)
                y = CenterConstraint()
            } childOf background
        }
    }

    override fun createOptionComponent(option: Option) = when (option.type) {
        OptionType.BUTTON -> DefaultButtonComponent(option)
        OptionType.INTEGER -> DefaultIntegerComponent(option)
        OptionType.FILE -> DefaultFileComponent(option)
        OptionType.TEXT -> DefaultTextComponent(option)
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
