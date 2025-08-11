package com.example.sizechanger;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class SizeState {
    public static final String NBT_KEY = "sizechanger_scale";
    public static final float MIN = 0.25f;
    public static final float MAX = 4.0f;

    public static float get(PlayerEntity p) {
        NbtCompound nbt = p.getPersistentData();
        return nbt.contains(NBT_KEY) ? nbt.getFloat(NBT_KEY) : 1.0f;
    }

    public static void set(PlayerEntity p, float value) {
        float clamped = Math.max(MIN, Math.min(MAX, value));
        p.getPersistentData().putFloat(NBT_KEY, clamped);
        p.calculateDimensions();
    }

    public static int cost(float from, float to) {
        // Cost in slimeballs: 1 slimeball per 0.25 difference
        int diffSteps = (int) Math.ceil(Math.abs(to - from) * 4.0f);
        return Math.max(1, diffSteps);
    }
}
