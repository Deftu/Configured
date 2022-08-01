package xyz.unifycraft.configured.gui.components

import gg.essential.elementa.components.UIImage
import gg.essential.elementa.constraints.ColorConstraint
import gg.essential.elementa.dsl.*
import java.awt.Color

fun createCaretIcon() = UIImage.ofResourceCached("/assets/configured/icons/caret.png")
fun createSearchIcon() = UIImage.ofResourceCached("/assets/configured/icons/search.png")
fun createEyeIcon(type: String = "") = UIImage.ofResourceCached("/assets/configured/icons/eye${if (type.isNotBlank()) "-$type" else ""}.png")
