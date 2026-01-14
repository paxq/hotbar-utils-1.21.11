package net.paxquinn.hotbar_utils.config;

import me.shedaniel.math.Color;
import net.minecraft.client.util.InputUtil;

public class BackSlot {
    transient public InputUtil.Key key;
    public int id = 0;
    public SlotType type = SlotType.INVENTORY;
    public Color color = null;
}
