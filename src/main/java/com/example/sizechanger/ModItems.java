package com.example.sizechanger;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

/**
 * Holds mod item definitions and performs registration.  The class is
 * intentionally empty aside from static initialisers; calling
 * {@link #register()} forces the class to load and register items.
 */
public class ModItems {
    /** The single item provided by this mod: the Size Tuner. */
    public static final Item SIZE_TUNER = Registry.register(
            Registries.ITEM,
            SizechangerMod.id("size_tuner"),
            new SizeTunerItem(new Item.Settings().maxCount(1))
    );

    /**
     * Trigger class initialisation.  Calling this in the mod
     * initialiser ensures that the static fields are initialised and the
     * items are registered.
     */
    public static void register() {
        // no-op – the static field initialisers perform registration
    }
}