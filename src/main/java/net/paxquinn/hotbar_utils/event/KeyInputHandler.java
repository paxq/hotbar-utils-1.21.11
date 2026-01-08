package net.paxquinn.hotbar_utils.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.paxquinn.hotbar_utils.HotbarUtilsClient;
import net.paxquinn.hotbar_utils.config.ConfigManager;
import net.paxquinn.hotbar_utils.config.HotbarUtilsConfig;
import net.paxquinn.hotbar_utils.config.HotbarUtilsConfigScreen;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final KeyBinding.Category HOTBAR_UTILS_CATEGORY = KeyBinding.Category.create(Identifier.of("hotbar_utils", "main"));
    public static final String KEY_SETTINGS = "key.hotbar_utils.settings";
    public static final String KEY_BACK_SLOT_1 = "key.hotbar_utils.slot_41";
    public static final String KEY_BACK_SLOT_2 = "key.hotbar_utils.slot_42";
    public static final String KEY_BACK_SLOT_3 = "key.hotbar_utils.slot_43";

    public static KeyBinding keySettings;
    public static KeyBinding keyBackSlot1;
    public static KeyBinding keyBackSlot2;
    public static KeyBinding keyBackSlot3;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            assert client.player != null;

            if (keySettings.wasPressed()) {
                client.player.sendMessage(Text.literal("Opening Hotbar Utils Settings..."), true);
                client.setScreen(HotbarUtilsConfigScreen.create(client.currentScreen));
            }

            if (!HotbarUtilsConfig.enabled) return;

            if (keyBackSlot1.wasPressed()) {
                HotbarUtilsClient.pullItemToSlot(client, HotbarUtilsConfig.backSlot1);
            }
            if (keyBackSlot2.wasPressed()) {
                HotbarUtilsClient.pullItemToSlot(client, HotbarUtilsConfig.backSlot2);
            }
            if (keyBackSlot3.wasPressed()) {
                HotbarUtilsClient.pullItemToSlot(client, HotbarUtilsConfig.backSlot3);
            }
        });
    }

    public static void register() {
        keySettings = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_SETTINGS,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_0,
                HOTBAR_UTILS_CATEGORY
        ));
        keyBackSlot1 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            KEY_BACK_SLOT_1,
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_7,
            HOTBAR_UTILS_CATEGORY
        ));
        keyBackSlot2 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_BACK_SLOT_2,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_8,
                HOTBAR_UTILS_CATEGORY
        ));
        keyBackSlot3 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_BACK_SLOT_3,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_9,
                HOTBAR_UTILS_CATEGORY
        ));

        registerKeyInputs();
    }
}
