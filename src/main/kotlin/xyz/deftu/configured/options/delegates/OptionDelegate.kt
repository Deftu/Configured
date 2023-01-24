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
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class OptionDelegate<T : Any> internal constructor(
    val default: T
) : ReadWriteProperty<Configurable, T> {
    abstract val name: String
    open var localizedName: String = ""
    open var description: String = ""
    open var category: String = Option.DEFAULT_CATEGORY
    open var localizedCategory: String = ""
    open var hidden: Boolean = Option.DEFAULT_HIDDEN
    open var tags: List<String> = listOf()
    var dependencies = listOf<String>()
        private set

    internal open var value: T = default

    override fun getValue(thisRef: Configurable, property: KProperty<*>) =
        value
    override fun setValue(thisRef: Configurable, property: KProperty<*>, value: T) {
        this.value = (value as? T) ?: default
    }

    fun tag(tag: String) {
        this.tags = this.tags + tag
    }

    fun tags(vararg tags: String) {
        this.tags = this.tags + tags
    }

    fun dependency(name: String) {
        val list = dependencies.toMutableList()
        list.add(name)
        dependencies = list
    }

    fun dependencies(vararg names: String) {
        val list = dependencies.toMutableList()
        list.addAll(names)
        dependencies = list
    }
}
