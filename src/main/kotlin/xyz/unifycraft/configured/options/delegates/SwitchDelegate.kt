package xyz.unifycraft.configured.options.delegates

import xyz.unifycraft.configured.Configurable
import xyz.unifycraft.configured.options.Option
import xyz.unifycraft.configured.options.OptionType

class SwitchDelegate(
    default: Boolean
) : OptionDelegate<Boolean>(default) {
    override lateinit var name: String
}

/**
 * Creates a switch option based
 * on the data provided.
 *
 * @param default The default value of the option.
 * @param block The metadata of the option.
 */
fun Configurable.switch(default: Boolean, block: SwitchDelegate.() -> Unit): SwitchDelegate {
    val delegate = SwitchDelegate(default)
    block(delegate)
    options.add(Option(delegate.name, delegate.localizedName, delegate.description, delegate.category, delegate.default, delegate.hidden, delegate.tags, OptionType.SWITCH, mapOf(), {
        delegate.value
    }, {
        delegate.setValue(this@switch, delegate::value, it.toString().toBoolean())
    }))
    return delegate
}
