package com.alex.cucumber.config;

import com.alex.cucumber.Cucumber;
import com.alex.cucumber.util.FeatureFlag;
import com.alex.cucumber.util.FeatureFlags;
import net.minecraft.resources.ResourceLocation;

@FeatureFlags
public final class ModFeatureFlags {
    public static final FeatureFlag NBT_TOOLTIPS = FeatureFlag.create(new ResourceLocation(Cucumber.MOD_ID, "nbt_tooltips"), ModConfigs.ENABLE_NBT_TOOLTIPS);
    public static final FeatureFlag TAG_TOOLTIPS = FeatureFlag.create(new ResourceLocation(Cucumber.MOD_ID, "tag_tooltips"), ModConfigs.ENABLE_TAG_TOOLTIPS);
}
