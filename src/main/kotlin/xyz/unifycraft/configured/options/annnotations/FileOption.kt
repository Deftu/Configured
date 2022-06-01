package xyz.unifycraft.configured.options.annnotations

import xyz.unifycraft.configured.options.Option

annotation class FileOption(
    val name: String,
    val description: String = "",
    val hidden: Boolean = Option.DEFAULT_HIDDEN,
    val extensions: Array<String> = [],
    val directory: Boolean = false
)
