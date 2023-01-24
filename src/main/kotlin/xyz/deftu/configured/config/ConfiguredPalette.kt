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

package xyz.deftu.configured.config

import gg.essential.elementa.state.BasicState
import gg.essential.elementa.utils.withAlpha
import java.awt.Color

private typealias hex = Color
object ConfiguredPalette {
    // Primary
    internal val primary = BasicState(hex(0x4E6AD0))
    internal val primaryVariant = BasicState(hex(0x3352C1))

    // Background
    internal val backgroundDark = BasicState(hex(0x28282B))
    internal val backgroundDark2 = BasicState(hex(0x151316))
    internal val backgroundDark3 = BasicState(hex(0x1D1D20))
    internal val backgroundDark4 = BasicState(hex(0x131316))

    internal val backgroundLight = BasicState(hex(0xFDFBF9))
    internal val backgroundLight2 = BasicState(hex(0xDBD7D2))
    internal val backgroundLight3 = BasicState(hex(0xFAF5F0))
    internal val backgroundLight4 = BasicState(hex(0xD2CDC6))

    // Button
    internal val button = BasicState(hex(0x5165AF))
    internal val buttonFaded = BasicState(hex(0x5165AF).withAlpha(0.85f))

    // Text
    internal val textDark = BasicState(hex(0xFDFBF9))
    internal val textDarkFaded = BasicState(hex(0xFDFBF9).withAlpha(0.75f))
    internal val textLight = BasicState(hex(0x28282B))
    internal val textLightFaded = BasicState(hex(0x28282B).withAlpha(0.75f))

    // State
    internal val success = BasicState(hex(0x05F140))
    internal val error = BasicState(hex(0xDD0426))
    internal val disabled = BasicState(hex(0x8C0317))

    // Public access
    fun getPrimary() = primary.get()
    fun getPrimaryVariant() = primaryVariant.get()
    internal fun getBackgroundDark() = backgroundDark.get()
    internal fun getBackgroundDark2() = backgroundDark2.get()
    internal fun getBackgroundDark3() = backgroundDark3.get()
    internal fun getBackgroundDark4() = backgroundDark4.get()
    internal fun getBackgroundLight() = backgroundLight.get()
    internal fun getBackgroundLight2() = backgroundLight2.get()
    internal fun getBackgroundLight3() = backgroundLight3.get()
    internal fun getBackgroundLight4() = backgroundLight4.get()
    fun getButton() = button.get()
    fun getButtonFaded() = buttonFaded.get()
    internal fun getTextDark() = textDark.get()
    internal fun getTextDarkFaded() = textDarkFaded.get()
    internal fun getTextLight() = textLight.get()
    internal fun getTextLightFaded() = textLightFaded.get()
    fun getSuccess() = success.get()
    fun getError() = error.get()
    fun getDisabled() = disabled.get()

    // Utility functions
    fun getBackground() = if (ConfiguredConfig.darkMode) getBackgroundDark() else getBackgroundLight()
    fun getBackground2() = if (ConfiguredConfig.darkMode) getBackgroundDark2() else getBackgroundLight2()
    fun getBackground3() = if (ConfiguredConfig.darkMode) getBackgroundDark3() else getBackgroundLight3()
    fun getBackground4() = if (ConfiguredConfig.darkMode) getBackgroundDark4() else getBackgroundLight4()
    fun getText() = if (ConfiguredConfig.darkMode) getTextDark() else getTextLight()
    fun getTextFaded() = if (ConfiguredConfig.darkMode) getTextDarkFaded() else getTextLightFaded()
}
