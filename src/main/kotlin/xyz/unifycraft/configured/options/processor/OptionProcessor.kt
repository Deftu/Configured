package xyz.unifycraft.configured.options.processor

import xyz.unifycraft.configured.Configurable
import xyz.unifycraft.configured.options.Option

interface OptionProcessor {
    fun process(configurable: Configurable): List<Option>
}
