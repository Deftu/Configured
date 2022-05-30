package xyz.unifycraft.configured.options

data class Option(
    val name: String,
    val description: String,
    val category: String,
    val default: Any,
    var hidden: Boolean,
    val type: OptionType,
    val attributes: Map<String, Any>,
    private val getter: () -> Any,
    private val setter: (Any) -> Unit
) {
    fun invoke() =
        getter()
    fun set(value: Any) =
        setter(value)

    companion object {
        const val DEFAULT_CATEGORY = "General"
        const val DEFAULT_HIDDEN = false
    }
}
