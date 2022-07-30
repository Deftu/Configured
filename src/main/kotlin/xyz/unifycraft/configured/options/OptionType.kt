package xyz.unifycraft.configured.options

import java.awt.Color
import java.io.File

enum class OptionType(
    val type: Class<*>,
    val serializable: Boolean
) {
    SWITCH(Boolean::class.java, true), // Yep
    TEXT(String::class.java, true), // Yep
    PERCENTAGE(Float::class.java, true), // Nope
    INTEGER(Int::class.java, true), // Yep
    COLOR(Color::class.java, true), // Nope
    FILE(File::class.java, true), // Yep
    BUTTON(Nothing::class.java, false) // Yep
}
