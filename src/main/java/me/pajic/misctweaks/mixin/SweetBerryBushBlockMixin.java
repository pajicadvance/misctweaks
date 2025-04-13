package me.pajic.misctweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.pajic.misctweaks.config.ModServerConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraftforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SweetBerryBushBlock.class)
public class SweetBerryBushBlockMixin {

    @WrapOperation(
            method = "entityInside",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"
            )
    )
    private boolean preventDamageIfSneakingOrWearingLegArmor(Entity instance, DamageSource source, float amount, Operation<Boolean> original) {
        if (instance instanceof Player p) {
            if (
                    (ModServerConfig.sneakingPreventsBerryBushDamage && p.isShiftKeyDown()) ||
                    (ModServerConfig.legArmorPreventsBerryBushDamage && p.getItemBySlot(EquipmentSlot.LEGS).is(Tags.Items.ARMORS_LEGGINGS))
            ) {
                return false;
            }
        }
        return original.call(instance, source, amount);
    }
}
