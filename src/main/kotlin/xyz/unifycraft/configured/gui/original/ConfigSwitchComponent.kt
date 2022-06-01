package xyz.unifycraft.configured.gui.original

import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.dsl.*
import xyz.unifycraft.configured.gui.ConfigOptionComponent
import xyz.unifycraft.configured.options.Option

class ConfigSwitchComponent(
    override val option: Option
) : ConfigOptionComponent() {

    // Data
    var toggle: Boolean
        get() = option.invoke().toString().toBoolean()
        set(value) = option.set(value)

    // Sub-components
    lateinit var switch: UIBlock

    init {
        val background by UIBlock().constrain {
            width = 20.pixels()
        }
    }
}
