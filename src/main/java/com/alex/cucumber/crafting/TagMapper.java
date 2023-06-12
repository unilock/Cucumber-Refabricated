package com.alex.cucumber.crafting;

import com.alex.cucumber.Cucumber;
import com.alex.cucumber.compat.almostunified.AlmostUnifiedAdapter;
import com.alex.cucumber.config.ModConfigs;
import com.alex.cucumber.forge.event.TagsUpdatedEvent;
import com.google.common.base.Stopwatch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TagMapper {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private static final Map<String, String> TAG_TO_ITEM_MAP = new HashMap<>();

    public static void onTagsUpdated(TagsUpdatedEvent event) {
        if (event.shouldUpdateStaticData())
            reloadTagMappings();
    }

    public static void reloadTagMappings() {
        var stopwatch = Stopwatch.createStarted();
        var dir = FabricLoader.getInstance().getConfigDir().toFile();

        TAG_TO_ITEM_MAP.clear();

        if (dir.exists() && dir.isDirectory()) {
            var file = FabricLoader.getInstance().getConfigDir().resolve("cucumber-tags.json").toFile();

            if (file.exists() && file.isFile()) {
                JsonObject json;
                FileReader reader = null;

                try {
                    var parser = new JsonParser();
                    reader = new FileReader(file);
                    json = parser.parse(reader).getAsJsonObject();

                    json.entrySet().stream().filter(e -> {
                        String value = e.getValue().getAsString();
                        return !"__comment".equalsIgnoreCase(e.getKey()) && !value.isEmpty() && !"null".equalsIgnoreCase(value);
                    }).forEach(entry -> {
                        var tagId = entry.getKey();
                        var itemId = entry.getValue().getAsString();

                        TAG_TO_ITEM_MAP.put(tagId, itemId);

                        // if auto refresh tag entries is enabled, we check any entries that contain an item ID to see
                        // if they are still present. if not we just refresh the entry
                        if (ModConfigs.AUTO_REFRESH_TAG_ENTRIES.get()) {
                            if (!itemId.isEmpty() && !"null".equalsIgnoreCase(itemId)) {
                                var item = BuiltInRegistries.ITEM.get(new ResourceLocation(itemId));
                                if (item == null || item == Items.AIR) {
                                    addTagToFile(tagId, json, file, false);
                                }
                            }
                        }
                    });

                    // save changes to disk if refresh is enabled
                    if (ModConfigs.AUTO_REFRESH_TAG_ENTRIES.get())
                        saveToFile(json, file);

                    reader.close();
                } catch (Exception e) {
                    Cucumber.LOGGER.error("An error occurred while reading cucumber-tags.json", e);
                } finally {
                    IOUtils.closeQuietly(reader);
                }
            } else {
                generateNewConfig(file);
            }
        }

        stopwatch.stop();

        Cucumber.LOGGER.info("Loaded cucumber-tags.json in {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    public static Item getItemForTag(String tagId) {
        var preferredItem = AlmostUnifiedAdapter.getPreferredItemForTag(tagId);
        if (preferredItem != null) {
            return preferredItem;
        }

        if (TAG_TO_ITEM_MAP.containsKey(tagId)) {
            var id = TAG_TO_ITEM_MAP.get(tagId);
            return BuiltInRegistries.ITEM.get(new ResourceLocation(id));
        } else {
            var file = FabricLoader.getInstance().getConfigDir().resolve("cucumber-tags.json").toFile();
            if (!file.exists()) {
                generateNewConfig(file);
            }

            if (file.isFile()) {
                JsonObject json = null;
                FileReader reader = null;

                try {
                    var parser = new JsonParser();
                    reader = new FileReader(file);
                    json = parser.parse(reader).getAsJsonObject();
                } catch (Exception e) {
                    Cucumber.LOGGER.error("An error occurred while reading cucumber-tags.json", e);
                } finally {
                    IOUtils.closeQuietly(reader);
                }

                if (json != null) {
                    if (json.has(tagId)) {
                        var itemId = json.get(tagId).getAsString();
                        if (itemId.isEmpty() || "null".equalsIgnoreCase(itemId))
                            return addTagToFile(tagId, json, file);

                        TAG_TO_ITEM_MAP.put(tagId, itemId);

                        return BuiltInRegistries.ITEM.get(new ResourceLocation(itemId));
                    }

                    return addTagToFile(tagId, json, file);
                }
            }

            return Items.AIR;
        }
    }

    public static ItemStack getItemStackForTag(String tagId, int size) {
        Item item = getItemForTag(tagId);
        return item != null && item != Items.AIR ? new ItemStack(item, size) : ItemStack.EMPTY;
    }

    private static Item addTagToFile(String tagId, JsonObject json, File file) {
        return addTagToFile(tagId, json, file, true);
    }

    private static Item addTagToFile(String tagId, JsonObject json, File file, boolean save) {
        var mods = ModConfigs.MOD_TAG_PRIORITIES.get();
        var key = ItemTags.bind(tagId);
        var tags = BuiltInRegistries.ITEM.holders().filter(entry -> entry.is(key));

        var item = tags.min((item1, item2) -> {
            var id1 = item1.unwrapKey().orElse(null);
            var index1 = id1 != null ? mods.indexOf(id1.location().getNamespace()) : -1;

            var id2 = item2.unwrapKey().orElse(null);
            var index2 = id2 != null ? mods.indexOf(id2.location().getNamespace()) : -1;

            return index1 > index2 ? 1 : index1 == -1 ? 0 : -1;
        }).orElse(Items.AIR.builtInRegistryHolder());

        var itemId = item.value().equals(Items.AIR) ? "null" : item.unwrapKey().orElse(ResourceKey.create(Registries.ITEM, new ResourceLocation("air"))).location().toString();

        json.addProperty(tagId, itemId);
        TAG_TO_ITEM_MAP.put(tagId, itemId);

        if (save) {
            saveToFile(json, file);
        }

        return item.value();
    }

    private static void saveToFile(JsonObject json, File file) {
        try (var writer = new FileWriter(file)) {
            GSON.toJson(json, writer);
        } catch (IOException e) {
            Cucumber.LOGGER.error("An error occurred while writing to cucumber-tags.json", e);
        }
    }

    private static void generateNewConfig(File file) {
        try (var writer = new FileWriter(file)) {
            var object = new JsonObject();
            object.addProperty("__comment", "Instructions: https://blakesmods.com/docs/cucumber/tags-config");

            GSON.toJson(object, writer);
        } catch (IOException e) {
            Cucumber.LOGGER.error("An error occurred while creating cucumber-tags.json", e);
        }
    }
}
