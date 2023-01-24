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

package xyz.deftu.configured.options.delegates

import xyz.deftu.configured.Configurable
import xyz.deftu.configured.options.Option
import xyz.deftu.configured.options.OptionType

class ButtonDelegate(
    runnable: Runnable
) : OptionDelegate<Runnable>(
    runnable
) {
    override lateinit var name: String
    var text = ""
}

fun Configurable.button(runnable: Runnable, block: ButtonDelegate.() -> Unit): ButtonDelegate {
    val delegate = ButtonDelegate(runnable)
    block(delegate)
    options.add(Option(delegate.name, delegate.localizedName, delegate.description, delegate.category, delegate.localizedCategory, delegate.default, delegate.hidden, delegate.tags, OptionType.BUTTON, mapOf(
        "text" to delegate.text
    ), {
        delegate.default.run()
    }, {
        throw UnsupportedOperationException("Cannot set a button option!")
    }))
    return delegate
}
