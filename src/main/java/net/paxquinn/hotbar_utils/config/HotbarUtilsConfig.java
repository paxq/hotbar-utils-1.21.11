package net.paxquinn.hotbar_utils.config;

import java.util.Arrays;
import java.util.List;

public class HotbarUtilsConfig {
    public boolean enabled = true;
    public int backSlot1 = 0;
    public int backSlot2 = 0;
    public int backSlot3 = 0;
    public SlotType backSlot1Type = SlotType.INVENTORY;
    public SlotType backSlot2Type = SlotType.INVENTORY;
    public SlotType backSlot3Type = SlotType.INVENTORY;
    public List<Integer> backSlotIDs = Arrays.asList(backSlot1, backSlot2, backSlot3);
    public List<SlotType> backSlotTypes = Arrays.asList(backSlot1Type, backSlot2Type, backSlot3Type);

    public boolean hudEnabled = true;
    public boolean hudOffhandTexture = false;
    public boolean renderUnbound = true;
    public int hudOffset = 8; //GUI Pixels
    public int hudSpacing = 0;
    public HudAnchor hudAnchor = HudAnchor.RIGHT;
    public HudAnchorType hudAnchorType = HudAnchorType.STATIC;

    public void updateIDs() {
        backSlotIDs = Arrays.asList(backSlot1, backSlot2, backSlot3);
    }
    public void updateTypes() {
        backSlotTypes = Arrays.asList(backSlot1Type, backSlot2Type, backSlot3Type);
    }
}