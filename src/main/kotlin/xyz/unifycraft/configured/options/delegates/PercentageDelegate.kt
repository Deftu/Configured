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
    var min by Delegates.notNull<Float>()
    var max by Delegates.notNull<Float>()
}

fun Configurable.percentage(default: Float, block: PercentageDelegate.() -> Unit): PercentageDelegate {
    val delegate = PercentageDelegate(default)
    block(delegate)
    options.add(Option(delegate.name, delegate.description, delegate.category, delegate.default, delegate.hidden, OptionType.PERCENTAGE, mapOf(
        "min" to delegate.min,
        "max" to delegate.max
    ), {
        delegate.value
    }, {
        delegate.setValue(this, delegate::value, it.toString().toFloat())
    }))
    return delegate
}
