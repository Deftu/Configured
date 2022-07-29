package xyz.unifycraft.configured.gui.original

import gg.essential.elementa.constraints.ChildBasedSizeConstraint
import gg.essential.elementa.dsl.*
import xyz.unifycraft.configured.gui.ConfigOptionComponent
import xyz.unifycraft.configured.options.Option

class DefaultIntegerComponent(
    override val option: Option
) : ConfigOptionComponent() {
    var number: Int
        get() = option.invoke().toString().toInt()
        set(value) = option.set(value)

    val minimum: Int
        get() = option.attributes["min"]?.toString()?.toInt() ?: 0
    val maximum: Int
        get() = option.attributes["max"]?.toString()?.toInt() ?: Int.MAX_VALUE

    init {
        constrain {
            width = ChildBasedSizeConstraint()
            height = ChildBasedSizeConstraint()
        }

        val input by InputBox(number.toString()).constrain {
            width = 25.pixels
            height = 25.pixels
        } childOf this
        input.textInput.onUpdate {
            if (it.isNotEmpty()) {
                val number = it.toIntOrNull()
                if (number == null) {
                    input.textInput.setText(this.number.toString())
                } else {
                    val number = number.coerceIn(minimum, maximum)
                    input.textInput.setText(number.toString())
                    this.number = number
                }
            } else number = 0
        }
    }
}
