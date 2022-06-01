package xyz.unifycraft.configured.options.dsl

import java.lang.reflect.Field
import kotlin.properties.Delegates

class IntegerOptionScope(
    default: Int,
    field: Field
) : OptionScope<Int>(
    default,
    field
) {
    override lateinit var name: String

    var min by Delegates.notNull<Int>()
    var max by Delegates.notNull<Int>()
}
