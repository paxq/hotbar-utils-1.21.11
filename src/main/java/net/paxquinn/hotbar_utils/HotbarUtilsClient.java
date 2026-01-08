package net.paxquinn.hotbar_utils;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.SlotActionType;
import net.paxquinn.hotbar_utils.config.ConfigManager;
import net.paxquinn.hotbar_utils.event.KeyInputHandler;
import net.paxquinn.hotbar_utils.overlay.HotbarOverlay;

public class HotbarUtilsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        ConfigManager.load();
        HudRenderCallback.EVENT.register(HotbarOverlay::render);
    }

    public static void pullItemToSlot(MinecraftClient client, int sourceSlot) {
        if (client.player == null || client.interactionManager == null) return;

        PlayerInventory inv = client.player.getInventory();
        if (sourceSlot == -1) return;

        int selectedHotbarSlot = inv.getSelectedSlot(); // 0–8

        client.interactionManager.clickSlot(
                client.player.currentScreenHandler.syncId,
                sourceSlot,
                selectedHotbarSlot,
                SlotActionType.SWAP,
                client.player
        );
    }
}
