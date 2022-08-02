package xyz.unifycraft.configured.options.delegates

import xyz.unifycraft.configured.Configurable
import xyz.unifycraft.configured.options.Option
import xyz.unifycraft.configured.options.OptionType
import java.io.File

class FileDelegate(
    default: File
) : OptionDelegate<File>(
    default
) {
    override lateinit var name: String
}

/**
 * Creates a file option based
 * on the data provided.
 *
 * @param default The default value of the option.
 * @param block The metadata of the option.
 */
fun Configurable.file(default: File, block: FileDelegate.() -> Unit): FileDelegate {
    val delegate = FileDelegate(default)
    block(delegate)
    options.add(Option(delegate.name, delegate.localizedName, delegate.description, delegate.category, delegate.default, delegate.hidden, delegate.tags, OptionType.FILE, mapOf(), {
        delegate.value
    }, {
        delegate.setValue(this, delegate::value, File(it.toString()))
    }))
    return delegate
}
