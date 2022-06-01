package xyz.unifycraft.configured.options.processor

import xyz.unifycraft.configured.Configurable
import xyz.unifycraft.configured.options.Option
import xyz.unifycraft.configured.options.OptionType
import xyz.unifycraft.configured.options.annnotations.*

object AnnotationOptionProcessor : OptionProcessor {
    override fun process(configurable: Configurable): List<Option> {
        val clz = configurable::class.java
        val fields = clz.declaredFields
        val options = mutableListOf<Option>()
        fields@ for (field in fields) {
            //#if MC<=11502
            field.isAccessible = true
            //#else
            //$$ field.trySetAccessible()
            //#endif
            annotations@ for (annotation in field.declaredAnnotations) {
                val data = process(annotation) ?: continue@annotations
                val value = try {
                    field.get(configurable)
                } catch (e: Exception) {
                    e.printStackTrace()
                    continue@fields
                }
                val category = (field.declaredAnnotations.find {
                    it is OptionCategory
                } as? OptionCategory)?.value ?: Option.DEFAULT_CATEGORY
                options.add(Option(data.name, data.description, category, value, data.hidden, data.type, data.attributes, {
                    value
                }, {
                    field.set(configurable, it)
                }))
            }
        }
        return options
    }

    private fun process(annotation: Annotation) = when (annotation) {
        is SwitchOption -> OptionData(annotation.name, annotation.description, annotation.hidden, OptionType.SWITCH)
        is CheckboxOption -> OptionData(annotation.name, annotation.description, annotation.hidden, OptionType.CHECKBOX)
        is TextOption -> OptionData(annotation.name, annotation.description, annotation.hidden, OptionType.TEXT, mapOf("protected" to annotation.protectedText, "limit" to annotation.limit))
        is ParagraphOption -> OptionData(annotation.name, annotation.description, annotation.hidden, OptionType.PARAGRAPH, mapOf("protected" to annotation.protectedText, "limit" to annotation.limit))
        is PercentageOption -> OptionData(annotation.name, annotation.description, annotation.hidden, OptionType.PERCENTAGE, mapOf("min" to annotation.min, "max" to annotation.max))
        is IntegerOption -> OptionData(annotation.name, annotation.description, annotation.hidden, OptionType.INTEGER, mapOf("min" to annotation.min, "max" to annotation.max))
        is ColorOption -> OptionData(annotation.name, annotation.description, annotation.hidden, OptionType.COLOR, mapOf("alpha" to annotation.alpha))
        is FileOption -> OptionData(annotation.name, annotation.description, annotation.hidden, OptionType.FILE, mapOf("extensions" to annotation.extensions, "directory" to annotation.directory))
        else -> null
    }
}

private data class OptionData(
    val name: String,
    val description: String,
    val hidden: Boolean,
    val type: OptionType,
    val attributes: Map<String, Any> = mapOf()
)
