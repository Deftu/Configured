package xyz.unifycraft.configured.gui.components

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
import xyz.unifycraft.configured.gui.ConfiguredPalette
import java.awt.Color

open class InputBoxComponent(
    defaultValue: String = "",
    protected: Boolean = false,
    limit: Int = -1
) : UIContainer() {
    private var valueChanged: List<(String) -> Unit> = listOf()
    private val protectionState = BasicState(protected)

    private val background by UIBlock(ConfiguredPalette.background).constrain {
        width = 100.percent
        height = 100.percent
    } effect OutlineEffect(
        color = ConfiguredPalette.main,
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
            selectionBackgroundColor = ConfiguredPalette.backgroundVariant,
            selectionForegroundColor = ConfiguredPalette.main,
            cursorColor = ConfiguredPalette.main
        )
        else -> UITextInput(
            selectionBackgroundColor = ConfiguredPalette.backgroundVariant,
            selectionForegroundColor = ConfiguredPalette.main,
            cursorColor = ConfiguredPalette.main
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
            textInput.setMinWidth(90.percent)
            textInput.setMaxWidth(90.percent)
        } else {
            (textInput as UIPasswordInput).bindProtection(protectionState)
            protectedButtonContainer.unhide()
            textInput.setMinWidth(FillConstraint())
            textInput.setMaxWidth(FillConstraint())
        }

        protectedButtonContainer.onMouseEnter {
            protectedButton.animate {
                setColorAnimation(Animations.OUT_EXP, 0.5f, ConfiguredPalette.main.toConstraint())
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
