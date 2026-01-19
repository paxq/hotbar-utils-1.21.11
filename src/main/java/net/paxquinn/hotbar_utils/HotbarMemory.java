package net.paxquinn.hotbar_utils;

import net.minecraft.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class HotbarMemory {
    private static final ItemStack[] LAST_STACKS = new ItemStack[9];

    public static void remember(int slot, ItemStack stack) {
        LAST_STACKS[slot] = stack.copy();
    }

    @Nullable
    public static ItemStack recall(int slot) {
        ItemStack stack = LAST_STACKS[slot];
        return stack == null || stack.isEmpty() ? ItemStack.EMPTY : stack.copy();
    }

    public static void reset() {

    }
}
