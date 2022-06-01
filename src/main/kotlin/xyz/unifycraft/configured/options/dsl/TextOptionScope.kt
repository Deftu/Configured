package xyz.unifycraft.configured.options.dsl

import java.lang.reflect.Field

class TextOptionScope(
    default: String,
    field: Field
) : OptionScope<String>(
    default,
    field
) {
    override lateinit var name: String
    var protectedText: Boolean = false
    var limit: Int = 128
}
