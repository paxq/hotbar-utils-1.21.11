package net.paxquinn.hotbar_utils.config;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

import java.util.Arrays;
import java.util.List;

public class HotbarUtilsConfig {
    public static boolean enabled = true;
    public static int backSlot1 = 9;
    public static int backSlot2 = 10;
    public static int backSlot3 = 11;
    public static List<Integer> backSlots = Arrays.asList(backSlot1, backSlot2, backSlot3);

    public static boolean hudEnabled = true;
    public static boolean hudOffhandTexture = false;
    public static int hudOffset = 8; //GUI Pixels
    public static int hudSpacing = 0;
    public static HudAnchor hudAnchor = HudAnchor.RIGHT;
    public static HudAnchorType hudAnchorType = HudAnchorType.STATIC;

}