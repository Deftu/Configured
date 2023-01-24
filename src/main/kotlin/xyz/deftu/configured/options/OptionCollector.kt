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

package xyz.deftu.configured.options

import xyz.deftu.configured.Config
import xyz.deftu.configured.Configurable
import xyz.deftu.configured.options.processor.AnnotationOptionProcessor
import xyz.deftu.configured.options.processor.ConfigurableInternalOptionProcessor

class OptionCollector {
    private val options = mutableListOf<Option>()
    private val processors = mutableListOf(
        AnnotationOptionProcessor,
        ConfigurableInternalOptionProcessor
    )

    var initialized = false
        private set

    fun collect(configurable: Configurable) {
        if (configurable is Config) initialized = true
        options.addAll(processors.flatMap {
            it.process(configurable)
        })
    }

    fun get() = options.toList()
}
