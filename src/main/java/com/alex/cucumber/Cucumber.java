package com.alex.cucumber;

import com.alex.cucumber.config.ModConfigs;
import com.alex.cucumber.init.ModRecipeSerializers;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cucumber implements ModInitializer {
	public static final String NAME = "Cucumber Library";
	public static final String MOD_ID = "cucumber";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	@Override
	public void onInitialize() {
		ModRecipeSerializers.registerModRecipeSerializers();

		ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.CLIENT, ModConfigs.CLIENT);
		ForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, ModConfigs.COMMON);
	}
}
