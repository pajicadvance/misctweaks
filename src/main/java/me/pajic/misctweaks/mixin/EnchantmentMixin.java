package me.pajic.misctweaks.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.pajic.misctweaks.MiscTweaks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.effects.ChangeItemDamage;
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
		if (instance instanceof ChangeItemDamage) {
			Identifier enchantment = serverLevel.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getKey((Enchantment) (Object) this);
			return (!MiscTweaks.CONFIG.soulSpeedNoDamage.get() || Enchantments.SOUL_SPEED.identifier() != enchantment) &&
					(!MiscTweaks.CONFIG.thornsNoDamage.get() || Enchantments.THORNS.identifier() != enchantment);
		}
		return true;
	}
}
