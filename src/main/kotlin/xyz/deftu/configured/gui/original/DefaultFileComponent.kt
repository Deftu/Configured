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

package xyz.deftu.configured.gui.original

import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.components.UIText
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.ChildBasedSizeConstraint
import gg.essential.elementa.constraints.SiblingConstraint
import gg.essential.elementa.dsl.*
import gg.essential.elementa.effects.OutlineEffect
import xyz.deftu.configured.config.ConfiguredPalette
import xyz.deftu.configured.gui.ConfigOptionComponent
import xyz.deftu.configured.gui.components.InputBoxComponent
import xyz.deftu.configured.options.Option
import java.io.File

//#if MC<=11605
import javax.swing.JFileChooser
//#else
//$$ import org.lwjgl.util.tinyfd.TinyFileDialogs
//#endif

class DefaultFileComponent(
    override val option: Option
) : ConfigOptionComponent() {
    var file: File
        get() = option.invoke() as File
        set(value) = option.set(value)

    init {
        constrain {
            width = ChildBasedSizeConstraint(7.5f)
            height = 25.pixels
        }

        val input by InputBoxComponent(file.absolutePath).constrain {
            width = 175.pixels
            height = 25.pixels
        } childOf this
        input.onValueChanged {
            try {
                file = File(it)
            } catch (_: Exception) { }
        }

        val button by UIBlock(ConfiguredPalette.getBackground2()).constrain {
            x = SiblingConstraint(7.5f)
            width = 25.pixels
            height = 25.pixels
        } effect OutlineEffect(
            color = ConfiguredPalette.getPrimary(),
            width = 2f
        ) childOf this
        UIText("...").constrain {
            x = CenterConstraint()
            y = CenterConstraint()
            color = ConfiguredPalette.getText().toConstraint()
        } childOf button
        button.onMouseClick {
            //#if MC<=11602
            val fileChooser = JFileChooser()
            fileChooser.currentDirectory = File(System.getProperty("user.home"))
            val result = fileChooser.showOpenDialog(null)
            if (result == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.selectedFile.absoluteFile
                input.textInput.setText(file.absolutePath)
            }
            //#else
            //$$ val result = TinyFileDialogs.tinyfd_openFileDialog("Select file", System.getProperty("user.home"), null, null, false)
            //$$ if (result != null) {
            //$$     file = File(result)
            //$$     input.textInput.setText(file.absolutePath)
            //$$ }
            //#endif
        }
    }
}
