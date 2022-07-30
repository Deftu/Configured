package xyz.unifycraft.configured.gui.original

import gg.essential.elementa.constraints.ChildBasedSizeConstraint
import gg.essential.elementa.dsl.*
import xyz.unifycraft.configured.gui.ConfigOptionComponent
import xyz.unifycraft.configured.gui.components.InputBoxComponent
import xyz.unifycraft.configured.options.Option

class DefaultTextComponent(
    override val option: Option
) : ConfigOptionComponent() {
    var text: String
        get() = option.invoke().toString()
        set(value) = option.set(value)

    init {
        constrain {
            width = ChildBasedSizeConstraint()
            height = ChildBasedSizeConstraint()
        }

        val input by InputBoxComponent(text).constrain {
            width = 175.pixels
            height = 25.pixels
        } childOf this
        input.textInput.onUpdate {
            text = it
        }
    }
}
