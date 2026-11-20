package me.pajic.misctweaks.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.misctweaks.MiscTweaks;
import me.pajic.misctweaks.util.ModUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.effects.EnchantmentLocationBasedEffect;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

//~ if <26.1 'ChangeItemDamage' -> 'DamageItem'
import net.minecraft.world.item.enchantment.effects.ChangeItemDamage;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

	@WrapWithCondition(
			method = "runLocationChangedEffects",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/item/enchantment/effects/EnchantmentLocationBasedEffect;onChangedBlock(Lnet/minecraft/server/level/ServerLevel;ILnet/minecraft/world/item/enchantment/EnchantedItemInUse;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Z)V"
			)
	)
	private boolean preventEquipmentDamage(
            EnchantmentLocationBasedEffect instance,
            ServerLevel serverLevel,
            int i,
            EnchantedItemInUse enchantedItemInUse,
            Entity entity,
            Vec3 vec3,
            boolean b
    ) {
        //~ if <26.1 'ChangeItemDamage' -> 'DamageItem'
		return !(instance instanceof ChangeItemDamage && MiscTweaks.CONFIG.soulSpeedNoDamage.get() &&
                mt$is(serverLevel, Enchantments.SOUL_SPEED));
	}

	@WrapMethod(method = "doPostAttack(Lnet/minecraft/server/level/ServerLevel;ILnet/minecraft/world/item/enchantment/EnchantedItemInUse;Lnet/minecraft/world/item/enchantment/EnchantmentTarget;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;)V")
	private void preventThornsEquipmentDamage(
            ServerLevel serverLevel,
            int enchantmentLevel,
            EnchantedItemInUse item,
            EnchantmentTarget forTarget,
            Entity victim,
            DamageSource damageSource,
            Operation<Void> original
    ) {
		boolean previous = ModUtil.suppressEnchantmentItemDamage;
		ModUtil.suppressEnchantmentItemDamage = MiscTweaks.CONFIG.thornsNoDamage.get() && mt$is(serverLevel, Enchantments.THORNS);
        original.call(serverLevel, enchantmentLevel, item, forTarget, victim, damageSource);
        ModUtil.suppressEnchantmentItemDamage = previous;
	}

	@Unique
	private boolean mt$is(ServerLevel serverLevel, ResourceKey<Enchantment> key) {
		//~ if <26.1 'lookupOrThrow' -> 'registryOrThrow'
		return serverLevel.registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
                .getResourceKey((Enchantment) (Object) this)
                .filter(key::equals).isPresent();
	}
}
