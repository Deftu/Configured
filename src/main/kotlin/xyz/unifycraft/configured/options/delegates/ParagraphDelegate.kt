package xyz.unifycraft.configured.options.delegates

import xyz.unifycraft.configured.Configurable
import xyz.unifycraft.configured.options.Option
import xyz.unifycraft.configured.options.OptionType

class ParagraphDelegate(
    default: String
) : OptionDelegate<String>(
    default
) {
    override lateinit var name: String
    var protectedText: Boolean = false
    var limit: Int = 128
}

fun Configurable.paragraph(default: String, block: ParagraphDelegate.() -> Unit): ParagraphDelegate {
    val delegate = ParagraphDelegate(default)
    block(delegate)
    options.add(Option(delegate.name, delegate.description, delegate.category, delegate.default, delegate.hidden, OptionType.PARAGRAPH, mapOf(
        "protected" to delegate.protectedText,
        "limit" to delegate.limit
    ), {
        delegate.value
    }, {
        delegate.setValue(this@paragraph, delegate::value, it.toString())
    }))
    return delegate
}