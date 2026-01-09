package net.paxquinn.hotbar_utils.overlay;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.paxquinn.hotbar_utils.config.HotbarUtilsConfig;
import net.paxquinn.hotbar_utils.config.HudAnchor;
import net.paxquinn.hotbar_utils.config.HudAnchorType;
import net.paxquinn.hotbar_utils.config.SlotType;

import java.util.List;

public class HotbarOverlay {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final Identifier WIDGET_HOTBAR = Identifier.ofVanilla("textures/gui/sprites/hud/hotbar.png");
    private static final Identifier WIDGET_OFFHAND = Identifier.ofVanilla("textures/gui/sprites/hud/hotbar_offhand_right.png");

    public static void render(DrawContext context, RenderTickCounter tickCounter) {
        if (client.player == null) return;
        if (!HotbarUtilsConfig.hudEnabled || !HotbarUtilsConfig.enabled || client.options.hudHidden) return;

        boolean isLeftHanded = client.options.getMainArm().getValue() == Arm.LEFT;
        boolean anchorAndClientHandMatch = (isLeftHanded && HotbarUtilsConfig.hudAnchor == HudAnchor.LEFT) || (!isLeftHanded && HotbarUtilsConfig.hudAnchor == HudAnchor.RIGHT);
        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();
        int hotbarLeft = screenWidth / 2 - 90;
        int hotbarY = screenHeight - 22;
        int offhandOffset = !client.player.getOffHandStack().isEmpty() || HotbarUtilsConfig.hudAnchorType == HudAnchorType.STATIC ? 28 : 0;
        int totalOffset = anchorAndClientHandMatch ? HotbarUtilsConfig.hudOffset : HotbarUtilsConfig.hudOffset + offhandOffset;
        int slotWidth = 20;
        int slotIndex = 9;
        int slotX = hotbarLeft + (slotIndex * slotWidth) + totalOffset;
        int slotY = hotbarY;

        if (HotbarUtilsConfig.hudSpacing <= 0) {
            if (HotbarUtilsConfig.hudAnchor == HudAnchor.RIGHT) {
                renderConnectedSlot(context, slotX, slotY);
            } else {
                slotX = hotbarLeft - (3 * slotWidth) - totalOffset - 2;
                renderConnectedSlot(context, slotX, slotY);
            }
        }
        for (int i = 0; i < HotbarUtilsConfig.backSlotIDs.toArray().length; i++) {
            List<Integer> slotIDList = HotbarUtilsConfig.backSlotIDs;
            List<SlotType> slotTypeList = HotbarUtilsConfig.backSlotTypes;
            ItemStack stack = client.player.getInventory().getStack(slotIDList.get(i));

            if (slotTypeList.get(i) == SlotType.HELMET) stack = client.player.getEquippedStack(EquipmentSlot.HEAD);
            if (slotTypeList.get(i) == SlotType.CHESTPLATE) stack = client.player.getEquippedStack(EquipmentSlot.BODY);
            if (slotTypeList.get(i) == SlotType.LEGGINGS) stack = client.player.getEquippedStack(EquipmentSlot.LEGS);
            if (slotTypeList.get(i) == SlotType.BOOTS) stack = client.player.getEquippedStack(EquipmentSlot.FEET);

            if (HotbarUtilsConfig.hudAnchor == HudAnchor.RIGHT) {
                slotX = hotbarLeft + ((slotIndex + i) * slotWidth) + totalOffset + (HotbarUtilsConfig.hudSpacing * i);
            } else {
                slotX = hotbarLeft - ((1 + i)* slotWidth) - totalOffset - (HotbarUtilsConfig.hudSpacing * i) - 2;
            }

            if (HotbarUtilsConfig.hudSpacing > 0) renderIndividualSlot(context, slotX, slotY, HotbarUtilsConfig.hudOffhandTexture);

            if (stack.isEmpty()) continue; // Skip item rendering if empty
            renderItem(context, stack, slotX + 3, slotY + 3);
        }
    }

    private static void renderIndividualSlot(DrawContext context, int x, int y, boolean offhand_texture) {
        if (offhand_texture) {
            // Offhand Style
            context.drawTexture(RenderPipelines.GUI_TEXTURED, WIDGET_OFFHAND, x, y, 7, 1, 22, 22, 29, 24);
            return;
        }
        // Hotbar Style
        context.drawTexture(RenderPipelines.GUI_TEXTURED, WIDGET_HOTBAR, x, y, 0, 0, 20, 22, 182, 22);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, WIDGET_HOTBAR, x + 1, y, 161, 0, 21, 22, 182, 22);
    }
    private static void renderConnectedSlot(DrawContext context, int x, int y) {
        context.drawTexture(RenderPipelines.GUI_TEXTURED, WIDGET_HOTBAR, x, y, 0, 0, 60, 22, 182, 22);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, WIDGET_HOTBAR, x + 1, y, 121, 0, 61, 22, 182, 22);
    }

    private static void renderItem(DrawContext context, ItemStack stack, int x, int y) {
        String countText = "";
        int count = stack.getCount();
        if (count > 1) countText = String.valueOf(count);

        context.drawItem(stack, x, y);
        context.drawStackOverlay(client.textRenderer, stack, x, y, countText);
    }
}
