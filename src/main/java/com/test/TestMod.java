package com.test;

import com.google.common.base.Stopwatch;
import gg.essential.universal.UScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;

//#if FORGE
//#if MC<=11202
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
//#else
//$$ import net.minecraftforge.eventbus.api.SubscribeEvent;
//$$ import net.minecraftforge.event.TickEvent;
//$$ import net.minecraftforge.common.MinecraftForge;
//$$ import net.minecraftforge.fml.common.Mod;
//$$
//#endif
//#else
//#if FABRIC
//$$ import net.fabricmc.api.ClientModInitializer;
//$$ import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
//#endif
//#endif

//#if FABRIC
//$$ public class TestMod implements ClientModInitializer {
//#else
//#if MC<=11202
@Mod(modid = "testmod")
//#else
//$$ @Mod(value = "testmod")
//#endif
public class TestMod {
//#endif

    private static Logger logger = LogManager.getLogger("TestMod");

    private TestConfigAnnotation configAnnotation;
    private TestConfigDelegate configDelegate;
    private TestConfigDsl configDsl;

    //#if FABRIC
    //$$ public void onInitializeClient() {
    //$$     initialize();
    //$$     ClientTickEvents.START_CLIENT_TICK.register(event -> tick());
    //$$ }
    //#else
    //#if MC<=11202
    @EventHandler
    public void fmlInitialize(FMLInitializationEvent event) {
        initialize();
        MinecraftForge.EVENT_BUS.register(this);
    }
    //#else
    //$$ {
    //$$     initialize();
    //$$     MinecraftForge.EVENT_BUS.register(this);
    //$$ }
    //#endif

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {
        tick();
    }
    //#endif

    private void initialize() {
        File configDir = new File("config");
        Stopwatch stopwatch = Stopwatch.createStarted();
        logger.info("Loading TestMod");
        logger.info(">>>>>>>>>>");

        configAnnotation = new com.test.TestConfigAnnotation(configDir);
        configDelegate = new com.test.TestConfigDelegate(configDir);
        configDsl = new com.test.TestConfigDsl(configDir);

        logger.info("-----");
        logger.info("Performing tasks on annotation-based config...");
        configAnnotation.initialize();
        logger.info("Switch: {}", configAnnotation.testSwitch);
        logger.info("Text: {}", configAnnotation.testText);
        logger.info("Percentage: {}", configAnnotation.testPercentage);
        logger.info("Integer: {}", configAnnotation.testInteger);
        logger.info("Color: {}", configAnnotation.testColor);
        logger.info("File: {}", configAnnotation.testFile);
        logger.info("Switch 2: {}", configAnnotation.testSwitch2);
        logger.info("Color 2: {}", configAnnotation.testColor2);

        logger.info("-----");
        logger.info("Performing tasks on delegate-based config...");
        configDelegate.initialize();
        logger.info("Switch: {}", configDelegate.getTestSwitch());
        logger.info("Text: {}", configDelegate.getTestText());
        logger.info("Percentage: {}", configDelegate.getTestPercentage());
        logger.info("Integer: {}", configDelegate.getTestInteger());
        logger.info("Color: {}", configDelegate.getTestColor());
        logger.info("File: {}", configDelegate.getTestFile());

        logger.info("-----");
        logger.info("Performing tasks on dsl-based config...");
        configDsl.initialize();
        logger.info("Switch: {}", configDsl.getTestSwitch());
        logger.info("Text: {}", configDsl.getTestText());
        logger.info("Percentage: {}", configDsl.getTestPercentage());
        logger.info("Integer: {}", configDsl.getTestInteger());
        logger.info("Color: {}", configDsl.getTestColor());
        logger.info("File: {}", configDsl.getTestFile());

        long elapsed = stopwatch.elapsed(java.util.concurrent.TimeUnit.MILLISECONDS);
        logger.info("TestMod loaded in {}ms. That's {}ms per config!", elapsed, elapsed / 3);
    }

    private void tick() {
        if (!(UScreen.getCurrentScreen() instanceof UScreen)) {
            UScreen.displayScreen(configAnnotation.menu());
        }
    }
}
