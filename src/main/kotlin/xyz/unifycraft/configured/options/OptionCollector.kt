package xyz.unifycraft.configured.options

import xyz.unifycraft.configured.Config
import xyz.unifycraft.configured.Configurable
import xyz.unifycraft.configured.options.processor.AnnotationOptionProcessor
import xyz.unifycraft.configured.options.processor.DelegateOptionProcessor
import xyz.unifycraft.configured.options.processor.DslOptionProcessor

class OptionCollector {
    private val options = mutableListOf<Option>()
    private val processors = mutableListOf(
        AnnotationOptionProcessor,
        DelegateOptionProcessor,
        DslOptionProcessor
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
