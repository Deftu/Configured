package xyz.unifycraft.configured.options.delegates

import xyz.unifycraft.configured.Configurable
import xyz.unifycraft.configured.options.Option
import xyz.unifycraft.configured.options.OptionType

class CheckboxDelegate(
    default: Boolean
) : OptionDelegate<Boolean>(
    default
) {
    override lateinit var name: String
}

fun Configurable.checkbox(default: Boolean, block: CheckboxDelegate.() -> Unit): CheckboxDelegate {
    val delegate = CheckboxDelegate(default)
    block(delegate)
    options.add(Option(delegate.name, delegate.description, delegate.category, delegate.default, delegate.hidden, OptionType.CHECKBOX, mapOf(), {
        delegate.value
    }, {
        delegate.setValue(this@checkbox, delegate::value, it.toString().toBoolean())
    }))
    return delegate
}
