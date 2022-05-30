package com.test

import xyz.unifycraft.configured.Config
import xyz.unifycraft.configured.options.delegates.color
import xyz.unifycraft.configured.options.delegates.switch
import java.awt.Color
import java.io.File

class TestConfigKotlin(
    configDir: File
) : Config(
    directory = File(configDir, "TestMod Kotlin"),
    title = "Test Mod"
) {
    var testSwitch by switch(false) {
        name = "Test Switch"
        description = "Test Switch"
    }

    var testColor by color(Color.RED) {
        name = "Test Color"
        description = "Test Color"
    }
}
