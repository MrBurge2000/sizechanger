package com.example.sizechanger;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/**
 * The handheld item that opens the size tuning GUI.  It has no durability
 * and does not do anything on the server directly; all logic is handled
 * via network packets.
 */
public class SizeTunerItem extends Item {
    public SizeTunerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            ClientGUI.open(SizeState.get(user));
        }
        return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
    }
}