package xyz.unifycraft.configured.options.dsl

import java.awt.Color
import java.lang.reflect.Field

class ColorOptionScope(
    default: Color,
    field: Field
) : OptionScope<Color>(
    default,
    field
) {
    override lateinit var name: String
    var alpha = false
}
