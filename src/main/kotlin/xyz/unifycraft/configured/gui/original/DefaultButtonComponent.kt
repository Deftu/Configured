package xyz.unifycraft.configured.gui.original

import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.components.UIText
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.ChildBasedSizeConstraint
import gg.essential.elementa.constraints.animation.Animations
import gg.essential.elementa.dsl.*
import xyz.unifycraft.configured.gui.ConfigOptionComponent
import xyz.unifycraft.configured.gui.ConfiguredPalette
import xyz.unifycraft.configured.options.Option

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

        val background by UIBlock(ConfiguredPalette.button).constrain {
            width = 100.pixels
            height = 30.pixels
        } childOf this
        val text by UIText(this.text).constrain {
            x = CenterConstraint()
            y = CenterConstraint()
        } childOf background

        onMouseEnter {
            background.animate {
                setColorAnimation(Animations.OUT_EXP, 0.5f, ConfiguredPalette.buttonVariant.toConstraint())
            }
        }.onMouseLeave {
            background.animate {
                setColorAnimation(Animations.IN_EXP, 0.5f, ConfiguredPalette.button.toConstraint())
            }
        }.onMouseClick {
            option.invoke()
        }
    }
}
