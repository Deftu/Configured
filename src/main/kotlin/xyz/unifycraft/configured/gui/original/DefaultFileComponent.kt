package xyz.unifycraft.configured.gui.original

import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.components.UIText
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.ChildBasedSizeConstraint
import gg.essential.elementa.constraints.SiblingConstraint
import gg.essential.elementa.dsl.*
import gg.essential.elementa.effects.OutlineEffect
import xyz.unifycraft.configured.gui.ConfigOptionComponent
import xyz.unifycraft.configured.gui.ConfiguredPalette
import xyz.unifycraft.configured.gui.components.InputBoxComponent
import xyz.unifycraft.configured.options.Option
import java.io.File

//#if MC<=11605
import javax.swing.JFileChooser
//#else
//$$ import org.lwjgl.util.tinyfd.TinyFileDialogs
//#endif

/**
 * Internal component used
 * for the Configured GUI.
 */
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

        val button by UIBlock(ConfiguredPalette.background).constrain {
            x = SiblingConstraint(7.5f)
            width = 25.pixels
            height = 25.pixels
        } effect OutlineEffect(
            color = ConfiguredPalette.main,
            width = 2f
        ) childOf this
        UIText("...").constrain {
            x = CenterConstraint()
            y = CenterConstraint()
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
