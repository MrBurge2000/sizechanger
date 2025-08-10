package com.example.sizechanger.mixin;

import com.example.sizechanger.SizeState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Mixin to adjust the player's dimensions based on the stored scale.
 */
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    /**
     * Redirect the call to {@code getBaseDimensions} to apply a scale factor.
     * The dimensions returned from the base method are multiplied by the
     * player's current scale.
     */
    @Redirect(
            method = "getDimensions",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;getBaseDimensions(Lnet/minecraft/entity/EntityPose;)Lnet/minecraft/entity/EntityDimensions;"
            )
    )
    private EntityDimensions scaleDimensions(PlayerEntity self, EntityPose pose) {
        EntityDimensions base = self.getBaseDimensions(pose);
        float scale = SizeState.get(self);
        return base.scaled(scale);
    }
}