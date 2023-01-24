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

import xyz.deftu.configured.config.ConfiguredConfig

object Configured {
    const val NAME = "@MOD_NAME@"
    const val VERSION = "@MOD_VERSION@"
    const val ID = "@MOD_ID@"

    const val GIT_BRANCH = "@GIT_BRANCH@"
    const val GIT_COMMIT = "@GIT_COMMIT@"

    private var initialized = false

    @JvmStatic
    fun initialize() {
        if (initialized)
            return

        Utils.initialize()
        //#if MC<=11202
        net.minecraftforge.client.ClientCommandHandler.instance.registerCommand(object : net.minecraft.command.CommandBase() {
            override fun getCommandName() = ID
            override fun getCommandUsage(sender: net.minecraft.command.ICommandSender) = ""

            override fun processCommand(sender: net.minecraft.command.ICommandSender, args: Array<out String>) {
                ConfiguredConfig.menu().let(Utils::openScreen)
            }

            override fun canCommandSenderUseCommand(sender: net.minecraft.command.ICommandSender) = true

        })
        //#elseif MC<=11802 && FABRIC==1
        //$$ net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.DISPATCHER.register(net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.literal(ID).executes {
        //$$    ConfiguredConfig.menu().let(Utils::openScreen);
        //$$    1
        //$$ })
        //#elseif MC>=11900 && FABRIC==1
        //$$ net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
        //$$     dispatcher.register(net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal(ID).executes {
        //$$         ConfiguredConfig.menu().let(Utils::openScreen);
        //$$         1
        //$$     })
        //$$ }
        //#elseif MC<=11802 && FORGE==1
        //$$ TODO("Implement Forge command registration")
        //#elseif MC>=11900 && FORGE==1
        //$$ TODO("Implement Forge command registration")
        //#endif

        initialized = true
    }
}
