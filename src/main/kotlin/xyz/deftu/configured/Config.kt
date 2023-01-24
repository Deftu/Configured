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

package xyz.deftu.configured

import xyz.deftu.configured.gui.ConfigMenu
import xyz.deftu.configured.gui.original.DefaultConfigMenu
import xyz.deftu.configured.options.Option
import xyz.deftu.configured.options.OptionCollector
import xyz.deftu.configured.options.OptionSerializer
import java.io.File

abstract class Config @JvmOverloads constructor(
    val directory: File,
    val title: String,
    val menu: (Config, String) -> ConfigMenu = { config, title -> DefaultConfigMenu(config, title) },
) : Configurable {
    companion object {
        val configDirectory by lazy {
            val directory = File("config")
            if (!directory.exists() && !directory.mkdirs())
                throw IllegalStateException("Could not create default config directory")

            directory
        }
    }

    val collector = OptionCollector()
    private val serializer = OptionSerializer()
    override val options = mutableListOf<Option>()

    // Initialization

    private var initialized = false

    fun initialize() {
        if (initialized)
            return

        // Initialize Configured internally.
        Configured.initialize()

        // Collect our options.
        collector.collect(this)
        serializer.initialize(this, collector)

        // Load every single option in our collector.
        load()

        // Save any new options.
        markDirty()
        save()

        // Save the config every time the game closes.
        Runtime.getRuntime().addShutdownHook(Thread({
            markDirty()
            save()
        }, "Configured ${javaClass.simpleName} Shutdown Hook"))

        // Run any operations required by the config.
        onInitialize()

        initialized = true
    }

    open fun onInitialize() {
    }

    fun addOther(configurable: Configurable) {
        collector.collect(configurable)
        load()
    }

    // Serialization

    fun markDirty() = serializer.markDirty()

    fun save() {
        serializer.serialize()
    }

    fun load() {
        serializer.deserialize()
    }

    // Dependency/hide APIs

    fun hideIf(propName: String, condition: Boolean) {
        TODO("Not implemented")
    }

    fun makeDependant(propName: String, dependencyName: String) {
        TODO("Not implemented")
    }

    // Listener API

    fun <T> addListener(propName: String, listener: (T) -> Unit) {
        TODO("Not implemented")
    }

    // GUI

    fun menu(): ConfigMenu = menu.invoke(this, title)
}
