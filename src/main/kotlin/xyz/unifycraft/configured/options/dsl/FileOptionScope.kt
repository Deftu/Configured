package xyz.unifycraft.configured.options.dsl

import java.io.File
import java.lang.reflect.Field

class FileOptionScope(
    default: File,
    field: Field
) : OptionScope<File>(
    default,
    field
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
