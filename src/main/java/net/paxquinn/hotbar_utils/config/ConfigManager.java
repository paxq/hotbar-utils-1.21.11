package net.paxquinn.hotbar_utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File FILE =
            FabricLoader.getInstance().getConfigDir().resolve("hotbar_utils.json").toFile();
    private static HotbarUtilsConfig config;

    public static void init() {
        load();
    }

    public static void load() {
        if (!FILE.exists()) {
            config = new HotbarUtilsConfig();
            validateConfig(config);
            save();
            return;
        }
        try (FileReader reader = new FileReader(FILE)) {
            config = GSON.fromJson(reader, HotbarUtilsConfig.class);
            if (config == null) config = new HotbarUtilsConfig();
        } catch (Exception e) {
            e.printStackTrace();
            config = new HotbarUtilsConfig();
        }

        validateConfig(config);
        config.updateKeybinds();
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(FILE)) {
            GSON.toJson(config, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HotbarUtilsConfig get() {
        return config;
    }

    private static void validateConfig(HotbarUtilsConfig config) {
        if (config.hudAnchor == null) config.hudAnchor = HudAnchor.RIGHT;
        if (config.hudAnchorType == null) config.hudAnchorType = HudAnchorType.STATIC;
    }
}
