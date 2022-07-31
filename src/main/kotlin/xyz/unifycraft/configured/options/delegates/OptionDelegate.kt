package xyz.unifycraft.configured.options.delegates

import xyz.unifycraft.configured.Config
import xyz.unifycraft.configured.Configurable
import xyz.unifycraft.configured.options.Option
import xyz.unifycraft.configured.options.OptionType
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class OptionDelegate<T : Any> internal constructor(
    val default: T
) : ReadWriteProperty<Configurable, T> {
    abstract val name: String
    open var localizedName: String = ""
    open var description: String = ""
    open var category: String = Option.DEFAULT_CATEGORY
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

    fun dependency(name: String) {
        val list = dependencies.toMutableList()
        list.add(name)
        dependencies = list
    }
}
