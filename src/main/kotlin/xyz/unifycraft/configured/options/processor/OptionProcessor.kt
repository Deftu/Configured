package xyz.unifycraft.configured.options.processor

import xyz.unifycraft.configured.Configurable
import xyz.unifycraft.configured.options.Option

/**
 * A base option processor that can be
 * extended to add new ways to get options
 * from [Configurable] classes.
 */
interface OptionProcessor {
    fun process(configurable: Configurable): List<Option>
}
