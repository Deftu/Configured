package xyz.unifycraft.configured.options.annnotations

import xyz.unifycraft.configured.options.Option

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
annotation class PercentageOption(
    val name: String,
    val max: Float,
    val min: Float,
    val description: String = "",
    val hidden: Boolean = Option.DEFAULT_HIDDEN
)
