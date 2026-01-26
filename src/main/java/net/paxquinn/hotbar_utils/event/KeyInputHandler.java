package net.paxquinn.hotbar_utils.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.paxquinn.hotbar_utils.HotbarMemory;
import net.paxquinn.hotbar_utils.config.ConfigManager;
import net.paxquinn.hotbar_utils.config.HotbarUtilsConfig;
import net.paxquinn.hotbar_utils.config.HotbarUtilsConfigScreen;
import net.paxquinn.hotbar_utils.config.SlotType;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayDeque;
import java.util.Queue;

public class KeyInputHandler {
    public static final KeyBinding.Category HOTBAR_UTILS_CATEGORY = KeyBinding.Category.create(Identifier.of("hotbar_utils", "main"));
    public static final String KEY_SETTINGS = "key.hotbar_utils.settings";
    public static final String KEY_BACK_SLOT_1 = "key.hotbar_utils.slot_41";
    public static final String KEY_BACK_SLOT_2 = "key.hotbar_utils.slot_42";
    public static final String KEY_BACK_SLOT_3 = "key.hotbar_utils.slot_43";
    public static final String KEY_RESTOCK = "key.hotbar_utils.restock";
    public static final String KEY_ELYTRA = "key.hotbar_utils.quick_elytra";

    public static KeyBinding keySettings;
    public static KeyBinding keyBackSlot1;
    public static KeyBinding keyBackSlot2;
    public static KeyBinding keyBackSlot3;
    public static KeyBinding keyRestock;
    public static KeyBinding keyElytra;


    private static final Queue<Integer> RESTOCK_QUEUE = new ArrayDeque<>();
    private static ItemStack restockTarget = ItemStack.EMPTY;
    private static int restockHotbarSlot = -1;
    private static boolean RESTOCK_ACTIVE = false;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
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
            if (keyRestock.wasPressed()) {
                restockViaClient(client);
            }
            if (keyElytra.wasPressed()) {
                swapElytra(client, config.preferredChestplateName, config.preferredChestplateType, config.preferredElytraName);
            }


            if (RESTOCK_ACTIVE) {
                if (client.interactionManager == null) {
                    RESTOCK_ACTIVE = false;
                    RESTOCK_QUEUE.clear();
                    return;
                }

                PlayerInventory inv = client.player.getInventory();
                ScreenHandler handler = client.player.currentScreenHandler;

                ItemStack held = inv.getStack(restockHotbarSlot);
                if (!held.isEmpty() && held.getCount() >= restockTarget.getMaxCount()) {
                    RESTOCK_ACTIVE = false;
                    RESTOCK_QUEUE.clear();
                    return;
                }

                Integer slot = RESTOCK_QUEUE.poll();
                if (slot == null) {
                    RESTOCK_ACTIVE = false;
                    return;
                }

                if (restockTarget.getMaxCount() <= 1 || held.isEmpty()) {
                    client.interactionManager.clickSlot(handler.syncId, slot, restockHotbarSlot, SlotActionType.SWAP, client.player);
                } else {
                    client.interactionManager.clickSlot(handler.syncId, slot, 0, SlotActionType.PICKUP, client.player);
                    client.interactionManager.clickSlot(handler.syncId, restockHotbarSlot, 0, SlotActionType.PICKUP, client.player);
                    if (!handler.getCursorStack().isEmpty()) client.interactionManager.clickSlot(handler.syncId, slot, 0, SlotActionType.PICKUP, client.player);
                }
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
            GLFW.GLFW_KEY_Y,
            HOTBAR_UTILS_CATEGORY
        ));
        keyBackSlot2 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_BACK_SLOT_2,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_U,
                HOTBAR_UTILS_CATEGORY
        ));
        keyBackSlot3 = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_BACK_SLOT_3,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                HOTBAR_UTILS_CATEGORY
        ));
        keyRestock = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_RESTOCK,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                HOTBAR_UTILS_CATEGORY
        ));
        keyElytra = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_ELYTRA,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_P,
                HOTBAR_UTILS_CATEGORY
        ));

        registerKeyInputs();
    }

    private static void restockViaClient(MinecraftClient client) {
        if (client.player == null || client.interactionManager == null) return;

        PlayerInventory inv = client.player.getInventory();
        int selectedSlot = inv.getSelectedSlot();

        ItemStack held = inv.getStack(selectedSlot);
        ItemStack target = held.isEmpty() ? HotbarMemory.recall(selectedSlot) : held;

        if (target == null || target.isEmpty()) return;
        if (!held.isEmpty()) HotbarMemory.remember(selectedSlot, target);

        ScreenHandler handler = client.player.currentScreenHandler;

        RESTOCK_QUEUE.clear();
        restockTarget = target.copy();
        restockHotbarSlot = selectedSlot;
        RESTOCK_ACTIVE = true;

        for (int slot = 9; slot < 36; slot++) {
            ItemStack stack = handler.getSlot(slot).getStack();

            if (stack.isEmpty()) continue;
            if (!ItemStack.areItemsAndComponentsEqual(stack, restockTarget)) continue;

            RESTOCK_QUEUE.add(slot);
        }
    }

    private static void swapElytra(MinecraftClient client, String preferredChestType, String preferredChestName, String preferredElytra) {
        if (client.player == null || client.interactionManager == null) return;
        ScreenHandler handler = client.player.currentScreenHandler;

        PlayerInventory inv = client.player.getInventory();
        int selectedHotbarSlot = inv.getSelectedSlot();

        ItemStack equippedItem = client.player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack preferredChestItem = createPreferredStack(preferredChestName, preferredChestType);
        ItemStack preferredElytraItem = createPreferredStack("minecraft:elytra", preferredElytra);
        ItemStack preferredItem = equippedItem.isOf(Items.ELYTRA) ? preferredChestItem : preferredElytraItem;

        int searchFallbackSlot = -1;
        for (int slot = 9; slot < 36; slot++) {
            ItemStack stack = handler.getSlot(slot).getStack();

            if (stack.isEmpty()) continue;
            // Create fallback if available
            if (stack.isOf(preferredItem.getItem()) || (equippedItem.isOf(Items.ELYTRA) && client.player.getPreferredEquipmentSlot(stack) == EquipmentSlot.CHEST)) searchFallbackSlot = slot;
            // Equip random chestplate
            if (equippedItem.isOf(Items.ELYTRA) && preferredItem.isEmpty() && client.player.getPreferredEquipmentSlot(stack) == EquipmentSlot.CHEST) {
                executeSwap(client, slot, selectedHotbarSlot);
                return;
            }
            // Equip specific item
            if (ItemStack.areItemsAndComponentsEqual(stack, preferredItem)) {
                executeSwap(client, slot, selectedHotbarSlot);
                return;
            }

            if (slot < 35 || searchFallbackSlot == -1) continue;
            executeSwap(client, searchFallbackSlot, selectedHotbarSlot);
            return;
        }
    }
    private static void executeSwap(MinecraftClient client, int slot, int selectedSlot) {
        if (client.player == null || client.interactionManager == null) return;
        client.interactionManager.clickSlot(client.player.currentScreenHandler.syncId, slot, selectedSlot, SlotActionType.SWAP, client.player);
        client.interactionManager.clickSlot(client.player.currentScreenHandler.syncId, 6, selectedSlot, SlotActionType.SWAP, client.player);
        client.interactionManager.clickSlot(client.player.currentScreenHandler.syncId, slot, selectedSlot, SlotActionType.SWAP, client.player);
    }
    private static Item itemFromString(String id) {
        Identifier identifier = Identifier.tryParse(id);
        if (identifier == null) return null;
        return Registries.ITEM.get(identifier);
    }
    private static ItemStack createPreferredStack(String id, String name) {
        Item item = itemFromString(id);
        if (item == null || item == Items.AIR) return ItemStack.EMPTY;
        ItemStack stack = new ItemStack(item, 1);
        if (name == null || name.isEmpty()) return stack;
        stack.set(DataComponentTypes.CUSTOM_NAME, Text.literal(name));
        return stack;
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
