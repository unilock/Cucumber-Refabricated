package com.alex.cucumber.compat.almostunified;

import com.almostreliable.unified.api.AlmostUnifiedLookup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class AlmostUnifiedAdapter {
    public static boolean isLoaded() {
        return FabricLoader.getInstance().isModLoaded("almostunified");
    }

    public static Item getPreferredItemForTag(String tagId) {
        if (isLoaded()) {
            var tagKey = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(tagId));
            return Adapter.getPreferredItemForTag(tagKey);
        }

        return null;
    }

    private static class Adapter {
        private static Item getPreferredItemForTag(TagKey<Item> tag) {
            return AlmostUnifiedLookup.INSTANCE.getPreferredItemForTag(tag);
        }
    }
}
