package com.example.sizechanger.mixin;

import com.example.sizechanger.SizeState;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Redirect(
        method = "getDimensions",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getBaseDimensions(Lnet/minecraft/entity/EntityPose;)Lnet/minecraft/entity/EntityDimensions;")
    )
    private EntityDimensions sizechanger$scaleDimensions(PlayerEntity self, EntityPose pose) {
        EntityDimensions base = self.getBaseDimensions(pose);
        float scale = SizeState.get(self);
        return base.scaled(scale);
    }
}
