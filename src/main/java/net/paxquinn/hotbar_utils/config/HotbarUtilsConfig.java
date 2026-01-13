package net.paxquinn.hotbar_utils.config;

import net.paxquinn.hotbar_utils.event.KeyInputHandler;
import net.paxquinn.hotbar_utils.mixin.KeyBindingAccessor;

public class HotbarUtilsConfig {
    public boolean enabled = true;
    public BackSlot backSlot1 = new BackSlot(((KeyBindingAccessor) KeyInputHandler.keyBackSlot1).hotbarutils$getBoundKey(), 0, SlotType.INVENTORY, null);
    public BackSlot backSlot2 = new BackSlot(((KeyBindingAccessor) KeyInputHandler.keyBackSlot2).hotbarutils$getBoundKey(), 0, SlotType.INVENTORY, null);
    public BackSlot backSlot3 = new BackSlot(((KeyBindingAccessor) KeyInputHandler.keyBackSlot3).hotbarutils$getBoundKey(), 0, SlotType.INVENTORY, null);

    public boolean hudEnabled = true;
    public boolean hudOffhandTexture = false;
    public boolean renderUnbound = true;
    public int hudOffset = 8; //GUI Pixels
    public int hudSpacing = 0;
    public HudAnchor hudAnchor = HudAnchor.RIGHT;
    public HudAnchorType hudAnchorType = HudAnchorType.STATIC;

    public void updateKeybinds() {
        backSlot1.key = ((KeyBindingAccessor) KeyInputHandler.keyBackSlot1).hotbarutils$getBoundKey();
        backSlot2.key = ((KeyBindingAccessor) KeyInputHandler.keyBackSlot2).hotbarutils$getBoundKey();
        backSlot3.key = ((KeyBindingAccessor) KeyInputHandler.keyBackSlot3).hotbarutils$getBoundKey();
    }
}