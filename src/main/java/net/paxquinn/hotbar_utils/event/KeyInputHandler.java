package net.paxquinn.hotbar_utils.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Identifier;
import net.paxquinn.hotbar_utils.config.ConfigManager;
import net.paxquinn.hotbar_utils.config.HotbarUtilsConfig;
import net.paxquinn.hotbar_utils.config.HotbarUtilsConfigScreen;
import net.paxquinn.hotbar_utils.config.SlotType;
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
            HotbarUtilsConfig config = ConfigManager.get();

            if (keySettings.wasPressed()) {
                client.setScreen(HotbarUtilsConfigScreen.create(client.currentScreen));
            }
            if (!config.enabled) return;

            if (keyBackSlot1.wasPressed()) {
                pullItemToSlot(client, config.backSlot1.id, config.backSlot1.type);
            }
            if (keyBackSlot2.wasPressed()) {
                pullItemToSlot(client, config.backSlot2.id, config.backSlot2.type);
            }
            if (keyBackSlot3.wasPressed()) {
                pullItemToSlot(client, config.backSlot3.id, config.backSlot3.type);
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

    private static void pullItemToSlot(MinecraftClient client, int slot, SlotType type) {
        if (client.player == null || client.interactionManager == null) return;

        PlayerInventory inv = client.player.getInventory();
        int selectedHotbarSlot = inv.getSelectedSlot();
        if (type == SlotType.INVENTORY) {
            client.interactionManager.clickSlot(
                    client.player.currentScreenHandler.syncId,
                    slot,
                    selectedHotbarSlot,
                    SlotActionType.SWAP,
                    client.player
            );
            return;
        }

        ItemStack heldStack = client.player.getMainHandStack();
        EquipmentSlot heldPreferredEquipmentSlot = client.player.getPreferredEquipmentSlot(heldStack);
        if (type == SlotType.HELMET && (heldPreferredEquipmentSlot == EquipmentSlot.HEAD || heldStack.isEmpty())) {
            client.interactionManager.clickSlot(client.player.currentScreenHandler.syncId, 5, selectedHotbarSlot, SlotActionType.SWAP, client.player);
        }
        if (type == SlotType.CHESTPLATE && (heldPreferredEquipmentSlot == EquipmentSlot.BODY || heldStack.isEmpty())) {
            client.interactionManager.clickSlot(client.player.currentScreenHandler.syncId, 6, selectedHotbarSlot, SlotActionType.SWAP, client.player);
        }
        if (type == SlotType.LEGGINGS && (heldPreferredEquipmentSlot == EquipmentSlot.LEGS || heldStack.isEmpty())) {
            client.interactionManager.clickSlot(client.player.currentScreenHandler.syncId, 7, selectedHotbarSlot, SlotActionType.SWAP, client.player);
        }
        if (type == SlotType.BOOTS && (heldPreferredEquipmentSlot == EquipmentSlot.FEET || heldStack.isEmpty())) {
            client.interactionManager.clickSlot(client.player.currentScreenHandler.syncId, 8, selectedHotbarSlot, SlotActionType.SWAP, client.player);
        }
    }
}
