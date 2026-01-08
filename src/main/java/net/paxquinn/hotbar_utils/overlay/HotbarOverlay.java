package net.paxquinn.hotbar_utils.overlay;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;

import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.paxquinn.hotbar_utils.config.HotbarUtilsConfig;

public class HotbarOverlay {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static void render(DrawContext context, RenderTickCounter tickCounter) {
        if (client.player == null) return;

// FOR THE FUTURE
//        private static ItemStack findMatchingItem(MinecraftClient client) {
//    for (ItemStack stack : client.player.getInventory().main) {
//        if (isValid(stack)) {
//            return stack.copy();
//        }
//    }
//    return ItemStack.EMPTY;
//}

        ItemStack stack = new ItemStack(Items.GRASS_BLOCK);
        if (stack.isEmpty()) return;

        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();

        int hotbarWidth = 182; // 9 slots * 20 pixels
        int baseX = screenWidth / 2 - hotbarWidth / 2; // left-most slot
        int baseY = screenHeight - 22;

        int slotIndex = 9; // the “10th” slot (0-indexed)
        int slotSpacing = 20; // spacing between vanilla slots

        int slotX = baseX + slotIndex * slotSpacing;
        int slotY = baseY;

        renderSlot(context, slotX - 4, slotY - 4);
        renderItem(context, stack, slotX, slotY);
    }

    private static final Identifier WIDGETS =
            Identifier.of("minecraft", "textures/gui/widgets.png");

    private static void renderSlot(DrawContext context, int x, int y) {
        context.drawTexture(
                RenderPipelines.GUI,  // pipeline
                WIDGETS,              // texture
                x, y,                 // screen position
                0f, 0f,           // UV coords of hotbar slot
                24, 24,        // size
                256, 256, // texture atlas size
                0xFFFFFFFF            // color (white, full alpha)
        );
    }

    private static void renderItem(DrawContext context, ItemStack stack, int x, int y) {
        context.drawItem(stack, x, y);
        context.drawStackOverlay(
                client.textRenderer,
                stack,
                x,
                y,
                "∞"
        );
    }
}
