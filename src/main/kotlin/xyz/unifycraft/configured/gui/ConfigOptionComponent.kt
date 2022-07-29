package xyz.unifycraft.configured.gui

import gg.essential.elementa.UIComponent
import gg.essential.elementa.components.UIContainer
import xyz.unifycraft.configured.options.Option

abstract class ConfigOptionComponent : UIContainer() {
    abstract val option: Option
}
