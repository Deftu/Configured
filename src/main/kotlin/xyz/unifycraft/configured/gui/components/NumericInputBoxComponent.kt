package xyz.unifycraft.configured.gui.components

class NumericInputBoxComponent(
    var value: Int = 0,
    var min: Int = 0,
    var max: Int = Int.MAX_VALUE,
) : InputBoxComponent() {
    init {
        textInput.onActivate(this::validate)
        textInput.onUpdate(this::validate)
    }

    fun validate(input: String) {
        var input = input.replace(nonNumericRegex, "")
        if (input.isEmpty()) input = "0"
        var num = input.toInt()
        if (num < min || num > max) {
            num = num.coerceIn(min, max)
            textInput.setText(num.toString())
        }
        value = num
    }

    companion object {
        val nonNumericRegex = "[^0-9.]".toRegex()
    }
}
