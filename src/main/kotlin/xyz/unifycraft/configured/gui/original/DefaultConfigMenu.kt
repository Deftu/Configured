package xyz.unifycraft.configured.gui.original

import xyz.unifycraft.configured.gui.ConfigMenu
import xyz.unifycraft.configured.gui.ConfigOptionComponent
import xyz.unifycraft.configured.options.Option
import xyz.unifycraft.configured.options.OptionType

class DefaultConfigMenu : ConfigMenu() {
    override fun createOptionComponent(option: Option) = when (option.type) {
        OptionType.SWITCH -> ConfigSwitchComponent(option)
        else -> object : ConfigOptionComponent() {
            override val option: Option
                get() = option
        }
    }
}
