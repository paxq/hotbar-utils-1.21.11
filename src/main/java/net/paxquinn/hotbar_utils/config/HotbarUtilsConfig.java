package net.paxquinn.hotbar_utils.config;

import java.util.Arrays;
import java.util.List;

public class HotbarUtilsConfig {
    public static boolean enabled = true;
    public static int backSlot1 = 9;
    public static int backSlot2 = 10;
    public static int backSlot3 = 11;
    public static SlotType backSlot1Type = SlotType.INVENTORY;
    public static SlotType backSlot2Type = SlotType.INVENTORY;
    public static SlotType backSlot3Type = SlotType.INVENTORY;
    public static List<Integer> backSlotIDs = Arrays.asList(backSlot1, backSlot2, backSlot3);
    public static List<SlotType> backSlotTypes = Arrays.asList(backSlot1Type, backSlot2Type, backSlot3Type);

    public static boolean hudEnabled = true;
    public static boolean hudOffhandTexture = false;
    public static int hudOffset = 8; //GUI Pixels
    public static int hudSpacing = 0;
    public static HudAnchor hudAnchor = HudAnchor.RIGHT;
    public static HudAnchorType hudAnchorType = HudAnchorType.STATIC;

}