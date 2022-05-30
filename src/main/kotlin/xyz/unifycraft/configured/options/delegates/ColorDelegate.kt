package xyz.unifycraft.configured.options.delegates

import xyz.unifycraft.configured.Configurable
import xyz.unifycraft.configured.options.Option
import xyz.unifycraft.configured.options.OptionType
import java.awt.Color

class ColorDelegate(
    default: Color
) : OptionDelegate<Color>(
    default
) {
    override lateinit var name: String
    var alpha = false
}

fun Configurable.color(default: Color, block: ColorDelegate.() -> Unit): ColorDelegate {
    val delegate = ColorDelegate(default)
    block(delegate)
    options.add(Option(delegate.name, delegate.description, delegate.category, delegate.default, delegate.hidden, OptionType.COLOR, mapOf(
        "alpha" to delegate.alpha
    ), {
        delegate.value
    }, {
        delegate.setValue(this, delegate::value, (it as? Color) ?: delegate.default)
    }))
    return delegate
}
