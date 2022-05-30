package xyz.unifycraft.configured.options.annnotations

import xyz.unifycraft.configured.options.Option

annotation class IntegerOption(
    val name: String,
    val max: Int,
    val min: Int,
    val description: String = "",
    val hidden: Boolean = Option.DEFAULT_HIDDEN
)
