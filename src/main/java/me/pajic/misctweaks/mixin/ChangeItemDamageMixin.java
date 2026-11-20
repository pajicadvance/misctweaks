package me.pajic.misctweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.misctweaks.util.ModUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;

//~ if <26.1 'ChangeItemDamage' -> 'DamageItem'
import net.minecraft.world.item.enchantment.effects.ChangeItemDamage;

//~ if <26.1 'ChangeItemDamage' -> 'DamageItem'
@Mixin(ChangeItemDamage.class)
public class ChangeItemDamageMixin {

	@WrapMethod(method = "apply")
	private void suppressItemDamage(
            ServerLevel serverLevel,
            int enchantmentLevel,
            EnchantedItemInUse item,
            Entity entity,
            Vec3 position,
            Operation<Void> original
    ) {
		if (!ModUtil.suppressEnchantmentItemDamage) original.call(serverLevel, enchantmentLevel, item, entity, position);
	}
}
