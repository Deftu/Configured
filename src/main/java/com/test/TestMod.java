package com.test;

//#if MC==10809
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "testmod")
public class TestMod {
    private TestConfig config;
    private TestConfigKotlin configKotlin;

    @Mod.EventHandler
    public void preInitialize(FMLPreInitializationEvent event) {
        System.out.println("Init TestMod");

        config = new TestConfig(event.getModConfigurationDirectory());
        config.initialize();
        System.out.println("TestSwitch: " + config.testSwitch);
        System.out.println("TestColor: " + config.testColor);

        System.out.println("---------------------------");

        configKotlin = new TestConfigKotlin(event.getModConfigurationDirectory());
        configKotlin.initialize();
        System.out.println("TestSwitch Kotlin: " + configKotlin.getTestSwitch());

        System.out.println("Init TestMod done");
    }
}
//#endif
