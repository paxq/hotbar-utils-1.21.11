package net.paxquinn.hotbar_utils.overlay;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.paxquinn.hotbar_utils.config.*;

import java.util.Arrays;
import java.util.List;

public class HotbarOverlay {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final Identifier WIDGET_HOTBAR_VANILLA = Identifier.ofVanilla("textures/gui/sprites/hud/hotbar.png");
    private static final Identifier WIDGET_OFFHAND_VANILLA = Identifier.ofVanilla("textures/gui/sprites/hud/hotbar_offhand_right.png");
    private static final Identifier WIDGET_HOTBAR_NUMBER = Identifier.of("hotbar-utils", "textures/gui/hotbar.png");

    public static void render(DrawContext context, RenderTickCounter tickCounter) {
        if (client.player == null) return;

        HotbarUtilsConfig config = ConfigManager.get();

        if (!config.hudEnabled || !config.enabled || client.options.hudHidden) return;

        boolean isLeftHanded = client.options.getMainArm().getValue() == Arm.LEFT;
        boolean anchorAndClientHandMatch = (isLeftHanded && config.hudAnchor == HudAnchor.LEFT) || (!isLeftHanded && config.hudAnchor == HudAnchor.RIGHT);
        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();
        int hotbarLeft = screenWidth / 2 - 90;
        int offhandOffset = !client.player.getOffHandStack().isEmpty() || config.hudAnchorType == HudAnchorType.STATIC ? 28 : 0;
        int totalOffset = anchorAndClientHandMatch ? config.hudOffset : config.hudOffset + offhandOffset;
        int slotWidth = 20;
        int slotIndex = 9;
        int slotX;
        int slotY = screenHeight - 22;
        int skippedSlots = 0;

        config.updateKeybinds();
        List<BackSlot> slotList = Arrays.asList(config.backSlot1, config.backSlot2, config.backSlot3);
        for (int i = 0; i < slotList.toArray().length; i++) {
            // Get Stack
            ItemStack stack = client.player.getInventory().getStack(slotList.get(i).id);
            if (slotList.get(i).type == SlotType.HELMET) stack = client.player.getEquippedStack(EquipmentSlot.HEAD);
            if (slotList.get(i).type == SlotType.CHESTPLATE) stack = client.player.getEquippedStack(EquipmentSlot.BODY);
            if (slotList.get(i).type == SlotType.LEGGINGS) stack = client.player.getEquippedStack(EquipmentSlot.LEGS);
            if (slotList.get(i).type == SlotType.BOOTS) stack = client.player.getEquippedStack(EquipmentSlot.FEET);

            // Skip rendering if empty
            if (config.hudAnchorType == HudAnchorType.DYNAMIC && (stack.isEmpty() || (!config.renderUnbound && slotList.get(i).key.getCode() == -1))) {
                skippedSlots++;
                continue;
            }

            // Determine slot/item position
            int addedIndex = i - skippedSlots;
            if (config.hudAnchor == HudAnchor.RIGHT) {
                slotX = hotbarLeft + ((slotIndex + addedIndex) * slotWidth) + totalOffset + (config.hudSpacing * addedIndex);
            } else {
                slotX = hotbarLeft - ((1 + addedIndex) * slotWidth) - totalOffset - (config.hudSpacing * addedIndex) - 2;
            }

            // Render Slot(s)
            if (config.hudSpacing > 0 || skippedSlots > 0) {
                renderIndividualSlot(context, slotX, slotY, config.hudOffhandTexture);
            } else if (i == 0) {
                slotX = hotbarLeft + (slotIndex * slotWidth) + totalOffset;
                if (config.hudAnchor == HudAnchor.LEFT) slotX = hotbarLeft - (3 * slotWidth) - totalOffset - 2;
                renderConnectedSlot(context, slotX, slotY);
            }
            // Render Overlay
            if (slotList.get(i).overlay > 0) renderOverlay(context, slotX, slotY, slotList.get(i).overlay - 1, slotWidth);

            if (stack.isEmpty() || (!config.renderUnbound && slotList.get(i).key.getCode() == -1)) continue; // Skip item rendering if empty or key is unbound

            // Render Item
            renderItem(context, stack, slotX + 3, slotY + 3);
        }
    }

    private static void renderIndividualSlot(DrawContext context, int x, int y, boolean offhand_texture) {
        if (offhand_texture) {
            // Offhand Style
            context.drawTexture(RenderPipelines.GUI_TEXTURED, WIDGET_OFFHAND_VANILLA, x, y, 7, 1, 22, 22, 29, 24);
            return;
        }
        // Hotbar Style
        context.drawTexture(RenderPipelines.GUI_TEXTURED, WIDGET_HOTBAR_VANILLA, x, y, 0, 0, 20, 22, 182, 22);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, WIDGET_HOTBAR_VANILLA, x + 1, y, 161, 0, 21, 22, 182, 22);
    }
    private static void renderConnectedSlot(DrawContext context, int x, int y) {
        context.drawTexture(RenderPipelines.GUI_TEXTURED, WIDGET_HOTBAR_VANILLA, x, y, 0, 0, 60, 22, 182, 22);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, WIDGET_HOTBAR_VANILLA, x + 1, y, 121, 0, 61, 22, 182, 22);
    }

    private static void renderItem(DrawContext context, ItemStack stack, int x, int y) {
        String countText = "";
        int count = stack.getCount();
        if (count > 1) countText = String.valueOf(count);

        context.drawItem(stack, x, y);
        context.drawStackOverlay(client.textRenderer, stack, x, y, countText);
    }

    private static void renderOverlay(DrawContext context, int x, int y, int index, int width) {
        context.drawTexture(RenderPipelines.GUI_TEXTURED, WIDGET_HOTBAR_NUMBER, x, y, index * width, 0, width, 22, 242, 22);
    }
}
