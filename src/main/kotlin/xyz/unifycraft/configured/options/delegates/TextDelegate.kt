package xyz.unifycraft.configured.options.delegates

import xyz.unifycraft.configured.Configurable
import xyz.unifycraft.configured.options.Option
import xyz.unifycraft.configured.options.OptionType

class TextDelegate(
    default: String
) : OptionDelegate<String>(
    default
) {
    override lateinit var name: String
    var protectedText: Boolean = false
    var limit: Int = 128
}

fun Configurable.text(default: String, block: TextDelegate.() -> Unit): TextDelegate {
    val delegate = TextDelegate(default)
    block(delegate)
    options.add(Option(delegate.name, delegate.localizedName, delegate.description, delegate.category, delegate.default, delegate.hidden, delegate.tags, OptionType.TEXT, mapOf(
        "protected" to delegate.protectedText,
        "limit" to delegate.limit
    ), {
        delegate.value
    }, {
        delegate.setValue(this@text, delegate::value, it.toString())
    }))
    return delegate
}
