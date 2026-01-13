package net.paxquinn.hotbar_utils.config;

import me.shedaniel.math.Color;
import net.minecraft.client.util.InputUtil;

public class BackSlot {
    public InputUtil.Key key;
    public int id;
    public SlotType type;
    public Color color;

    public BackSlot(InputUtil.Key defaultKey, int id, SlotType type, Color color) {
        this.key = defaultKey;
        this.id = id;
        this.type = type;
        this.color = color;
    }
}
