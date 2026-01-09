package net.paxquinn.hotbar_utils;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.paxquinn.hotbar_utils.config.HotbarUtilsConfigScreen;

public class HotbarUtilsModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return HotbarUtilsConfigScreen::create;
    }
}

