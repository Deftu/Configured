package xyz.unifycraft.configured.options.annnotations

import xyz.unifycraft.configured.options.Option

annotation class ParagraphOption(
    val name: String,
    val description: String = "",
    val hidden: Boolean = Option.DEFAULT_HIDDEN,
    val protectedText: Boolean = false,
    val limit: Int = 128
)
