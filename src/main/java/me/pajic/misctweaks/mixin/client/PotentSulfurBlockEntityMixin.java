package me.pajic.misctweaks.mixin.client;

//? >=26.2 {

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.pajic.misctweaks.MiscTweaksClient;
import me.pajic.misctweaks.util.ModClientUtil;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.PotentSulfurBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PotentSulfurBlockEntity.class)
public class PotentSulfurBlockEntityMixin {

	@WrapWithCondition(
			method = "lambda$static$3",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/level/Level;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V"
			)
	)
	private static boolean limitGeyserSoundRange(
			Level level,
			double x,
			double y,
			double z,
			SoundEvent sound,
			SoundSource source,
			float volume,
			float pitch,
			boolean distanceDelay
	) {
		return !MiscTweaksClient.CONFIG.limitAmbientSoundRange.get() || ModClientUtil.isInSoundRange(x, y, z, 32);
	}
}
//?}
