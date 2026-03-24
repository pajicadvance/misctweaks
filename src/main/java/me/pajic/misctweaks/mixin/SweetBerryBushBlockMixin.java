package me.pajic.misctweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.pajic.misctweaks.MiscTweaks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SweetBerryBushBlock.class)
public class SweetBerryBushBlockMixin {

    @WrapOperation(
            method = "entityInside",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;hurtServer(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/damagesource/DamageSource;F)Z"
            )
    )
    private boolean preventDamageIfSneakingOrWearingLegArmor(Entity instance, ServerLevel serverLevel, DamageSource source, float amount, Operation<Boolean> original) {
        if (instance instanceof Player p) {
            if ((MiscTweaks.CONFIG.sneakingPreventsBerryBushDamage.get() && p.isShiftKeyDown())) return false;
        }
		if (instance instanceof LivingEntity l && MiscTweaks.CONFIG.armorPreventsBerryBushDamage.get()) {
			if (l.getItemBySlot(EquipmentSlot.LEGS).is(ItemTags.LEG_ARMOR) || l.hasItemInSlot(EquipmentSlot.BODY)) return false;
		}
        return original.call(instance, serverLevel, source, amount);
    }

	@WrapOperation(
			method = "entityInside",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/entity/Entity;makeStuckInBlock(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/phys/Vec3;)V"
			)
	)
	private void reduceSlowEffectWhenWearingLegArmor(Entity instance, BlockState state, Vec3 motionMultiplier, Operation<Void> original) {
		if (MiscTweaks.CONFIG.armorReducesBerryBushSlow.get() && instance instanceof LivingEntity l) {
			if (l.getItemBySlot(EquipmentSlot.LEGS).is(ItemTags.LEG_ARMOR) || l.hasItemInSlot(EquipmentSlot.BODY)) {
				double d = MiscTweaks.CONFIG.berryBushSlowReduction.get();
				if (d == 2.2) return;
				original.call(instance, state, motionMultiplier.multiply(d, d, d));
				return;
			}
		}
		original.call(instance, state, motionMultiplier);
	}
}
