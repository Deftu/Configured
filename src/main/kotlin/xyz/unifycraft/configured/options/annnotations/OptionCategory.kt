package xyz.unifycraft.configured.options.annnotations

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD, AnnotationTarget.FUNCTION)
annotation class OptionCategory(
    val value: String
)
