package xyz.unifycraft.configured.gui

import gg.essential.elementa.components.UIContainer
import xyz.unifycraft.configured.options.Option

/**
 * Base class for components representing
 * config options. This should be used alongside
 * [ConfigMenu] to create custom menus for
 * configs.
 */
abstract class ConfigOptionComponent : UIContainer() {
    abstract val option: Option
    /**
     * Should be called when the category
     * is changed or the config requires
     * floating components added by
     * options to be closed.
     */
    open fun closePopups() {}
}
