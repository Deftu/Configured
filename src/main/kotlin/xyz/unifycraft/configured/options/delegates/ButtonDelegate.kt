package xyz.unifycraft.configured.options.delegates

import xyz.unifycraft.configured.Configurable
import xyz.unifycraft.configured.options.Option
import xyz.unifycraft.configured.options.OptionType

class ButtonDelegate(
    runnable: Runnable
) : OptionDelegate<Runnable>(
    runnable
) {
    override lateinit var name: String
    var text = ""
}

fun Configurable.button(runnable: Runnable, block: ButtonDelegate.() -> Unit): ButtonDelegate {
    val delegate = ButtonDelegate(runnable)
    block(delegate)
    options.add(Option(delegate.name, delegate.localizedName, delegate.description, delegate.category, delegate.default, delegate.hidden, delegate.tags, OptionType.BUTTON, mapOf(
        "text" to delegate.text
    ), {
        delegate.default.run()
    }, {
        throw UnsupportedOperationException("Cannot set a button option!")
    }))
    return delegate
}
