package xyz.unifycraft.configured.gui.components

/**
 * A type of [InputBoxComponent] which
 * only accepts numerical values.
 * (f.ex -20, 21, 62, etc)
 */
class NumericInputBoxComponent(
    var value: Number = 0,
    var min: Int = 0,
    var max: Int = Int.MAX_VALUE,
) : InputBoxComponent() {
    init {
        validate(value.toString())
        apply(value.toString())
        textInput.onActivate(this::validate)
        onValueChanged(this::validate)
    }

    /**
     * Validates the input and updates the value if valid.
     */
    fun validate(input: String) {
        var input = input.replace(nonNumericRegex, "")
        if (input.isEmpty()) input = "0"
        var num = input.toFloat()
        if (num < min || num > max) {
            num = num.coerceIn(min.toFloat(), max.toFloat())
            textInput.setText(num.toString())
        }
        value = num
    }

    /**
     * Applies the value to the text input.
     */
    fun apply(input: String) = textInput.setText(input)

    companion object {
        val nonNumericRegex = "[^0-9.]".toRegex()
    }
}
