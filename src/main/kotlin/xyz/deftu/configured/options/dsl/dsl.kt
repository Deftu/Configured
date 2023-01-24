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

package xyz.deftu.configured.options.dsl

import xyz.deftu.configured.Configurable
import xyz.deftu.configured.options.Option
import xyz.deftu.configured.options.OptionType
import xyz.deftu.configured.utils.setAccessibility
import java.awt.Color
import java.io.File
import java.lang.reflect.Field
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaField

// Switch

fun Configurable.switch(field: Field, default: Boolean, block: SwitchOptionScope.() -> Unit) {
    val scope = SwitchOptionScope(default, field)
    block(scope)
    field.setAccessibility(true)
    field.set(this, default)

    options.add(Option(scope.name, scope.localizedName, scope.description, scope.category, scope.localizedCategory, scope.default, scope.hidden, scope.tags, OptionType.SWITCH, mapOf(), {
        scope.field.setAccessibility(true)
        scope.field.get(this) ?: scope.default
    }, {
        scope.field.setAccessibility(true)
        scope.field.set(this, it)
    }))
}

fun Configurable.switch(property: KProperty<*>, default: Boolean, block: SwitchOptionScope.() -> Unit) =
    switch(property.toJavaField(), default, block)

// Text

fun Configurable.text(field: Field, default: String, block: TextOptionScope.() -> Unit) {
    val scope = TextOptionScope(default, field)
    block(scope)
    field.setAccessibility(true)
    field.set(this, default)
    options.add(Option(scope.name, scope.localizedName, scope.description, scope.category, scope.localizedCategory, scope.default, scope.hidden, scope.tags, OptionType.TEXT, mapOf(
        "protected" to scope.protectedText,
        "limit" to scope.limit
    ), {
        scope.field.setAccessibility(true)
        scope.field.get(this) ?: scope.default
    }, {
        scope.field.setAccessibility(true)
        scope.field.set(this, it)
    }))
}

fun Configurable.text(property: KProperty<*>, default: String, block: TextOptionScope.() -> Unit) =
    text(property.toJavaField(), default, block)

// Dropdown

fun Configurable.dropdown(field: Field, default: Int, block: DropdownOptionScrope.() -> Unit) {
val scope = DropdownOptionScrope(default, field)
    block(scope)
    field.setAccessibility(true)
    field.set(this, default)
    options.add(Option(scope.name, scope.localizedName, scope.description, scope.category, scope.localizedCategory, scope.default, scope.hidden, scope.tags, OptionType.DROPDOWN, mapOf(
        "options" to scope.options
    ), {
        scope.field.setAccessibility(true)
        scope.field.get(this) ?: scope.default
    }, {
        scope.field.setAccessibility(true)
        scope.field.set(this, it)
    }))
}

fun Configurable.dropdown(property: KProperty<*>, default: Int, block: DropdownOptionScrope.() -> Unit) =
    dropdown(property.toJavaField(), default, block)

// Percentage

fun Configurable.percentage(field: Field, default: Float, block: PercentageOptionScope.() -> Unit) {
    val scope = PercentageOptionScope(default, field)
    block(scope)
    field.setAccessibility(true)
    field.set(this, default)
    options.add(Option(scope.name, scope.localizedName, scope.description, scope.category, scope.localizedCategory, scope.default, scope.hidden, scope.tags, OptionType.PERCENTAGE, mapOf(
        "min" to scope.min,
        "max" to scope.max
    ), {
        scope.field.setAccessibility(true)
        scope.field.get(this) ?: scope.default
    }, {
        scope.field.setAccessibility(true)
        scope.field.set(this, it)
    }))
}

fun Configurable.percentage(property: KProperty<*>, default: Float, block: PercentageOptionScope.() -> Unit) =
    percentage(property.toJavaField(), default, block)

// Integer

fun Configurable.integer(field: Field, default: Int, block: IntegerOptionScope.() -> Unit) {
    val scope = IntegerOptionScope(default, field)
    block(scope)
    field.setAccessibility(true)
    field.set(this, default)
    options.add(Option(scope.name, scope.localizedName, scope.description, scope.category, scope.localizedCategory, scope.default, scope.hidden, scope.tags, OptionType.INTEGER, mapOf(
        "min" to scope.min,
        "max" to scope.max
    ), {
        scope.field.setAccessibility(true)
        scope.field.get(this) ?: scope.default
    }, {
        scope.field.setAccessibility(true)
        scope.field.set(this, it)
    }))
}

fun Configurable.integer(property: KProperty<*>, default: Int, block: IntegerOptionScope.() -> Unit) =
    integer(property.toJavaField(), default, block)

// Color

fun Configurable.color(field: Field, default: Color, block: ColorOptionScope.() -> Unit) {
    val scope = ColorOptionScope(default, field)
    block(scope)
    field.setAccessibility(true)
    field.set(this, default)
    options.add(Option(scope.name, scope.localizedName, scope.description, scope.category, scope.localizedCategory, scope.default, scope.hidden, scope.tags, OptionType.COLOR, mapOf(
        "alpha" to scope.alpha
    ), {
        scope.field.setAccessibility(true)
        scope.field.get(this) ?: scope.default
    }, {
        scope.field.setAccessibility(true)
        scope.field.set(this, it)
    }))
}

fun Configurable.color(property: KProperty<*>, default: Color, block: ColorOptionScope.() -> Unit) =
    color(property.toJavaField(), default, block)

// File

fun Configurable.file(field: Field, default: File, block: FileOptionScope.() -> Unit) {
    val scope = FileOptionScope(default, field)
    block(scope)
    field.setAccessibility(true)
    field.set(this, default)
    options.add(Option(scope.name, scope.localizedName, scope.description, scope.category, scope.localizedCategory, scope.default, scope.hidden, scope.tags, OptionType.FILE, mapOf(), {
        scope.field.setAccessibility(true)
        scope.field.get(this) ?: scope.default
    }, {
        scope.field.setAccessibility(true)
        scope.field.set(this, it)
    }))
}

fun Configurable.file(property: KProperty<*>, default: File, block: FileOptionScope.() -> Unit) =
    file(property.toJavaField(), default, block)

// Make sure that the KProperty uses JVM fields
fun KProperty<*>.toJavaField() =
    this.javaField ?: throw IllegalArgumentException("KProperty must be a JVM field.")
