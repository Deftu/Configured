package xyz.unifycraft.configured

import xyz.unifycraft.configured.options.Option

interface Configurable {
    val options: MutableList<Option>
}
