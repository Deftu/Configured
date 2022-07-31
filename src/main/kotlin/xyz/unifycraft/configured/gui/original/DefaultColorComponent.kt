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
import xyz.unifycraft.configured.options.Option
import java.awt.Color

/**
 * Adapted from Vigilance under LGPL 3.0
 * https://github.com/EssentialGG/Vigilance/blob/2c59ec0e0b4328f1f0674a40f9de699c0add0e91/LICENSE
 */
class DefaultColorComponent(
    override val option: Option
) : ConfigOptionComponent() {
    var colorOption: Color
        get() = option.invoke() as Color
        set(value) = option.set(value)

    val hasAlpha: Boolean
        get() = option.attributes["has_alpha"]?.toString()?.toBoolean() ?: false

    init {
        constrain {
            width = 25.pixels
            height = 25.pixels
        }

        val colorBox by UIBlock(colorOption).constrain {
            y = CenterConstraint()
            width = 25.pixels
            height = 25.pixels
        } effect OutlineEffect(
            color = ConfiguredPalette.main,
            width = 2f
        ) childOf this

        val colorPicker by ColorPicker(
            value = colorOption,
            hasAlpha = hasAlpha
        ).constrain {
            x = SiblingConstraint(5f, alignOpposite = true)
            y = CenterConstraint()
        } childOf this
        colorPicker.hide()

        colorBox.onMouseClick {
            println("clicked color indicator")
            if (!this@DefaultColorComponent.children.contains(colorPicker)) {
                println("unhiding color picker")
                colorPicker.unhide()
                colorPicker.setFloating(true)
            } else {
                println("hiding color picker")
                colorPicker.hide()
                colorPicker.setFloating(false)
            }
        }

        colorPicker.onValueChanged {
            colorBox.setColor(it)
            colorOption = it
        }
    }
}

/**
 * Adapted from Vigilance under LGPL 3.0
 * https://github.com/EssentialGG/Vigilance/blob/2c59ec0e0b4328f1f0674a40f9de699c0add0e91/LICENSE
 */
class ColorPicker(
    value: Color,
    hasAlpha: Boolean
) : UIContainer() {
    private var valueChangedListener: (Color) -> Unit = {}

    private var currentHue: Float
    private var currentSaturation: Float
    private var currentBrightness: Float
    private var currentAlpha = value.alpha / 255f

    private var draggingHue = false
    private var draggingPicker = false

    init {
        constrain {
            width = 70.pixels
            height = if (hasAlpha) 70.pixels else 40.pixels
        }

        val hsb = Color.RGBtoHSB(value.red, value.green, value.blue, null)
        currentHue = hsb[0]
        currentSaturation = hsb[1]
        currentBrightness = hsb[2]

        val background by UIBlock(ConfiguredPalette.backgroundVariant).constrain {
            width = 100.percent
            height = 100.percent
        } effect OutlineEffect(
            color = ConfiguredPalette.main,
            width = 2f
        ) childOf this

        val pickerBox by UIBlock(ConfiguredPalette.backgroundVariant).constrain {
            x = 2.5.pixels
            y = 2.5.pixels
            width = 50.pixels
            height = 50.pixels
        } effect OutlineEffect(
            color = ConfiguredPalette.main,
            width = 1f
        ) childOf background
        val hueBox by UIBlock(ConfiguredPalette.backgroundVariant).constrain {
            x = SiblingConstraint(2.5f)
            y = 2.5.pixels
            width = 12.5.pixels
            height = 50.pixels
        } effect OutlineEffect(
            color = ConfiguredPalette.main,
            width = 1f
        ) childOf background
        if (hasAlpha) {
            val alphaBox by UIBlock(ConfiguredPalette.backgroundVariant).constrain {
                x = 2.5.pixels
                y = SiblingConstraint(2.5f)
                width = 50.pixels
                height = 12.5.pixels
            } effect OutlineEffect(
                color = ConfiguredPalette.main,
                width = 1f
            ) childOf background
        }
    }

    fun onValueChanged(listener: (Color) -> Unit) {
        valueChangedListener = listener
    }
}
