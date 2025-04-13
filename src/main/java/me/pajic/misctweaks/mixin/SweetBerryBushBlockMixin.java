package me.pajic.misctweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.pajic.misctweaks.Main;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//? if >= 1.21.1
import net.minecraft.tags.ItemTags;
//? if 1.21.4
/*import net.minecraft.server.level.ServerLevel;*/

@Mixin(SweetBerryBushBlock.class)
public class SweetBerryBushBlockMixin {

    @WrapOperation(
            method = "entityInside",
            at = @At(
                    value = "INVOKE",
                    //? if <= 1.21.1
                    target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"
                    //? if 1.21.4
                    /*target = "Lnet/minecraft/world/entity/Entity;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z"*/
            )
    )
    //? if <= 1.21.1
    private boolean preventDamageIfSneakingOrWearingLegArmor(Entity instance, DamageSource source, float amount, Operation<Boolean> original) {
    //? if 1.21.4
    /*private boolean preventDamageIfSneakingOrWearingLegArmor(Entity instance, ServerLevel serverLevel, DamageSource source, float amount, Operation<Boolean> original) {*/
        if (instance instanceof Player p) {
            if (
                    (Main.CONFIG.sneakingPreventsBerryBushDamage() && p.isShiftKeyDown()) ||
                    (Main.CONFIG.legArmorPreventsBerryBushDamage() && p.getItemBySlot(EquipmentSlot.LEGS).is(
                            //? if >= 1.21.1
                            ItemTags.LEG_ARMOR
                            //? if < 1.21.1
                            /*Main.LEG_ARMOR*/
                    ))
            ) {
                return false;
            }
        }
        //? if <= 1.21.1
        return original.call(instance, source, amount);
        //? if 1.21.4
        /*return original.call(instance, serverLevel, source, amount);*/
    }
}
