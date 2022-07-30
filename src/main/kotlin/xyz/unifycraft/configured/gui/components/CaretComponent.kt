package xyz.unifycraft.configured.gui.components

import gg.essential.elementa.components.UIContainer
import gg.essential.elementa.components.UIImage
import gg.essential.elementa.constraints.ColorConstraint
import gg.essential.elementa.dsl.*
import java.awt.Color

class CaretComponent(
    val color: ColorConstraint = Color.WHITE.toConstraint()
) : UIContainer() {
    constructor(
        color: Color
    ) : this(color.toConstraint())

    private val caret by UIImage.ofResourceCached("/assets/configured/icons/caret.png").constrain {
        width = 100.percent
        height = 100.percent
        color = this@CaretComponent.color
    } childOf this
}
