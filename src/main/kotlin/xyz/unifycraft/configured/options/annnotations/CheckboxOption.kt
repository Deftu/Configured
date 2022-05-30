package xyz.unifycraft.configured.options.annnotations

import xyz.unifycraft.configured.options.Option

annotation class CheckboxOption(
    val name: String,
    val description: String = "",
    val hidden: Boolean = Option.DEFAULT_HIDDEN
)
