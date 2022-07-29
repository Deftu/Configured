package xyz.unifycraft.configured.gui.original

import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.components.UIText
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.ChildBasedSizeConstraint
import gg.essential.elementa.constraints.ConstantColorConstraint
import gg.essential.elementa.constraints.animation.Animations
import gg.essential.elementa.dsl.*
import xyz.unifycraft.configured.gui.ConfigOptionComponent
import xyz.unifycraft.configured.gui.ConfiguredPalette
import xyz.unifycraft.configured.options.Option

class DefaultButtonComponent(
    override val option: Option
) : ConfigOptionComponent() {
    init {
        constrain {
            width = ChildBasedSizeConstraint()
            height = ChildBasedSizeConstraint()
        }

        val background by UIBlock(ConfiguredPalette.button).constrain {
            width = 100.pixels
            height = 30.pixels
        } childOf this
        val textAttr = option.attributes["text"]?.toString()
        val text by UIText(if (textAttr.isNullOrBlank()) option.name else textAttr).constrain {
            x = CenterConstraint()
            y = CenterConstraint()
        }.setTextScale(1.04.pixels) childOf background

        onMouseEnter {
            background.animate {
                setColorAnimation(Animations.OUT_EXP, 0.25f, ConstantColorConstraint(ConfiguredPalette.buttonVariant))
            }
        }.onMouseLeave {
            background.animate {
                setColorAnimation(Animations.IN_EXP, 0.25f, ConstantColorConstraint(ConfiguredPalette.button))
            }
        }.onMouseClick {
            option.invoke()
        }
    }
}
