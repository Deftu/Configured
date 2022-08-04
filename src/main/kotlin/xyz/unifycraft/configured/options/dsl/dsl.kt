package xyz.unifycraft.configured.options.dsl

import xyz.unifycraft.configured.Configurable
import xyz.unifycraft.configured.options.Option
import xyz.unifycraft.configured.options.OptionType
import xyz.unifycraft.configured.utils.setAccessibility
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

    options.add(Option(scope.name, scope.localizedName, scope.description, scope.category, scope.default, scope.hidden, scope.tags, OptionType.SWITCH, mapOf(), {
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
    options.add(Option(scope.name, scope.localizedName, scope.description, scope.category, scope.default, scope.hidden, scope.tags, OptionType.TEXT, mapOf(
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
    options.add(Option(scope.name, scope.localizedName, scope.description, scope.category, scope.default, scope.hidden, scope.tags, OptionType.DROPDOWN, mapOf(
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
    options.add(Option(scope.name, scope.localizedName, scope.description, scope.category, scope.default, scope.hidden, scope.tags, OptionType.PERCENTAGE, mapOf(
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
    options.add(Option(scope.name, scope.localizedName, scope.description, scope.category, scope.default, scope.hidden, scope.tags, OptionType.INTEGER, mapOf(
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
    options.add(Option(scope.name, scope.localizedName, scope.description, scope.category, scope.default, scope.hidden, scope.tags, OptionType.COLOR, mapOf(
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
    options.add(Option(scope.name, scope.localizedName, scope.description, scope.category, scope.default, scope.hidden, scope.tags, OptionType.FILE, mapOf(), {
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
