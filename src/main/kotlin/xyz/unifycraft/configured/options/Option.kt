package xyz.unifycraft.configured.options

import net.minecraft.client.resources.I18n

/**
 * Class representing a single option in the config,
 * holding metadata and values for the option.
 */
data class Option(
    val name: String,
    private val localizedNameKey: String,
    val description: String,
    val category: String,
    val default: Any,
    var hidden: Boolean,
    val tags: List<String>,
    val type: OptionType,
    val attributes: Map<String, Any>,
    private val getter: () -> Any,
    private val setter: (Any) -> Unit
) {
    val localizedName: String
        get() = I18n.format(localizedNameKey)

    fun invoke() =
        getter()
    fun set(value: Any) =
        setter(value)

    companion object {
        const val DEFAULT_CATEGORY = "General"
        const val DEFAULT_HIDDEN = false
    }
}
