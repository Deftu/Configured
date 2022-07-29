package xyz.unifycraft.configured.gui.original

import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.components.UIContainer
import gg.essential.elementa.components.input.UITextInput
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.dsl.*
import gg.essential.elementa.effects.OutlineEffect
import xyz.unifycraft.configured.gui.ConfiguredPalette
import java.awt.Color

class InputBox(
    defaultValue: String = ""
) : UIContainer() {
    private val background by UIBlock(ConfiguredPalette.background).constrain {
        width = 100.percent
        height = 100.percent
    } effect OutlineEffect(
        color = ConfiguredPalette.main,
        width = 2f
    ) childOf this
    val textInput by UITextInput(
        selectionBackgroundColor = ConfiguredPalette.backgroundVariant,
        selectionForegroundColor = ConfiguredPalette.main,
        cursorColor = ConfiguredPalette.main
    ).constrain {
        x = CenterConstraint()
        y = CenterConstraint()
        height = 50.percent
    } childOf background

    init {
        textInput.setMinWidth(50.percent)
        textInput.setMaxWidth(100.percent)
        textInput.setText(defaultValue)

        onMouseClick {
            textInput.grabWindowFocus()
        }.onFocus {
            textInput.setActive(true)
        }.onFocusLost {
            textInput.setActive(false)
        }
    }
}
