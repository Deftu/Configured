package com.test

import xyz.unifycraft.configured.Config
import xyz.unifycraft.configured.options.delegates.*
import java.awt.Color
import java.io.File

class TestConfigDelegate(
    configDir: File
) : Config(
    directory = File(configDir, "TestMod Delegate"),
    title = "Test Mod"
) {
    var testSwitch by switch(false) {
        name = "Test Switch"
        description = "This is a switch"
    }

    var testText by text("This is a text") {
        name = "Test Text"
        description = "This is a text"
    }

    var testPercentage by percentage(50f) {
        name = "Test Percentage"
        description = "This is a percentage"
        min = 0
        max = 100
    }

    var testInteger by integer(5) {
        name = "Test Integer"
        description = "This is an integer"
        min = 0
        max = 10
    }

    var testColor by color(Color.RED) {
        name = "Test Color"
        description = "This is a color"
    }

    var testFile by file(File("./config")) {
        name = "Test File"
        description = "This is a file"
    }

    init {
        button({
            println("Hello, World!")
        }) {
            name = "Test Button"
            description = "This is a button"
            text = "Click me!"
        }
    }
}
