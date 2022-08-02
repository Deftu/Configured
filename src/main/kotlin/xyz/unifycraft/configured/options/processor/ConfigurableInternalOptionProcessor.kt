package xyz.unifycraft.configured.options.processor

import xyz.unifycraft.configured.Configurable

object ConfigurableInternalOptionProcessor : OptionProcessor {
    override fun process(configurable: Configurable) =
        configurable.options
}
