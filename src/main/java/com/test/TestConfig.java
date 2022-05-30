package com.test;

//#if MC==10809
import xyz.unifycraft.configured.Config;
import xyz.unifycraft.configured.options.annnotations.ColorOption;
import xyz.unifycraft.configured.options.annnotations.SwitchOption;

import java.awt.*;
import java.io.File;

public class TestConfig extends Config {
    @SwitchOption(name = "Test Switch", description = "Test Switch")
    public boolean testSwitch = false;

    @ColorOption(name = "Test Color", description = "Test Color", alpha = true)
    public Color testColor = Color.RED;

    public TestConfig(File configDir) {
        super(new File(configDir, "TestMod"), "Test Mod");
    }
}
//#endif
