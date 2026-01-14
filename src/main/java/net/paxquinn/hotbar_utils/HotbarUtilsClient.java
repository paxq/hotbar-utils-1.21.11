package net.paxquinn.hotbar_utils;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.paxquinn.hotbar_utils.config.ConfigManager;
import net.paxquinn.hotbar_utils.event.KeyInputHandler;
import net.paxquinn.hotbar_utils.overlay.HotbarOverlay;

public class HotbarUtilsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        ConfigManager.init();
        HudRenderCallback.EVENT.register(HotbarOverlay::render);
    }
}
