package com.example.sizechanger;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {
    public static final Item SIZE_TUNER = Registry.register(
            Registries.ITEM,
            SizechangerMod.id("size_tuner"),
            new SizeTunerItem(new Item.Settings().maxCount(1))
    );

    public static void register() { /* no-op, triggers class load */ }
}
