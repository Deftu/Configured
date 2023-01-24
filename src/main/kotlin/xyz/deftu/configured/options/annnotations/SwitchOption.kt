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

package xyz.deftu.configured.options.annnotations

import xyz.deftu.configured.options.Option

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.FIELD)
annotation class SwitchOption(
    /**
     * The serializable name of the option,
     * and the default if the localized
     * name is blank.
     */
    val name: String,
    /**
     * The localized name of the option.
     */
    val localizedName: String = "",
    /**
     * The description of the option.
     */
    val description: String = "",
    /**
     * Whether the option is hidden
     * from the user.
     */
    val hidden: Boolean = Option.DEFAULT_HIDDEN,
    /**
     * The tags that can be used to
     * search for the option in the
     * default UI.
     */
    val tags: Array<String> = [],
)
