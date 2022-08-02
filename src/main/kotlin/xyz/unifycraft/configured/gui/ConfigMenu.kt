package xyz.unifycraft.configured.gui

import gg.essential.elementa.ElementaVersion
import gg.essential.elementa.WindowScreen
import xyz.unifycraft.configured.options.Option

/**
 * Base class for creating menus
 * which interact with configs created
 * using Configured. Use this to create
 * custom menus for your configs!
 */
abstract class ConfigMenu(
    enableRepeatKeys: Boolean = false,
    drawDefaultBackground: Boolean = false,
    newGuiScale: Int = -1
) : WindowScreen(
    version = ElementaVersion.V2,
    enableRepeatKeys = enableRepeatKeys,
    drawDefaultBackground = drawDefaultBackground,
    restoreCurrentGuiOnClose = true,
    newGuiScale = newGuiScale
) {
    abstract fun createOptionComponent(option: Option): ConfigOptionComponent
}
