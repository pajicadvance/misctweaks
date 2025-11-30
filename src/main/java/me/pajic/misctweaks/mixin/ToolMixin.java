package me.pajic.misctweaks.mixin;

//? if > 1.20.1 {

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.misctweaks.MiscTweaks;
import me.pajic.misctweaks.ModTags;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Tool.class)
public class ToolMixin {

	@ModifyExpressionValue(
			method = "getMiningSpeed",
			at = @At(
					value = "INVOKE",
					target = "Ljava/util/Optional;get()Ljava/lang/Object;"
			)
	)
	private <T> Object modifyMiningSpeed(T original, @Local(argsOnly = true) BlockState state) {
		return MiscTweaks.CONFIG.fasterObsidianMining.get() && state.is(ModTags.OBSIDIAN_LIKE) ?
				MiscTweaks.CONFIG.obsidianMiningSpeedMultiplier.get() * (float) original : original;
	}
}
//?}
