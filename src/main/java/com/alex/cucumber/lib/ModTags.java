package com.alex.cucumber.lib;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import static com.alex.cucumber.Cucumber.MOD_ID;

public class ModTags {
    public static final TagKey<Block> MINEABLE_WITH_PAXEL = TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, "mineable/paxel"));
    public static final TagKey<Block> MINEABLE_WITH_SICKLE = TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, "mineable/sickle"));
}
