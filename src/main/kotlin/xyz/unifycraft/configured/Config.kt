package xyz.unifycraft.configured

import xyz.unifycraft.configured.gui.ConfigMenu
import xyz.unifycraft.configured.gui.original.DefaultConfigMenu
import xyz.unifycraft.configured.options.Option
import xyz.unifycraft.configured.options.OptionCollector
import xyz.unifycraft.configured.options.OptionSerializer
import java.io.File

abstract class Config @JvmOverloads constructor(
    val directory: File,
    val title: String,
    val menu: (Config, String) -> ConfigMenu = { config, title -> DefaultConfigMenu(config, title) },
) : Configurable {
    val collector = OptionCollector()
    private val serializer = OptionSerializer()
    override val options = mutableListOf<Option>()

    // Initialization

    fun initialize() {
        // Collect our options.
        collector.collect(this)
        serializer.initialize(this, collector)

        // Load every single option in our collector.
        load()

        // Save any new options.
        markDirty()
        save()

        // Run any operations required by the config.
        onInitialize()
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

    fun hideIf(name: String, condition: Boolean) {
        TODO("Not implemented")
    }

    fun makeDependant(name: String, dependencyName: String) {
        TODO("Not implemented")
    }

    // GUI

    fun menu(): ConfigMenu = menu.invoke(this, title)
}
