package com.alex.cucumber;

import com.alex.cucumber.init.ModRecipeSerializers;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cucumber implements ModInitializer {
	public static final String NAME = "Cucumber Library";
	public static final String MOD_ID = "cucumber";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	@Override
	public void onInitialize() {
		ModRecipeSerializers.registerRecipeSerializers();
	}
}
