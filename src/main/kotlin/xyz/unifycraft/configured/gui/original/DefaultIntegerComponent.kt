package xyz.unifycraft.configured.gui.original

import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.components.UIContainer
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.ChildBasedSizeConstraint
import gg.essential.elementa.constraints.SiblingConstraint
import gg.essential.elementa.dsl.*
import gg.essential.elementa.effects.OutlineEffect
import xyz.unifycraft.configured.gui.ConfigOptionComponent
import xyz.unifycraft.configured.gui.ConfiguredPalette
import xyz.unifycraft.configured.gui.components.CaretComponent
import xyz.unifycraft.configured.gui.components.NumericInputBoxComponent
import xyz.unifycraft.configured.gui.effects.RotateEffect
import xyz.unifycraft.configured.options.Option

class DefaultIntegerComponent(
    override val option: Option
) : ConfigOptionComponent() {
    // Data
    var number: Int
        get() = option.invoke().toString().toInt()
        set(value) = option.set(value)

    val minimum: Int
        get() = option.attributes["min"]?.toString()?.toInt() ?: 0
    val maximum: Int
        get() = option.attributes["max"]?.toString()?.toInt() ?: Int.MAX_VALUE

    // Components
    private lateinit var inputBox: NumericInputBoxComponent

    init {
        constrain {
            width = ChildBasedSizeConstraint()
            height = 32.5.pixels
        }

        val operationContainer by UIContainer().constrain {
            width = 25.pixels
            height = 32.5.pixels
        } childOf this

        val addBackground by UIBlock(ConfiguredPalette.background).constrain {
            width = 25.pixels
            height = 12.5.pixels
        } effect OutlineEffect(
            color = ConfiguredPalette.main,
            width = 2f
        ) childOf operationContainer
        addBackground.onMouseClick {
            if (number >= maximum) return@onMouseClick
            number += 1
            val numStr = number.toString()
            inputBox.validate(numStr)
            inputBox.apply(numStr)
        }
        val addCaret by CaretComponent().constrain {
            x = CenterConstraint()
            y = CenterConstraint()
            width = 12.pixels
            height = 6.5.pixels
        } effect RotateEffect(180f) childOf addBackground

        val removeBackground by UIBlock(ConfiguredPalette.background).constrain {
            y = SiblingConstraint(7.5f)
            width = 25.pixels
            height = 12.5.pixels
        } effect OutlineEffect(
            color = ConfiguredPalette.main,
            width = 2f
        ) childOf operationContainer
        removeBackground.onMouseClick {
            if (number <= minimum) return@onMouseClick
            number -= 1
            val numStr = number.toString()
            inputBox.validate(numStr)
            inputBox.apply(numStr)
        }
        val removeCaret by CaretComponent().constrain {
            x = CenterConstraint()
            y = CenterConstraint()
            width = 12.pixels
            height = 6.5.pixels
        } childOf removeBackground

        val input by NumericInputBoxComponent(
            value = number,
            min = minimum,
            max = maximum,
        ).constrain {
            x = SiblingConstraint(6f)
            y = CenterConstraint()
            width = 35.pixels
            height = 25.pixels
        } childOf this
        inputBox = input
    }
}
