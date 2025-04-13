package me.pajic.misctweaks.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.pajic.misctweaks.Main;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ThornsEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Consumer;

@Mixin(ThornsEnchantment.class)
public class ThornsEnchantmentMixin {

    @WrapWithCondition(
            method = "doPostHurt",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;hurtAndBreak(ILnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V"
            )
    )
    private boolean preventEquipmentDamage(ItemStack instance, int i, LivingEntity livingEntity, Consumer consumer) {
        return !Main.CONFIG.thornsNoDamage();
    }
}
