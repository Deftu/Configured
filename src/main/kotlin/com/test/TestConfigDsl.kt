package com.test

import xyz.unifycraft.configured.Config
import xyz.unifycraft.configured.options.dsl.*
import java.awt.Color
import java.io.File
import kotlin.properties.Delegates

class TestConfigDsl(
    configDir: File
) : Config(
    directory = File(configDir, "TestMod DSL"),
    title = "Test Mod"
) {
    var testCheckbox = false
    var testSwitch = false
    var testText = ""
    var testParagraph = ""
    var testPercentage = 0f
    var testInteger = 0
    lateinit var testColor: Color
    lateinit var testFile: File

    init {
        checkbox(::testCheckbox, true) {
            name = "Test Checkbox"
            description = "This is a checkbox"
        }

        switch(::testSwitch, false) {
            name = "Test Switch"
            description = "This is a switch"
        }

        text(::testText, "This is a text") {
            name = "Test Text"
            description = "This is a text field"
        }

        paragraph(::testParagraph, "This is a paragraph") {
            name = "Test Paragraph"
            description = "This is a paragraph"
        }

        percentage(::testPercentage, 50f) {
            name = "Test Percentage"
            description = "This is a percentage"
            min = 0f
            max = 100f
        }

        integer(::testInteger, 5) {
            name = "Test Integer"
            description = "This is an integer"
            min = 0
            max = 10
        }

        color(::testColor, Color.RED) {
            name = "Test Color"
            description = "This is a color"
        }

        file(::testFile, File("./config")) {
            name = "Test File"
            description = "This is a file"
        }
    }
}
