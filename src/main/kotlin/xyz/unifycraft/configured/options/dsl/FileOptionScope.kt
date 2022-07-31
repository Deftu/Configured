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
}
