package xyz.unifycraft.configured.options.delegates

import xyz.unifycraft.configured.Configurable
import xyz.unifycraft.configured.options.Option
import xyz.unifycraft.configured.options.OptionType
import kotlin.properties.Delegates

class PercentageDelegate(
    default: Float
) : OptionDelegate<Float>(
    default
) {
    override lateinit var name: String
    var min by Delegates.notNull<Int>()
    var max by Delegates.notNull<Int>()
}

/**
 * Creates a percentage option based
 * on the data provided.
 *
 * @param default The default value of the option.
 * @param block The metadata of the option.
 */
fun Configurable.percentage(default: Float, block: PercentageDelegate.() -> Unit): PercentageDelegate {
    val delegate = PercentageDelegate(default)
    block(delegate)
    options.add(Option(delegate.name, delegate.localizedName, delegate.description, delegate.category, delegate.default, delegate.hidden, delegate.tags, OptionType.PERCENTAGE, mapOf(
        "min" to delegate.min,
        "max" to delegate.max
    ), {
        delegate.value
    }, {
        delegate.setValue(this, delegate::value, it.toString().toFloat())
    }))
    return delegate
}
