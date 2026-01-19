package net.paxquinn.hotbar_utils;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.paxquinn.hotbar_utils.config.ConfigManager;
import net.paxquinn.hotbar_utils.event.KeyInputHandler;
import net.paxquinn.hotbar_utils.overlay.HotbarOverlay;

public class HotbarUtilsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        ConfigManager.init();
        HudRenderCallback.EVENT.register(HotbarOverlay::render);

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            HotbarMemory.reset();

            if (client.player != null) {
                PlayerInventory inv = client.player.getInventory();
                for (int i = 0; i < 9; i++) {
                    ItemStack stack = inv.getStack(i);
                    HotbarMemory.remember(i, stack);
                }
            }
        });
    }
}
