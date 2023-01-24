/*
 * This file is a part of the Configured library
 * Copyright (C) 2023 Deftu (https://deftu.xyz)
 *
 * DO NOT remove or alter copyright notices, or remove this file header.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.test;

import com.google.common.base.Stopwatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.File;

import xyz.deftu.configured.Configured;

//#if FORGE
//#if MC<=11202
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
//#else
//$$ import net.minecraftforge.fml.common.Mod;
//$$
//#endif
//#else
//#if FABRIC
//$$ import net.fabricmc.api.ClientModInitializer;
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
    //$$ }
    //#else
    //#if MC<=11202
    @EventHandler
    public void fmlInitialize(FMLInitializationEvent event) {
        initialize();
    }
    //#else
    //$$ {
    //$$     initialize();
    //$$ }
    //#endif
    //#endif

    private void initialize() {
        File configDir = new File("config");
        Stopwatch stopwatch = Stopwatch.createStarted();

        Configured.initialize();

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
        logger.info("Dropdown: {}", configAnnotation.testDropdown);
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
}
