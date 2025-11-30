package me.pajic.misctweaks.mixin;

//? if 1.20.1 {

/*import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.pajic.misctweaks.MiscTweaks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Consumer;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @WrapWithCondition(
            method = "tryAddSoulSpeed",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V"
            )
    )
    private boolean preventEquipmentDamage(ItemStack instance, int i, LivingEntity livingEntity, Consumer consumer) {
        return !MiscTweaks.CONFIG.soulSpeedNoDamage.get();
    }
}
*///?}
