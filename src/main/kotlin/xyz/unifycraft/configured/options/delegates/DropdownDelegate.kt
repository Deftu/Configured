package xyz.unifycraft.configured.options.delegates

import xyz.unifycraft.configured.Configurable
import xyz.unifycraft.configured.options.Option
import xyz.unifycraft.configured.options.OptionType

class DropdownDelegate(
    default: Int
) : OptionDelegate<Int>(
    default
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

fun Configurable.dropdown(default: Int, block: DropdownDelegate.() -> Unit): DropdownDelegate {
    val delegate = DropdownDelegate(default)
    block(delegate)
    options.add(Option(delegate.name, delegate.localizedName, delegate.description, delegate.category, delegate.default, delegate.hidden, delegate.tags, OptionType.DROPDOWN, mapOf(
        "options" to delegate.options
    ), {
        delegate.value
    }, {
        delegate.setValue(this, delegate::value, it.toString().toInt())
    }))
    return delegate
}
