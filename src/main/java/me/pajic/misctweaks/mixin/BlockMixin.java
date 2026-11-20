package me.pajic.misctweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.misctweaks.MiscTweaks;
import me.pajic.misctweaks.util.ModUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Block.class)
public class BlockMixin {

	@ModifyExpressionValue(
			method = "popResource(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/util/Mth;nextDouble(Lnet/minecraft/util/RandomSource;DD)D"
			)
	)
	private static double dropAtBlockCenter(double original) {
		return ModUtil.blockBreaker != null && MiscTweaks.CONFIG.stableBlockDrops.alwaysSpawnDropInBlockCenter.get() ? 0 : original;
	}

	@WrapMethod(method = "popResource(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;)V")
	private static void markBlockDrop(Level level, BlockPos pos, ItemStack stack, Operation<Void> original) {
		boolean previous = ModUtil.poppingBlockResource;
		ModUtil.poppingBlockResource = true;
		try {
			original.call(level, pos, stack);
		} finally {
			ModUtil.poppingBlockResource = previous;
		}
	}
}
