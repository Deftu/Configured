package xyz.unifycraft.configured.options.processor

import xyz.unifycraft.configured.Configurable

/**
 * An option processor which grabs
 * the options list from the [Configurable]
 * itself instead of doing it's own logic.
 */
object ConfigurableInternalOptionProcessor : OptionProcessor {
    override fun process(configurable: Configurable) =
        configurable.options
}
