package net.paxquinn.hotbar_utils.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
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
        SubCategoryBuilder backSlot1 = entryBuilder.startSubCategory(Text.translatable("config.hotbar_utils.subcategory.slot_41"));
        backSlot1.add( entryBuilder.
                startIntSlider(Text.translatable("config.hotbar_utils.slot.overlay"), config.backSlot1.overlay, 0, 12)
                .setTooltip(Text.translatable("config.hotbar_utils.slot.overlay.tooltip"))
                .setSaveConsumer(value -> config.backSlot1.overlay = value)
                .build());
        backSlot1.add( entryBuilder.
                startIntField(Text.translatable("config.hotbar_utils.slot.id"), config.backSlot1.id)
                .setTooltip(Text.translatable("config.hotbar_utils.slot.id.tooltip"))
                .setSaveConsumer(value -> config.backSlot1.id = value)
                .build());
        backSlot1.add( entryBuilder.
                startEnumSelector(Text.translatable("config.hotbar_utils.slot.type"), SlotType.class, config.backSlot1.type)
                .setTooltip(Text.translatable("config.hotbar_utils.slot.type.tooltip"))
                .setSaveConsumer(value -> config.backSlot1.type = value)
                .build());
        general.addEntry(backSlot1.build());
        // Back Slot 2 Sub-Category
        SubCategoryBuilder backSlot2 = entryBuilder.startSubCategory(Text.translatable("config.hotbar_utils.subcategory.slot_42"));
        backSlot2.add( entryBuilder.
                startIntSlider(Text.translatable("config.hotbar_utils.slot.overlay"), config.backSlot2.overlay, 0, 12)
                .setTooltip(Text.translatable("config.hotbar_utils.slot.overlay.tooltip"))
                .setSaveConsumer(value -> config.backSlot2.overlay = value)
                .build());
        backSlot2.add( entryBuilder.
                startIntField(Text.translatable("config.hotbar_utils.slot.id"), config.backSlot2.id)
                .setTooltip(Text.translatable("config.hotbar_utils.slot.id.tooltip"))
                .setSaveConsumer(value -> config.backSlot2.id = value)
                .build());
        backSlot2.add( entryBuilder.
                startEnumSelector(Text.translatable("config.hotbar_utils.slot.type"), SlotType.class, config.backSlot2.type)
                .setTooltip(Text.translatable("config.hotbar_utils.slot.type.tooltip"))
                .setSaveConsumer(value -> config.backSlot2.type = value)
                .build());
        general.addEntry(backSlot2.build());
        // Back Slot 3 Sub-Category
        SubCategoryBuilder backSlot3 = entryBuilder.startSubCategory(Text.translatable("config.hotbar_utils.subcategory.slot_43"));
        backSlot3.add( entryBuilder.
                startIntSlider(Text.translatable("config.hotbar_utils.slot.overlay"), config.backSlot3.overlay, 0, 12)
                .setTooltip(Text.translatable("config.hotbar_utils.slot.overlay.tooltip"))
                .setSaveConsumer(value -> config.backSlot3.overlay = value)
                .build());
        backSlot3.add( entryBuilder.
                startIntField(Text.translatable("config.hotbar_utils.slot.id"), config.backSlot3.id)
                .setTooltip(Text.translatable("config.hotbar_utils.slot.id.tooltip"))
                .setSaveConsumer(value -> config.backSlot3.id = value)
                .build());
        backSlot3.add( entryBuilder.
                startEnumSelector(Text.translatable("config.hotbar_utils.slot.type"), SlotType.class, config.backSlot3.type)
                .setTooltip(Text.translatable("config.hotbar_utils.slot.type.tooltip"))
                .setSaveConsumer(value -> config.backSlot3.type = value)
                .build());
        general.addEntry(backSlot3.build());

        // Quick Elytra Sub-Category
        SubCategoryBuilder elytra = entryBuilder.startSubCategory(Text.translatable("config.hotbar_utils.subcategory.quick_elytra"));
        elytra.add( entryBuilder.
                startTextField(Text.translatable("config.hotbar_utils.elytra.chestplate_type"), config.preferredChestplateType)
                .setTooltip(Text.translatable("config.hotbar_utils.elytra.chestplate_type.tooltip"))
                .setSaveConsumer(value -> config.preferredChestplateType = value)
                .build());
        elytra.add( entryBuilder.
                startTextField(Text.translatable("config.hotbar_utils.elytra.chestplate_name"), config.preferredChestplateName)
                .setTooltip(Text.translatable("config.hotbar_utils.elytra.chestplate_name.tooltip"))
                .setSaveConsumer(value -> config.preferredChestplateName = value)
                .build());
        elytra.add( entryBuilder.
                startTextField(Text.translatable("config.hotbar_utils.elytra.elytra_name"), config.preferredElytraName)
                .setTooltip(Text.translatable("config.hotbar_utils.elytra.elytra_name.tooltip"))
                .setSaveConsumer(value -> config.preferredElytraName = value)
                .build());
        general.addEntry(elytra.build());

        // HUD Sub-Category
        SubCategoryBuilder hud = entryBuilder.startSubCategory(Text.translatable("config.hotbar_utils.subcategory.hud"));
        hud.add( entryBuilder.startBooleanToggle( Text.translatable("config.hotbar_utils.hud_enabled"), config.hudEnabled )
                .setSaveConsumer(value -> config.hudEnabled = value)
                .build());
        hud.add( entryBuilder.startBooleanToggle( Text.translatable("config.hotbar_utils.render_unbound"), config.renderUnbound )
                .setSaveConsumer(value -> config.renderUnbound = value)
                .setTooltip(Text.translatable("config.hotbar_utils.render_unbound.tooltip"))
                .build());
        hud.add( entryBuilder.startBooleanToggle( Text.translatable("config.hotbar_utils.offhand_texture"), config.hudOffhandTexture )
                .setSaveConsumer(value -> config.hudOffhandTexture = value)
                .setTooltip(Text.translatable("config.hotbar_utils.offhand_texture.tooltip"))
                .build());
        hud.add( entryBuilder.startIntSlider( Text.translatable("config.hotbar_utils.hud_offset"), config.hudOffset, 0, 64 )
                .setSaveConsumer(value -> config.hudOffset = value)
                .setTooltip(Text.translatable("config.hotbar_utils.hud_offset.tooltip"))
                .build());
        hud.add( entryBuilder.startIntSlider( Text.translatable("config.hotbar_utils.hud_spacing"), config.hudSpacing, 0, 12 )
                .setSaveConsumer(value -> config.hudSpacing = value)
                .setTooltip(Text.translatable("config.hotbar_utils.hud_spacing.tooltip"))
                .build());
        hud.add( entryBuilder.startEnumSelector( Text.translatable("config.hotbar_utils.hud_anchor"), HudAnchor.class, config.hudAnchor )
                .setDefaultValue(HudAnchor.RIGHT)
                .setSaveConsumer(value -> config.hudAnchor = value)
                .setTooltip(Text.translatable("config.hotbar_utils.hud_anchor.tooltip"))
                .build());
        hud.add( entryBuilder.startEnumSelector( Text.translatable("config.hotbar_utils.hud_anchor_type"), HudAnchorType.class, config.hudAnchorType )
                .setDefaultValue(HudAnchorType.STATIC)
                .setSaveConsumer(value -> config.hudAnchorType = value)
                .setTooltip(Text.translatable("config.hotbar_utils.hud_anchor_type.tooltip"))
                .build());
        general.addEntry(hud.build());

        return builder.build();
    }
}



//        ├─ ├─ ├─