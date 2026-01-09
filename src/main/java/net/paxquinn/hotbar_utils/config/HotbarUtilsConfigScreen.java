package net.paxquinn.hotbar_utils.config;

import me.shedaniel.clothconfig2.api.AbstractConfigEntry;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.paxquinn.hotbar_utils.event.KeyInputHandler;

public class HotbarUtilsConfigScreen {
    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("config.hotbar_utils.title"));

        builder.setSavingRunnable(ConfigManager::save);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        //////////////////
        //    GENERAL   //
        //////////////////
        ConfigCategory general = builder.getOrCreateCategory(Text.translatable("config.hotbar_utils.category.general"));
        general.addEntry( entryBuilder.startBooleanToggle( Text.translatable("config.hotbar_utils.enabled"), HotbarUtilsConfig.enabled )
                .setSaveConsumer(value -> HotbarUtilsConfig.enabled = value)
                .build());
        // Back Slot 1 Sub-Category
//        general.addEntry( entryBuilder.startTextDescription(Text.literal(" ")).build()); // WHITESPACE
        general.addEntry( entryBuilder.startTextDescription(Text.translatable("config.hotbar_utils.back_slot_description")).build());
        general.addEntry( entryBuilder.startTextDescription(Text.translatable("config.hotbar_utils.subcategory.slot_41")).build());
        general.addEntry( entryBuilder.
                startIntField(Text.translatable("config.hotbar_utils.slot.id"), HotbarUtilsConfig.backSlot1)
                .setTooltip(Text.translatable("config.hotbar_utils.slot.id.tooltip"))
                .setSaveConsumer(value -> HotbarUtilsConfig.backSlot1 = value)
                .build());
        general.addEntry( entryBuilder.
                startEnumSelector(Text.translatable("config.hotbar_utils.slot.type"), SlotType.class, HotbarUtilsConfig.backSlot1Type)
                .setTooltip(Text.translatable("config.hotbar_utils.slot.type.tooltip"))
                .setSaveConsumer(value -> HotbarUtilsConfig.backSlot1Type = value)
                .build());
        // Back Slot 2 Sub-Category
        general.addEntry( entryBuilder.startTextDescription(Text.translatable("config.hotbar_utils.subcategory.slot_42")).build());
        general.addEntry( entryBuilder.
                startIntField(Text.translatable("config.hotbar_utils.slot.id"), HotbarUtilsConfig.backSlot2)
                .setTooltip(Text.translatable("config.hotbar_utils.slot.id.tooltip"))
                .setSaveConsumer(value -> HotbarUtilsConfig.backSlot2 = value)
                .build());
        general.addEntry( entryBuilder.
                startEnumSelector(Text.translatable("config.hotbar_utils.slot.type"), SlotType.class, HotbarUtilsConfig.backSlot2Type)
                .setTooltip(Text.translatable("config.hotbar_utils.slot.type.tooltip"))
                .setSaveConsumer(value -> HotbarUtilsConfig.backSlot2Type = value)
                .build());
        // Back Slot 3 Sub-Category
        general.addEntry( entryBuilder.startTextDescription(Text.translatable("config.hotbar_utils.subcategory.slot_43")).build());
        general.addEntry( entryBuilder.
                startIntField(Text.translatable("config.hotbar_utils.slot.id"), HotbarUtilsConfig.backSlot3)
                .setTooltip(Text.translatable("config.hotbar_utils.slot.id.tooltip"))
                .setSaveConsumer(value -> HotbarUtilsConfig.backSlot3 = value)
                .build());
        general.addEntry( entryBuilder.
                startEnumSelector(Text.translatable("config.hotbar_utils.slot.type"), SlotType.class, HotbarUtilsConfig.backSlot3Type)
                .setTooltip(Text.translatable("config.hotbar_utils.slot.type.tooltip"))
                .setSaveConsumer(value -> HotbarUtilsConfig.backSlot3Type = value)
                .build());


        //////////////////
        //   KEYBINDS   //
        //////////////////
        ConfigCategory keybinds = builder.getOrCreateCategory(Text.translatable("config.hotbar_utils.category.keybind"));
        keybinds.addEntry( entryBuilder
                .startKeyCodeField(Text.translatable("key.hotbar_utils.settings"), KeyInputHandler.keySettings.getDefaultKey())
                .setKeySaveConsumer(key -> { KeyInputHandler.keySettings.setBoundKey(key); KeyInputHandler.keySettings.setPressed(false); })
                .build());
        keybinds.addEntry( entryBuilder
                .startKeyCodeField(Text.translatable("key.hotbar_utils.slot_41"), KeyInputHandler.keyBackSlot1.getDefaultKey())
                .setKeySaveConsumer(key -> { KeyInputHandler.keyBackSlot1.setBoundKey(key); KeyInputHandler.keyBackSlot1.setPressed(false); })
                .build());
        keybinds.addEntry( entryBuilder
                .startKeyCodeField(Text.translatable("key.hotbar_utils.slot_42"), KeyInputHandler.keyBackSlot2.getDefaultKey())
                .setKeySaveConsumer(key -> { KeyInputHandler.keyBackSlot2.setBoundKey(key); KeyInputHandler.keyBackSlot2.setPressed(false); })
                .build());
        keybinds.addEntry( entryBuilder
                .startKeyCodeField(Text.translatable("key.hotbar_utils.slot_43"), KeyInputHandler.keyBackSlot3.getDefaultKey())
                .setKeySaveConsumer(key -> { KeyInputHandler.keyBackSlot3.setBoundKey(key); KeyInputHandler.keyBackSlot3.setPressed(false); })
                .build());


        //////////////////
        //      HUD     //
        //////////////////
        ConfigCategory hud = builder.getOrCreateCategory(Text.translatable("config.hotbar_utils.category.hud"));
        hud.addEntry( entryBuilder.startBooleanToggle( Text.translatable("config.hotbar_utils.hud_enabled"), HotbarUtilsConfig.hudEnabled )
                .setSaveConsumer(value -> HotbarUtilsConfig.hudEnabled = value)
                .build());
        hud.addEntry( entryBuilder.startBooleanToggle( Text.translatable("config.hotbar_utils.offhand_texture"), HotbarUtilsConfig.hudOffhandTexture )
                .setSaveConsumer(value -> HotbarUtilsConfig.hudOffhandTexture = value)
                .setTooltip(Text.translatable("config.hotbar_utils.offhand_texture.tooltip"))
                .build());
        hud.addEntry( entryBuilder.startIntSlider( Text.translatable("config.hotbar_utils.hud_offset"), HotbarUtilsConfig.hudOffset, 0, 64 )
                .setSaveConsumer(value -> HotbarUtilsConfig.hudOffset = value)
                .setTooltip(Text.translatable("config.hotbar_utils.hud_offset.tooltip"))
                .build());
        hud.addEntry( entryBuilder.startIntSlider( Text.translatable("config.hotbar_utils.hud_spacing"), HotbarUtilsConfig.hudSpacing, 0, 12 )
                .setSaveConsumer(value -> HotbarUtilsConfig.hudSpacing = value)
                .setTooltip(Text.translatable("config.hotbar_utils.hud_spacing.tooltip"))
                .build());
        hud.addEntry( entryBuilder.startEnumSelector( Text.translatable("config.hotbar_utils.hud_anchor"), HudAnchor.class, HotbarUtilsConfig.hudAnchor )
                .setDefaultValue(HudAnchor.RIGHT)
                .setSaveConsumer(value -> HotbarUtilsConfig.hudAnchor = value)
                .setTooltip(Text.translatable("config.hotbar_utils.hud_anchor.tooltip"))
                .build());
        hud.addEntry( entryBuilder.startEnumSelector( Text.translatable("config.hotbar_utils.hud_anchor_type"), HudAnchorType.class, HotbarUtilsConfig.hudAnchorType )
                .setDefaultValue(HudAnchorType.STATIC)
                .setSaveConsumer(value -> HotbarUtilsConfig.hudAnchorType = value)
                .setTooltip(Text.translatable("config.hotbar_utils.hud_anchor_type.tooltip"))
                .build());

        return builder.build();
    }
}
