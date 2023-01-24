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

package xyz.deftu.configured.gui.effects

import gg.essential.elementa.effects.Effect
import gg.essential.universal.UMatrixStack

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
