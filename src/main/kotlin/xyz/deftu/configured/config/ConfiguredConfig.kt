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

import gg.essential.universal.ChatColor
import xyz.deftu.configured.Config
import xyz.deftu.configured.Configured
import xyz.deftu.configured.options.delegates.color
import xyz.deftu.configured.options.delegates.switch
import java.io.File

object ConfiguredConfig : Config(
    directory = File(configDirectory, Configured.NAME),
    title = "${ChatColor.AQUA}${Configured.NAME}"
) {
    internal var darkMode by switch(true) {
        name = "Dark Mode"
        description = "Use a dark theme for the Configured GUI."
        tags("dark", "dark theme")
        tags("light", "light theme")
        tag("theme")
    }

    private var primary by color(ConfiguredPalette.getPrimary()) {
        name = "Primary Color"
        description = "The primary colour of EnhancedPixel's branding."
        tags("primary", "branding", "accent")
    }

    private var primaryVariant by color(ConfiguredPalette.getPrimaryVariant()) {
        name = "Primary Variant Color"
        description = "A variant of EnhancedPixel's primary colour."
        tags("secondary", "branding", "accent")
    }

    init {
        initialize()
    }

    override fun onInitialize() {
        setupPalette()
    }

    private fun setupPalette() {
        ConfiguredPalette.primary.set(primary)
        ConfiguredPalette.primaryVariant.set(primaryVariant)
    }
}
