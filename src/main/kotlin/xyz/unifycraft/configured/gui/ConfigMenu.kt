package xyz.unifycraft.configured.gui

import gg.essential.elementa.ElementaVersion
import gg.essential.elementa.WindowScreen
import xyz.unifycraft.configured.options.Option

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
