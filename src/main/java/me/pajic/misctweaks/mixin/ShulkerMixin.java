package me.pajic.misctweaks.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.pajic.misctweaks.Main;
import net.minecraft.world.entity.monster.Shulker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Shulker.class)
public class ShulkerMixin {

    @WrapWithCondition(
            //? if <= 1.21.1
            method = "hurt",
            //? if 1.21.4
            /*method = "hurtServer",*/
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/monster/Shulker;hitByShulkerBullet()V"
            )
    )
    private boolean preventDuplication(Shulker instance) {
        return !Main.CONFIG.preventShulkerDuplication();
    }
}
