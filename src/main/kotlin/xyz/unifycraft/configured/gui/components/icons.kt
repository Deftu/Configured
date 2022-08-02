package xyz.unifycraft.configured.gui.components

import gg.essential.elementa.components.UIImage
import gg.essential.elementa.constraints.ColorConstraint
import gg.essential.elementa.dsl.*
import java.awt.Color

/**
 * Creates a [UIImage] with a caret icon
 * as the image.
 */
fun createCaretIcon() = UIImage.ofResourceCached("/assets/configured/icons/caret.png")
/**
 * Creates a [UIImage] with a search icon
 * as the image.
 */
fun createSearchIcon() = UIImage.ofResourceCached("/assets/configured/icons/search.png")
/**
 * Creates a [UIImage] with an eye icon
 * as the image.
 *
 * @param type The type of icon that will be used. Valid
 * types are "crossed".
 */
fun createEyeIcon(type: String = "") = UIImage.ofResourceCached("/assets/configured/icons/eye${if (type.isNotBlank()) "-$type" else ""}.png")
