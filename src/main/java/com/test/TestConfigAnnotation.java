package com.test;

//#if MC==10809
import xyz.unifycraft.configured.Config;
import xyz.unifycraft.configured.options.annnotations.*;

import java.awt.*;
import java.io.File;

public class TestConfigAnnotation extends Config {
    @CheckboxOption(name = "Checkbox", description = "This is a checkbox")
    public boolean testCheckbox = true;

    @SwitchOption(name = "Switch", description = "This is a switch")
    public boolean testSwitch = false;

    @TextOption(name = "Text", description = "This is a text")
    public String testText = "This is a text";

    @ParagraphOption(name = "Paragraph", description = "This is a paragraph")
    public String testParagraph = "This is a paragraph";

    @PercentageOption(name = "Percentage", description = "This is a percentage", min = 0, max = 100)
    public float testPercentage = 50;

    @IntegerOption(name = "Integer", description = "This is an integer", min = 0, max = 10)
    public int testInteger = 5;

    @ColorOption(name = "Color", description = "This is a color")
    public Color testColor = Color.RED;

    @FileOption(name = "File", description = "This is a file")
    public File testFile = new File("./config");

    @ButtonOption(name = "Button", description = "This is a button", text = "Click me!")
    public void button() {
        System.out.println("Button clicked!");
    }

    public TestConfigAnnotation(File configDir) {
        super(new File(configDir, "TestMod Annotation"), "Test Mod");
    }
}
//#endif
