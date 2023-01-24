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

package xyz.deftu.configured.options.processor

import xyz.deftu.configured.Configurable
import xyz.deftu.configured.options.Option
import xyz.deftu.configured.options.OptionType
import xyz.deftu.configured.options.annnotations.*
import xyz.deftu.configured.utils.setAccessibility

object AnnotationOptionProcessor : OptionProcessor {
    override fun process(configurable: Configurable): List<Option> {
        val clz = configurable::class.java
        val fields = clz.declaredFields
        val methods = clz.declaredMethods
        val options = mutableListOf<Option>()

        // Search for options in all the fields in the class.
        fields@ for (field in fields) {
            field.setAccessibility(true)
            annotations@ for (annotation in field.declaredAnnotations) {
                val data = process(annotation) ?: continue@annotations
                val category = (field.declaredAnnotations.find {
                    it is OptionCategory
                } as? OptionCategory)
                val categoryValue = category?.value ?: Option.DEFAULT_CATEGORY
                val categoryLocalized = category?.localized ?: ""
                options.add(Option(data.name, data.localizedName, data.description, categoryValue, categoryLocalized, try {
                    field.get(configurable)
                } catch (e: Exception) {
                    e.printStackTrace()
                    continue@fields
                }, data.hidden, data.tags.toList(), data.type, data.attributes, {
                    field.get(configurable)
                }, {
                    field.set(configurable, it)
                }))
            }
        }

        // Search for options in all the methods in the class.
        methods@ for (method in methods) {
            method.setAccessibility(true)
            annotations@ for (annotation in method.declaredAnnotations) {
                val data = process(annotation) ?: continue@annotations
                val category = (method.declaredAnnotations.find {
                    it is OptionCategory
                } as? OptionCategory)
                val categoryValue = category?.value ?: Option.DEFAULT_CATEGORY
                val categoryLocalized = category?.localized ?: ""
                options.add(Option(data.name, data.localizedName, data.description, categoryValue, categoryLocalized, {  }, data.hidden, data.tags.toList(), data.type, data.attributes, {
                    method.invoke(configurable)
                    Runnable {  }
                }, {
                    throw UnsupportedOperationException("Cannot set a button option!")
                }))
            }
        }
        return options
    }

    private fun process(annotation: Annotation) = when (annotation) {
        is SwitchOption -> OptionData(annotation.name, annotation.localizedName, annotation.description, annotation.hidden, annotation.tags, OptionType.SWITCH)
        is TextOption -> OptionData(annotation.name, annotation.localizedName, annotation.description, annotation.hidden, annotation.tags, OptionType.TEXT, mapOf("protected" to annotation.protectedText, "limit" to annotation.limit))
        is PercentageOption -> OptionData(annotation.name, annotation.localizedName, annotation.description, annotation.hidden, annotation.tags, OptionType.PERCENTAGE, mapOf("min" to annotation.min, "max" to annotation.max))
        is IntegerOption -> OptionData(annotation.name, annotation.localizedName, annotation.description, annotation.hidden, annotation.tags, OptionType.INTEGER, mapOf("min" to annotation.min, "max" to annotation.max))
        is ColorOption -> OptionData(annotation.name, annotation.localizedName, annotation.description, annotation.hidden, annotation.tags, OptionType.COLOR, mapOf("alpha" to annotation.alpha))
        is FileOption -> OptionData(annotation.name, annotation.localizedName, annotation.description, annotation.hidden, annotation.tags, OptionType.FILE)
        is DropdownOption -> OptionData(annotation.name, annotation.localizedName, annotation.description, annotation.hidden, annotation.tags, OptionType.DROPDOWN, mapOf("options" to annotation.options.toList()))
        is ButtonOption -> OptionData(annotation.name, annotation.localizedName, annotation.description, annotation.hidden, annotation.tags, OptionType.BUTTON, mapOf("text" to annotation.text))
        else -> null
    }
}

private data class OptionData(
    val name: String,
    val localizedName: String,
    val description: String,
    val hidden: Boolean,
    val tags: Array<String>,
    val type: OptionType,
    val attributes: Map<String, Any> = mapOf()
)
