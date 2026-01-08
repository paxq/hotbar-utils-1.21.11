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
    private static HotbarUtilsConfig config = new HotbarUtilsConfig();

    public static void load() {
        if (!FILE.exists()) {
            save();
            return;
        }
        try (FileReader reader = new FileReader(FILE)) {
            HotbarUtilsConfig loaded = GSON.fromJson(reader, HotbarUtilsConfig.class);
            HotbarUtilsConfig.enabled = loaded.enabled;
            HotbarUtilsConfig.backSlot1 = loaded.backSlot1;
            HotbarUtilsConfig.backSlot2 = loaded.backSlot2;
            HotbarUtilsConfig.backSlot3 = loaded.backSlot3;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(FILE)) {
            GSON.toJson(new HotbarUtilsConfig(), writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HotbarUtilsConfig get() {
        return config;
    }
}
