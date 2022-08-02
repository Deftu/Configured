package xyz.unifycraft.configured.gui.original

import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.constraints.ChildBasedSizeConstraint
import gg.essential.elementa.constraints.animation.Animations
import gg.essential.elementa.dsl.*
import gg.essential.elementa.effects.OutlineEffect
import xyz.unifycraft.configured.gui.ConfigOptionComponent
import xyz.unifycraft.configured.gui.ConfiguredPalette
import xyz.unifycraft.configured.options.Option

/**
 * Internal component used
 * for the Configured GUI.
 */
class DefaultSwitchComponent(
    override val option: Option
) : ConfigOptionComponent() {
    var toggle: Boolean
        get() = option.invoke().toString().toBoolean()
        set(value) = option.set(value)

    init {
        constrain {
            width = ChildBasedSizeConstraint()
            height = ChildBasedSizeConstraint()
        }

        val background by UIBlock(ConfiguredPalette.background).constrain {
            width = 37.5.pixels
            height = 18.75.pixels
        } effect OutlineEffect(
            color = fetchToggleColor(),
            width = 2f
        ) childOf this
        val thumb by UIBlock(fetchToggleColor()).constrain {
            x = 0.pixels(alignOpposite = toggle)
            width = 50.percent
            height = 100.percent
        } childOf background

        onMouseClick {
            toggle = !toggle
            thumb.animate {
                (background.effects[0] as OutlineEffect)::color.animate(Animations.OUT_EXP, 0.5f, fetchToggleColor())
                setColorAnimation(Animations.OUT_EXP, 0.5f, fetchToggleColor().toConstraint())
                setXAnimation(Animations.OUT_EXP, 0.5f, 0.pixels(alignOpposite = toggle))
            }
        }
    }

    private fun fetchToggleColor() =
        if (toggle) ConfiguredPalette.main else ConfiguredPalette.mainVariant
}
