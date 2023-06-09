package com.alex.cucumber.helper;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraftforge.common.ForgeConfigSpec;

public final class ConfigHelper {
    public static void load(ForgeConfigSpec config, String location) {
        var path = FabricLoader.getInstance().getConfigDir().resolve(location);
        var data = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();

        data.load();

        config.setConfig(data);
    }
}
