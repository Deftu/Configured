package xyz.unifycraft.configured.gui.original

import gg.essential.elementa.UIComponent
import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.components.UIContainer
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.RelativeConstraint
import gg.essential.elementa.constraints.SiblingConstraint
import gg.essential.elementa.dsl.*
import gg.essential.elementa.effects.OutlineEffect
import gg.essential.elementa.utils.withAlpha
import gg.essential.universal.UGraphics
import gg.essential.universal.UMatrixStack
import xyz.unifycraft.configured.gui.ConfigOptionComponent
import xyz.unifycraft.configured.gui.ConfiguredPalette
import xyz.unifycraft.configured.gui.components.InputBoxComponent
import xyz.unifycraft.configured.options.Option
import java.awt.Color
import kotlin.math.roundToInt

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
        get() = option.attributes["alpha"]?.toString()?.toBoolean() ?: false

    private var colorPicker: ColorPicker

    init {
        constrain {
            width = 125.pixels
            height = 25.pixels
        }

        val mainContainer by UIContainer().constrain {
            width = 125.pixels
            height = 25.pixels
        } childOf this
        val input by InputBoxComponent(
            defaultValue = createColorString(colorOption)
        ).constrain {
            width = 100.percent
            height = 100.percent
        } childOf mainContainer
        val colorIndicator by UIBlock(colorOption).constrain {
            x = 6.5.pixels(alignOpposite = true)
            y = CenterConstraint()
            width = 12.5.pixels
            height = 12.5.pixels
        } effect OutlineEffect(
            color = ConfiguredPalette.main,
            width = 2f
        ) childOf mainContainer

        val colorPicker by ColorPicker(
            value = colorOption,
            hasAlpha = hasAlpha
        ).constrain {
            x = SiblingConstraint(5f, alignOpposite = true)
            y = CenterConstraint()
        } childOf this
        colorPicker.hide()
        this.colorPicker = colorPicker

        input.textInput.onActivate { originalHex ->
            var hex = originalHex.replaceFirst("#", "")
            if (hex.length == 3) {
                val chars = listOf(hex[0], hex[0], hex[1], hex[1], hex[2], hex[2])
                hex = chars.joinToString("")
            } else if (hex.length > 6) hex = hex.substring(0, 6)
            else if (hex.length in 4..5) return@onActivate
            hex = "0x$hex"
            var color = Color.decode(hex)
            if (originalHex.length == 9) {
                val alpha = originalHex.replaceFirst("#", "").substring(6, 8).toInt(16)
                color = color.withAlpha(alpha)
            }
            colorOption = color
            colorIndicator.setColor(color)
            val hsb = Color.RGBtoHSB(color.red, color.green, color.blue, null)
            colorPicker.setHsb(hsb[0], hsb[1], hsb[2])
        }

        colorIndicator.onMouseClick {
            println("clicked color indicator")
            if (!this@DefaultColorComponent.children.contains(colorPicker)) {
                colorPicker.unhide()
                colorPicker.setFloating(true)
            } else {
                colorPicker.hide()
                colorPicker.setFloating(false)
            }
        }

        colorPicker.onValueChanged {
            colorIndicator.setColor(it)
            input.textInput.setText(createColorString(it))
            colorOption = it
        }
    }

    override fun closePopups() {
        colorPicker.setFloating(false)
        colorPicker.hide()
    }

    private fun createColorString(color: Color) =
        "#%06x".format(color.rgb and 0xffffff) + if (hasAlpha) {
            "%02x".format(color.alpha)
        } else ""
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

    private var pickerIndicator: UIContainer
    private var hueIndicator: UIBlock

    init {
        constrain {
            width = 70.pixels
            height = if (hasAlpha) 65.pixels else 55.pixels
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
        pickerIndicator = UIContainer().constrain {
            x = (RelativeConstraint(currentSaturation) - 3.5.pixels)
            y = (RelativeConstraint(1f - currentBrightness) - 3.5.pixels)
            width = 2.pixels
            height = 2.pixels
        } effect OutlineEffect(
            color = Color.WHITE,
            width = 1f
        )
        pickerBox.onMouseClick {
            draggingPicker = true
            currentSaturation = it.relativeX / pickerBox.getWidth()
            currentBrightness = 1f - (it.relativeY / pickerBox.getHeight())
            updatePicker()
        }.onMouseRelease {
            draggingPicker = false
        }.onMouseDrag { mouseX, mouseY, _ ->
            if (!draggingPicker) return@onMouseDrag
            currentSaturation = (mouseX / pickerBox.getWidth()).coerceIn(0f..1f)
            currentBrightness = 1f - (mouseY / pickerBox.getHeight()).coerceIn(0f..1f)
            updatePicker()
        }

        val hueBox by UIBlock(ConfiguredPalette.backgroundVariant).constrain {
            x = SiblingConstraint(2.5f)
            y = 2.5.pixels
            width = 12.5.pixels
            height = 50.pixels
        } effect OutlineEffect(
            color = ConfiguredPalette.main,
            width = 1f
        ) childOf background
        hueIndicator = UIBlock(Color.WHITE).constrain {
            x = CenterConstraint()
            y = RelativeConstraint(currentHue)
            width = 12.5.pixels
            height = 1.pixel
        }
        hueBox.onMouseClick {
            draggingHue = true
            currentHue = (it.relativeY - 1f) / hueBox.getHeight()
            updateHue()
        }.onMouseRelease {
            draggingHue = false
        }.onMouseDrag { mouseX, mouseY, _ ->
            if (!draggingHue) return@onMouseDrag
            currentHue = ((mouseY - 1f) / hueBox.getHeight()).coerceIn(0f..1f)
            updateHue()
        }

        if (hasAlpha) {
            var mouseDown = false
            var draggingOffset = 0f

            val alphaSlider by UIContainer().constrain {
                x = CenterConstraint()
                y = SiblingConstraint(2.5f)
                width = 55.pixels
                height = 7.5.pixels
            } childOf background
            val alphaTrack by UIBlock(ConfiguredPalette.background).constrain {
                y = CenterConstraint()
                width = 55.pixels
                height = 2.5.pixels
            } effect OutlineEffect(
                color = ConfiguredPalette.main,
                width = 2f
            ) childOf alphaSlider
            val alphaThumb by UIBlock(ConfiguredPalette.background).constrain {
                x = basicXConstraint {
                    (alphaTrack.getLeft() + alphaTrack.getWidth() * currentAlpha) - (it.getWidth() / 2)
                }
                y = CenterConstraint()
                width = 7.5.pixels
                height = 7.5.pixels
            } effect OutlineEffect(
                color = ConfiguredPalette.main,
                width = 2f
            ) childOf alphaSlider

            alphaThumb.onMouseClick {
                draggingOffset = it.relativeX - alphaThumb.getWidth() / 2
                mouseDown = true
                it.stopPropagation()
            }.onMouseRelease {
                draggingOffset = 0f
                mouseDown = false
            }.onMouseDrag { mouseX, _, _ ->
                if (!mouseDown) return@onMouseDrag
                val clamped = (mouseX + alphaThumb.getLeft() - draggingOffset).coerceIn(alphaTrack.getLeft()..alphaTrack.getRight())
                val percentage = ((clamped - alphaTrack.getLeft()) / alphaTrack.getWidth()).coerceIn(0f..1f)
                currentAlpha = percentage
                valueChangedListener(fetchColor())
            }
        }

        // Draw the pickers
        object : UIComponent() {
            override fun draw(stack: UMatrixStack) {
                super.beforeDraw(stack)
                drawColorPicker(stack, this)
                super.draw(stack)
            }
        }.constrain {
            x = CenterConstraint()
            y = CenterConstraint()
            width = 100.percent
            height = 100.percent
        } childOf pickerBox
        pickerIndicator childOf pickerBox
        object : UIComponent() {
            override fun draw(stack: UMatrixStack) {
                super.beforeDraw(stack)
                drawHuePicker(stack, this)
                super.draw(stack)
            }
        }.constrain {
            x = CenterConstraint()
            y = CenterConstraint()
            width = 100.percent
            height = 100.percent
        } childOf hueBox
        hueIndicator childOf hueBox
    }

    private fun fetchColor(x: Float, y: Float, hue: Float) =
        Color(Color.HSBtoRGB(hue, x, y))
    private fun fetchColor() =
        Color((Color.HSBtoRGB(currentHue, currentSaturation, currentBrightness) and 0xffffff) or ((currentAlpha * 255f).roundToInt() shl 24), true)

    private fun updatePicker() {
        val color = fetchColor()

        pickerIndicator.constrain {
            x = (RelativeConstraint(currentSaturation) - 2.5.pixels).coerceIn(1.pixels, 1.pixels(alignOpposite = true))
            y = (RelativeConstraint(1f - currentBrightness) - 2.5.pixels).coerceIn(1.pixels, 1.pixels(alignOpposite = true))
        }

        valueChangedListener(color)
    }

    private fun updateHue() {
        val color = fetchColor()

        hueIndicator.constrain {
            y = RelativeConstraint(currentHue.coerceAtMost(0.98f))
        }

        valueChangedListener(color)
    }

    fun setHsb(hue: Float, saturation: Float, brightness: Float) {
        currentHue = hue
        currentSaturation = saturation
        currentBrightness = brightness
        updatePicker()
        updateHue()
    }

    private fun drawColorPicker(stack: UMatrixStack, component: UIComponent) {
        val left = component.getLeft().toDouble()
        val top = component.getTop().toDouble()
        val right = component.getRight().toDouble()
        val bottom = component.getBottom().toDouble()

        setupDraw()
        val graphics = UGraphics.getFromTessellator()

        graphics.beginWithDefaultShader(UGraphics.DrawMode.QUADS, UGraphics.CommonVertexFormats.POSITION_COLOR)
        val height = bottom - top

        for (x in 0..49) {
            val curLeft = left + (right - left).toFloat() * x.toFloat() / 50f
            val curRight = left + (right - left).toFloat() * (x.toFloat() + 1) / 50f

            var first = true
            for (y in 0..50) {
                val yPos = top + (y.toFloat() * height / 50.0)
                val color = fetchColor(x.toFloat() / 50f, 1 - y.toFloat() / 50f, currentHue)

                if (!first) {
                    drawVertex(graphics, stack, curLeft, yPos, color)
                    drawVertex(graphics, stack, curRight, yPos, color)
                }
                if (y < 50) {
                    drawVertex(graphics, stack, curRight, yPos, color)
                    drawVertex(graphics, stack, curLeft, yPos, color)
                }

                first = false
            }

        }

        graphics.drawDirect()
        cleanupDraw()
    }

    private fun drawHuePicker(stack: UMatrixStack, component: UIComponent) {
        val left = component.getLeft().toDouble()
        val top = component.getTop().toDouble()
        val right = component.getRight().toDouble()
        val height = component.getHeight().toDouble()

        setupDraw()
        val graphics = UGraphics.getFromTessellator()

        graphics.beginWithDefaultShader(UGraphics.DrawMode.QUADS, UGraphics.CommonVertexFormats.POSITION_COLOR)

        var first = true
        for ((i, color) in hueColorList.withIndex()) {
            val yPos = top + (i.toFloat() * height / 50.0)
            if (!first) {
                drawVertex(graphics, stack, left, yPos, color)
                drawVertex(graphics, stack, right, yPos, color)
            }

            drawVertex(graphics, stack, right, yPos, color)
            drawVertex(graphics, stack, left, yPos, color)

            first = false
        }

        graphics.drawDirect()
        cleanupDraw()
    }

    private fun setupDraw() {
        UGraphics.enableBlend()
        UGraphics.disableAlpha()
        UGraphics.tryBlendFuncSeparate(770, 771, 1, 0)
        UGraphics.shadeModel(7425)
    }

    private fun cleanupDraw() {
        UGraphics.shadeModel(7424)
        UGraphics.disableBlend()
        UGraphics.enableAlpha()
    }

    private fun drawVertex(graphics: UGraphics, matrixStack: UMatrixStack, x: Double, y: Double, color: Color) {
        graphics
            .pos(matrixStack, x, y, 0.0)
            .color(color.red.toFloat() / 255f, color.green.toFloat() / 255f, color.blue.toFloat() / 255f, 1f)
            .endVertex()
    }

    fun onValueChanged(listener: (Color) -> Unit) {
        valueChangedListener = listener
    }

    companion object {
        private val hueColorList = (0..50).map {
            Color.getHSBColor(it / 50f, 1f, 0.7f)
        }
    }
}
