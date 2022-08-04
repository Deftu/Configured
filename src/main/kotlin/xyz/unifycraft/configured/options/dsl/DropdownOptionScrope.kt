package xyz.unifycraft.configured.options.dsl

import java.lang.reflect.Field

class DropdownOptionScrope(
    default: Int,
    field: Field
) : OptionScope<Int>(
    default,
    field
) {
    override lateinit var name: String
    var options = listOf<String>()

    fun option(name: String) {
        val options = options.toMutableList()
        options.add(name)
        this.options = options
    }

    fun options(vararg names: String) {
        for (name in names) option(name)
    }
}
