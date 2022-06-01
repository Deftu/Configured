package xyz.unifycraft.configured.options.annnotations

import xyz.unifycraft.configured.options.Option

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
annotation class SwitchOption(
    val name: String,
    val description: String = "",
    val hidden: Boolean = Option.DEFAULT_HIDDEN
)
