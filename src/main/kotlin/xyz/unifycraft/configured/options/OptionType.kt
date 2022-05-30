package xyz.unifycraft.configured.options

import java.awt.Color

enum class OptionType(
    val type: Class<*>,
    val serializable: Boolean
) {
    CHECKBOX(Boolean::class.java, true),
    SWITCH(Boolean::class.java, true),
    TEXT(String::class.java, true),
    PARAGRAPH(String::class.java, true),
    PERCENTAGE(Float::class.java, true),
    INTEGER(Int::class.java, true),
    COLOR(Color::class.java, true),
    BUTTON(Nothing::class.java, false)
}
