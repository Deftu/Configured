/*
 * This file is a part of the Configured library
 * Copyright (C) 2023 Deftu (https://deftu.xyz)
 *
 * DO NOT remove or alter copyright notices, or remove this file header.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package xyz.deftu.configured.gui.components

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
