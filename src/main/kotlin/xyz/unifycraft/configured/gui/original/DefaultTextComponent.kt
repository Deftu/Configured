package xyz.unifycraft.configured.gui.original

import gg.essential.elementa.constraints.ChildBasedSizeConstraint
import gg.essential.elementa.dsl.*
import xyz.unifycraft.configured.gui.ConfigOptionComponent
import xyz.unifycraft.configured.gui.components.InputBoxComponent
import xyz.unifycraft.configured.options.Option

/**
 * Internal component used
 * for the Configured GUI.
 */
class DefaultTextComponent(
    override val option: Option
) : ConfigOptionComponent() {
    var text: String
        get() = option.invoke().toString()
        set(value) = option.set(value)

    val protected: Boolean
        get() = option.attributes["protected"]?.toString()?.toBoolean() ?: false
    val limit: Int
        get() = option.attributes["limit"]?.toString()?.toInt() ?: -1

    init {
        constrain {
            width = ChildBasedSizeConstraint()
            height = ChildBasedSizeConstraint()
        }

        val input by InputBoxComponent(
            defaultValue = text,
            protected = protected,
            limit = limit
        ).constrain {
            width = 175.pixels
            height = 25.pixels
        } childOf this
        input.onValueChanged {
            text = it
        }
    }
}
