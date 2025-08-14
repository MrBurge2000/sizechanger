package com.example.sizechanger;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mod entry point.  Registers items, networking and the item group on
 * initialization.  Also exposes a helper for constructing identifiers
 * within this mod's namespace.
 */
public class SizechangerMod implements ModInitializer {
    public static final String MOD_ID = "sizechanger";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    /**
     * Creative tab for the size changer mod.  Displays the Size Tuner.
     */
    public static final ItemGroup ITEM_GROUP = Registry.register(
            Registries.ITEM_GROUP,
            id("sizechanger_group"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.SIZE_TUNER))
                    .displayName(Text.translatable("itemGroup.sizechanger"))
                    .entries((ctx, entries) -> entries.add(ModItems.SIZE_TUNER))
                    .build()
    );

    @Override
    public void onInitialize() {
        ModItems.register();
        ModNetworking.registerServer();
        LOGGER.info("Initializing Size Changer mod");
    }

    /**
     * Construct an identifier within the mod's namespace.
     *
     * @param path the path
     * @return a new identifier
     */
    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}