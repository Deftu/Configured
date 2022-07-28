package com.test;

import xyz.unifycraft.configured.Config;
import xyz.unifycraft.configured.options.annnotations.*;

import java.awt.*;
import java.io.File;

public class TestConfigAnnotation extends Config {
    @CheckboxOption(name = "Test Checkbox", description = "This is a checkbox")
    public boolean testCheckbox = true;

    @SwitchOption(name = "Test Switch", description = "This is a switch")
    public boolean testSwitch = false;

    @TextOption(name = "Test Text", description = "This is a text")
    public String testText = "This is a text";

    @ParagraphOption(name = "Test Paragraph", description = "This is a paragraph")
    public String testParagraph = "This is a paragraph";

    @PercentageOption(name = "Test Percentage", description = "This is a percentage", min = 0, max = 100)
    public float testPercentage = 50;

    @IntegerOption(name = "Test Integer", description = "This is an integer", min = 0, max = 10)
    public int testInteger = 5;

    @ColorOption(name = "Test Color", description = "This is a color")
    public Color testColor = Color.RED;

    @FileOption(name = "Test File", description = "This is a file")
    public File testFile = new File("./config");

    @ButtonOption(name = "Test Button", description = "This is a button", text = "Click me!")
    public void button() {
        System.out.println("Button clicked!");
    }

    public TestConfigAnnotation(File configDir) {
        super(new File(configDir, "TestMod Annotation"), "Test Mod");
    }
}
