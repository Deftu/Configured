package xyz.unifycraft.configured.options.delegates

import xyz.unifycraft.configured.Config
import xyz.unifycraft.configured.Configurable
import xyz.unifycraft.configured.options.Option
import xyz.unifycraft.configured.options.OptionType

class SwitchDelegate(
    default: Boolean
) : OptionDelegate<Boolean>(default) {
    override lateinit var name: String
}

fun Configurable.switch(default: Boolean, block: SwitchDelegate.() -> Unit): SwitchDelegate {
    val delegate = SwitchDelegate(default)
    block(delegate)
    options.add(Option(delegate.name, delegate.description, delegate.category, delegate.default, delegate.hidden, OptionType.SWITCH, mapOf(), {
        delegate.value
    }, {
        delegate.setValue(this@switch, delegate::value, it.toString().toBoolean())
    }))
    return delegate
}
