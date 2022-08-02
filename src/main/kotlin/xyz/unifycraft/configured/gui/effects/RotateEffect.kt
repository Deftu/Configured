package xyz.unifycraft.configured.gui.effects

import gg.essential.elementa.effects.Effect
import gg.essential.universal.UMatrixStack

/**
 * Rotates the component by the specified angle.
 */
class RotateEffect(
    var angle: Float
) : Effect() {
    override fun beforeDraw(matrixStack: UMatrixStack) {
        matrixStack.push()
        val x = (boundComponent.getLeft() + (boundComponent.getRight() - boundComponent.getLeft()) / 2).toDouble()
        val y = (boundComponent.getTop() + (boundComponent.getBottom() - boundComponent.getTop()) / 2).toDouble()
        matrixStack.translate(x, y, 0.0)
        matrixStack.rotate(angle, 0f, 0f, 1f)
        matrixStack.translate(-x, -y, 0.0)
    }

    override fun afterDraw(matrixStack: UMatrixStack) {
        matrixStack.pop()
    }
}
