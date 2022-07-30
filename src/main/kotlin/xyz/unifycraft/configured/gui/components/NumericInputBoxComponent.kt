package xyz.unifycraft.configured.gui.components

class NumericInputBoxComponent(
    var value: Number = 0,
    var min: Int = 0,
    var max: Int = Int.MAX_VALUE,
) : InputBoxComponent() {
    init {
        validate(value.toString())
        apply(value.toString())
        textInput.onActivate(this::validate)
        textInput.onUpdate(this::validate)
    }

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

    fun apply(input: String) = textInput.setText(input)

    companion object {
        val nonNumericRegex = "[^0-9.]".toRegex()
    }
}
