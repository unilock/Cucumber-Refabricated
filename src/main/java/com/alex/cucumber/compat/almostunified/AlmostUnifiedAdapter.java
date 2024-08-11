package com.alex.cucumber.compat.almostunified;

import com.almostreliable.unified.api.AlmostUnifiedLookup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class AlmostUnifiedAdapter {
    private static final boolean LOADED = FabricLoader.getInstance().isModLoaded("almostunified");

    public static Item getPreferredItemForTag(String tagId) {
        if (LOADED) {
            var tagKey = TagKey.create(Registries.ITEM, ResourceLocation.tryParse(tagId));
            return AlmostUnifiedLookup.INSTANCE.getPreferredItemForTag(tagKey);
        }

        return null;
    }
}
