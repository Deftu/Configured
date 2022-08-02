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
import xyz.unifycraft.configured.gui.components.NumericInputBoxComponent
import xyz.unifycraft.configured.options.Option
import java.text.DecimalFormat

/**
 * Internal component used
 * for the Configured GUI.
 *
 * Maths for slider functionality adapted from EvergreenHUD under GPL 3.0
 * https://github.com/isXander/EvergreenHUD/blob/86b881510698d9e75b519ada0747da12d89037ff/LICENSE.md
 */
class DefaultPercentageComponent(
    override val option: Option
) : ConfigOptionComponent() {
    private val numberFormat = DecimalFormat("0.00")

    var number: Float
        get() = option.invoke().toString().toFloat()
        set(value) = option.set(value)

    val minimum: Int
        get() = option.attributes["min"]?.toString()?.toInt() ?: 0
    val maximum: Int
        get() = option.attributes["max"]?.toString()?.toInt() ?: Int.MAX_VALUE

    private var draggingOffset = 0f
    private var mouseDown = false

    init {
        constrain {
            width = ChildBasedSizeConstraint()
            height = 25.pixels
        }

        val slider by UIContainer().constrain {
            y = CenterConstraint()
            width = 150.pixels
            height = 12.5.pixels
        } childOf this
        val track by UIBlock(ConfiguredPalette.background).constrain {
            y = CenterConstraint()
            width = 150.pixels
            height = 5.pixels
        } effect OutlineEffect(
            color = ConfiguredPalette.main,
            width = 2f
        ) childOf slider
        val thumb by UIBlock(ConfiguredPalette.background).constrain {
            x = basicXConstraint {
                (track.getLeft() + track.getWidth() * (number / 100)) - (it.getWidth() / 2)
            }
            y = CenterConstraint()
            width = 12.5.pixels
            height = 12.5.pixels
        } effect OutlineEffect(
            color = ConfiguredPalette.main,
            width = 2f
        ) childOf slider

        val input by NumericInputBoxComponent(
            value = numberFormat.format(number).toFloat(),
            min = minimum,
            max = maximum
        ).constrain {
            x = SiblingConstraint(12.5f)
            width = 35.pixels
            height = 25.pixels
        } childOf this
        input.textInput.onUpdate {
            val num = it.toFloatOrNull() ?: return@onUpdate
            number = num
        }

        thumb.onMouseClick {
            draggingOffset = it.relativeX - thumb.getWidth() / 2
            mouseDown = true
            it.stopPropagation()
        }.onMouseRelease {
            draggingOffset = 0f
            mouseDown = false
        }.onMouseDrag { mouseX, _, _ ->
            if (!mouseDown) return@onMouseDrag
            val clamped = (mouseX + thumb.getLeft() - draggingOffset).coerceIn(track.getLeft()..track.getRight())
            val percentage = ((clamped - track.getLeft()) / track.getWidth()).coerceIn(0f..1f)
            number = percentage * 100
            input.apply(numberFormat.format(number))
        }
    }
}
