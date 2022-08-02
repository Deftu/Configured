package xyz.unifycraft.configured.options

import java.awt.Color
import java.io.File

/**
 * An enum representing each possible
 * type of option which can be created
 * inside of a [Config].
 */
enum class OptionType(
    val type: Class<*>,
    val serializable: Boolean
) {
    SWITCH(Boolean::class.java, true),
    TEXT(String::class.java, true),
    PERCENTAGE(Float::class.java, true),
    INTEGER(Int::class.java, true),
    COLOR(Color::class.java, true),
    FILE(File::class.java, true),
    BUTTON(Nothing::class.java, false)
}
