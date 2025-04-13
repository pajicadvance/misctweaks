package me.pajic.misctweaks.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.pajic.misctweaks.Main;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.effects.EnchantmentLocationBasedEffect;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import net.minecraft.world.item.enchantment.effects.DamageItem;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    @WrapWithCondition(
            method = "runLocationChangedEffects",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/effects/EnchantmentLocationBasedEffect;onChangedBlock(Lnet/minecraft/server/level/ServerLevel;ILnet/minecraft/world/item/enchantment/EnchantedItemInUse;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Z)V"
            )
    )
    private boolean preventEquipmentDamage(EnchantmentLocationBasedEffect instance, ServerLevel serverLevel, int i, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3, boolean b) {
        if (instance instanceof DamageItem) {
            ResourceLocation enchantment = serverLevel.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getKey((Enchantment) (Object) this);
            return (!Main.CONFIG.soulSpeedNoDamage() || Enchantments.SOUL_SPEED.location() != enchantment) &&
                    (!Main.CONFIG.thornsNoDamage() || Enchantments.THORNS.location() != enchantment);
        }
        return true;
    }
}
