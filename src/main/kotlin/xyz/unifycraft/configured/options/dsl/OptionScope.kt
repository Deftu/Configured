package xyz.unifycraft.configured.options.dsl

import xyz.unifycraft.configured.options.Option
import java.lang.reflect.Field
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.javaField

abstract class OptionScope<T : Any> internal constructor(
    val default: T,
    val field: Field
) {
    abstract val name: String
    open var description: String = ""
    open var category: String = Option.DEFAULT_CATEGORY
    open var hidden: Boolean = Option.DEFAULT_HIDDEN
    var dependencies = listOf<String>()
        private set

    fun dependency(name: String) {
        val list = dependencies.toMutableList()
        list.add(name)
        dependencies = list
    }
}
