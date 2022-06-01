package com.test;

//#if MC==10809
import com.google.common.base.Stopwatch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = "testmod")
public class TestMod {
    private static Logger logger = LogManager.getLogger("TestMod");

    private TestConfigAnnotation configAnnotation;
    private TestConfigDelegate configDelegate;
    private TestConfigDsl configDsl;

    @Mod.EventHandler
    public void preInitialize(FMLPreInitializationEvent event) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        logger.info("Loading TestMod");
        logger.info(">>>>>>>>>>");

        configAnnotation = new TestConfigAnnotation(event.getModConfigurationDirectory());
        configDelegate = new TestConfigDelegate(event.getModConfigurationDirectory());
        configDsl = new TestConfigDsl(event.getModConfigurationDirectory());

        logger.info("Performing tasks on annotation-based config...");
        logger.info("-----");
        configAnnotation.initialize();
        logger.info("Checkbox: {}", configAnnotation.testCheckbox);
        logger.info("Switch: {}", configAnnotation.testSwitch);
        logger.info("Text: {}", configAnnotation.testText);
        logger.info("Paragraph: {}", configAnnotation.testParagraph);
        logger.info("Percentage: {}", configAnnotation.testPercentage);
        logger.info("Integer: {}", configAnnotation.testInteger);
        logger.info("Color: {}", configAnnotation.testColor);
        logger.info("File: {}", configAnnotation.testFile);

        logger.info("Performing tasks on delegate-based config...");
        logger.info("-----");
        configDelegate.initialize();
        logger.info("Checkbox: {}", configDelegate.getTestCheckbox());
        logger.info("Switch: {}", configDelegate.getTestSwitch());
        logger.info("Text: {}", configDelegate.getTestText());
        logger.info("Paragraph: {}", configDelegate.getTestParagraph());
        logger.info("Percentage: {}", configDelegate.getTestPercentage());
        logger.info("Integer: {}", configDelegate.getTestInteger());
        logger.info("Color: {}", configDelegate.getTestColor());
        logger.info("File: {}", configDelegate.getTestFile());

        logger.info("Performing tasks on dsl-based config...");
        logger.info("-----");
        configDsl.initialize();
        logger.info("Checkbox: {}", configDsl.getTestCheckbox());
        logger.info("Switch: {}", configDsl.getTestSwitch());
        logger.info("Text: {}", configDsl.getTestText());
        logger.info("Paragraph: {}", configDsl.getTestParagraph());
        logger.info("Percentage: {}", configDsl.getTestPercentage());
        logger.info("Integer: {}", configDsl.getTestInteger());
        logger.info("Color: {}", configDsl.getTestColor());
        logger.info("File: {}", configDsl.getTestFile());

        logger.info("TestMod loaded in {}ms", stopwatch.elapsed(java.util.concurrent.TimeUnit.MILLISECONDS));
    }
}
//#endif
