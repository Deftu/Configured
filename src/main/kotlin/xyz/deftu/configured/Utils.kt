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

package xyz.deftu.configured

import xyz.deftu.configured.platform.MenuScreen

internal object Utils {
    private var awaitingOpen: MenuScreen? = null

    fun initialize() {
        //#if MC<=11202
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(this)
        //#elseif MC>=11800 && FABRIC==1
        //$$ net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.END_CLIENT_TICK.register(::tick)
        //#elseif MC<=11802 && FORGE==1
        //$$ TODO("Implement Forge command registration")
        //#elseif MC>=11900 && FORGE==1
        //$$ TODO("Implement Forge command registration")
        //#endif
    }

    fun openScreen(screen: MenuScreen) {
        if (awaitingOpen != null)
            return

        awaitingOpen = screen
    }

    fun update() {
        if (awaitingOpen != null) {
            //#if MC<=11202
            net.minecraft.client.Minecraft.getMinecraft().displayGuiScreen(awaitingOpen)
            //#else
            //$$ net.minecraft.client.MinecraftClient.getInstance().setScreen(awaitingOpen)
            //#endif
            awaitingOpen = null
        }
    }

    //#if MC<=11202
    @net.minecraftforge.fml.common.eventhandler.SubscribeEvent
    fun onTick(event: net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent) {
        if (event.phase == net.minecraftforge.fml.common.gameevent.TickEvent.Phase.END)
            update()
    }
    //#elseif MC>=11800 && FABRIC==1
    //$$ private fun tick(client: net.minecraft.client.MinecraftClient) {
    //$$     update()
    //$$ }
    //#elseif MC<=11802 && FORGE==1
    //$$ TODO("Implement Forge command registration")
    //#elseif MC>=11900 && FORGE==1
    //$$ TODO("Implement Forge command registration")
    //#endif
}
