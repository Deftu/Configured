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
    internal val extensions = mutableListOf<String>()
    var directory = false

    fun extensions(vararg extensions: String) {
        this.extensions.addAll(extensions)
    }

    fun extensions(extensions: List<String>) {
        this.extensions.addAll(extensions)
    }

    fun extension(extension: String) {
        this.extensions.add(extension)
    }
}

fun Configurable.file(default: File, block: FileDelegate.() -> Unit): FileDelegate {
    val delegate = FileDelegate(default)
    block(delegate)
    options.add(Option(delegate.name, delegate.description, delegate.category, delegate.default, delegate.hidden, OptionType.FILE, mapOf(
        "extensions" to delegate.extensions,
        "directory" to delegate.directory
    ), {
        delegate.value
    }, {
        delegate.setValue(this, delegate::value, File(it.toString()))
    }))
    return delegate
}
