package xyz.unifycraft.configured.gui

import gg.essential.elementa.UIComponent
import xyz.unifycraft.configured.options.Option

abstract class ConfigOptionComponent : UIComponent() {
    abstract val option: Option
}
