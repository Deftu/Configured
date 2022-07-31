package xyz.unifycraft.configured.options.delegates

import xyz.unifycraft.configured.Configurable
import xyz.unifycraft.configured.options.Option
import xyz.unifycraft.configured.options.OptionType
import kotlin.properties.Delegates

class IntegerDelegate(
    default: Int
) : OptionDelegate<Int>(
    default
) {
    override lateinit var name: String
    var min by Delegates.notNull<Int>()
    var max by Delegates.notNull<Int>()
}

fun Configurable.integer(default: Int, block: IntegerDelegate.() -> Unit): IntegerDelegate {
    val delegate = IntegerDelegate(default)
    block(delegate)
    options.add(Option(delegate.name, delegate.localizedName, delegate.description, delegate.category, delegate.default, delegate.hidden, delegate.tags, OptionType.INTEGER, mapOf(
        "min" to delegate.min,
        "max" to delegate.max
    ), {
        delegate.value
    }, {
        delegate.setValue(this, delegate::value, it.toString().toInt())
    }))
    return delegate
}
