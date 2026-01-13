package net.paxquinn.hotbar_utils.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class HotbarUtilsConfigScreen {
    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("config.hotbar_utils.title"));

        builder.setSavingRunnable(ConfigManager::save);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        HotbarUtilsConfig config = ConfigManager.get();

        //////////////////
        //    GENERAL   //
        //////////////////
        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.hotbar_utils.category.general"));
        general.addEntry( entryBuilder.startBooleanToggle( Text.translatable("config.hotbar_utils.enabled"), config.enabled )
                .setSaveConsumer(value -> config.enabled = value)
                .build());
        // Back Slot 1 Sub-Category
//        general.addEntry( entryBuilder.startTextDescription(Text.literal(" ")).build()); // WHITESPACE
        general.addEntry( entryBuilder.startTextDescription(Text.translatable("config.hotbar_utils.back_slot_description")).build());
        general.addEntry( entryBuilder.startTextDescription(Text.translatable("config.hotbar_utils.subcategory.slot_41")).build());
        general.addEntry( entryBuilder.
                startIntField(Text.translatable("config.hotbar_utils.slot.id"), config.backSlot1.id)
                .setTooltip(Text.translatable("config.hotbar_utils.slot.id.tooltip"))
                .setSaveConsumer(value -> config.backSlot1.id = value)
                .build());
        general.addEntry( entryBuilder.
                startEnumSelector(Text.translatable("config.hotbar_utils.slot.type"), SlotType.class, config.backSlot1.type)
                .setTooltip(Text.translatable("config.hotbar_utils.slot.type.tooltip"))
                .setSaveConsumer(value -> config.backSlot1.type = value)
                .build());
        // Back Slot 2 Sub-Category
        general.addEntry( entryBuilder.startTextDescription(Text.translatable("config.hotbar_utils.subcategory.slot_42")).build());
        general.addEntry( entryBuilder.
                startIntField(Text.translatable("config.hotbar_utils.slot.id"), config.backSlot2.id)
                .setTooltip(Text.translatable("config.hotbar_utils.slot.id.tooltip"))
                .setSaveConsumer(value -> config.backSlot2.id = value)
                .build());
        general.addEntry( entryBuilder.
                startEnumSelector(Text.translatable("config.hotbar_utils.slot.type"), SlotType.class, config.backSlot2.type)
                .setTooltip(Text.translatable("config.hotbar_utils.slot.type.tooltip"))
                .setSaveConsumer(value -> config.backSlot2.type = value)
                .build());
        // Back Slot 3 Sub-Category
        general.addEntry( entryBuilder.startTextDescription(Text.translatable("config.hotbar_utils.subcategory.slot_43")).build());
        general.addEntry( entryBuilder.
                startIntField(Text.translatable("config.hotbar_utils.slot.id"), config.backSlot3.id)
                .setTooltip(Text.translatable("config.hotbar_utils.slot.id.tooltip"))
                .setSaveConsumer(value -> config.backSlot3.id = value)
                .build());
        general.addEntry( entryBuilder.
                startEnumSelector(Text.translatable("config.hotbar_utils.slot.type"), SlotType.class, config.backSlot3.type)
                .setTooltip(Text.translatable("config.hotbar_utils.slot.type.tooltip"))
                .setSaveConsumer(value -> config.backSlot3.type = value)
                .build());

        //////////////////
        //      HUD     //
        //////////////////
        ConfigCategory hud = builder.getOrCreateCategory(Text.translatable("config.hotbar_utils.category.hud"));
        hud.addEntry( entryBuilder.startBooleanToggle( Text.translatable("config.hotbar_utils.hud_enabled"), config.hudEnabled )
                .setSaveConsumer(value -> config.hudEnabled = value)
                .build());
        hud.addEntry( entryBuilder.startBooleanToggle( Text.translatable("config.hotbar_utils.render_unbound"), config.renderUnbound )
                .setSaveConsumer(value -> config.renderUnbound = value)
                .setTooltip(Text.translatable("config.hotbar_utils.render_unbound.tooltip"))
                .build());
        hud.addEntry( entryBuilder.startBooleanToggle( Text.translatable("config.hotbar_utils.offhand_texture"), config.hudOffhandTexture )
                .setSaveConsumer(value -> config.hudOffhandTexture = value)
                .setTooltip(Text.translatable("config.hotbar_utils.offhand_texture.tooltip"))
                .build());
        hud.addEntry( entryBuilder.startIntSlider( Text.translatable("config.hotbar_utils.hud_offset"), config.hudOffset, 0, 64 )
                .setSaveConsumer(value -> config.hudOffset = value)
                .setTooltip(Text.translatable("config.hotbar_utils.hud_offset.tooltip"))
                .build());
        hud.addEntry( entryBuilder.startIntSlider( Text.translatable("config.hotbar_utils.hud_spacing"), config.hudSpacing, 0, 12 )
                .setSaveConsumer(value -> config.hudSpacing = value)
                .setTooltip(Text.translatable("config.hotbar_utils.hud_spacing.tooltip"))
                .build());
        hud.addEntry( entryBuilder.startEnumSelector( Text.translatable("config.hotbar_utils.hud_anchor"), HudAnchor.class, config.hudAnchor )
                .setDefaultValue(HudAnchor.RIGHT)
                .setSaveConsumer(value -> config.hudAnchor = value)
                .setTooltip(Text.translatable("config.hotbar_utils.hud_anchor.tooltip"))
                .build());
        hud.addEntry( entryBuilder.startEnumSelector( Text.translatable("config.hotbar_utils.hud_anchor_type"), HudAnchorType.class, config.hudAnchorType )
                .setDefaultValue(HudAnchorType.STATIC)
                .setSaveConsumer(value -> config.hudAnchorType = value)
                .setTooltip(Text.translatable("config.hotbar_utils.hud_anchor_type.tooltip"))
                .build());

        return builder.build();
    }
}
