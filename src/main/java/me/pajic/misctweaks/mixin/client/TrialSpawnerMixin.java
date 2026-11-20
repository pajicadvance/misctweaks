package me.pajic.misctweaks.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.pajic.misctweaks.MiscTweaksClient;
import me.pajic.misctweaks.util.ModClientUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TrialSpawner.class)
public class TrialSpawnerMixin {

	@WrapWithCondition(
			method = "tickClient",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/Level;playLocalSound(Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V"
			)
	)
	private boolean limitAmbientSoundRange(
			Level level,
			BlockPos pos,
			SoundEvent sound,
			SoundSource source,
			float volume,
			float pitch,
			boolean distanceDelay
	) {
		return !MiscTweaksClient.CONFIG.limitAmbientSoundRange.get() ||
				ModClientUtil.isInSoundRange(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 16);
	}
}
