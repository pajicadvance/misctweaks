package me.pajic.misctweaks.mixin;

//? if > 1.20.1 {

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.pajic.misctweaks.MiscTweaks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.effects./*? if >= 1.21.10 {*/ChangeItemDamage/*?} else {*//*DamageItem*//*?}*/;
import net.minecraft.world.item.enchantment.effects.EnchantmentLocationBasedEffect;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

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
		if (instance instanceof /*? if >= 1.21.10 {*/ChangeItemDamage/*?} else {*//*DamageItem*//*?}*/) {
			Identifier enchantment = serverLevel.registryAccess()./*? if >= 1.21.10 {*/lookupOrThrow/*?} else {*//*registryOrThrow*//*?}*/(Registries.ENCHANTMENT).getKey((Enchantment) (Object) this);
			return (!MiscTweaks.CONFIG.soulSpeedNoDamage.get() || Enchantments.SOUL_SPEED./*? if > 1.21.10 {*/identifier()/*?} else {*//*location()*//*?}*/ != enchantment) &&
					(!MiscTweaks.CONFIG.thornsNoDamage.get() || Enchantments.THORNS./*? if > 1.21.10 {*/identifier()/*?} else {*//*location()*//*?}*/ != enchantment);
		}
		return true;
	}
}
//?}
