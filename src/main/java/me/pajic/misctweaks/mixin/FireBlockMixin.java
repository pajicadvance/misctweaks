package me.pajic.misctweaks.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.misctweaks.MiscTweaks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireBlock.class)
public abstract class FireBlockMixin {

	@Inject(
            method = "bootStrap",
            at = @At("TAIL")
    )
    private static void setFlammable(CallbackInfo ci, @Local(name = "fire") FireBlock fire) {
        if (MiscTweaks.CONFIG.flammableCobweb.get()) {
			MiscTweaks.CONFIG.flammableBlocks.get().forEach(entry ->
					((FireBlockInvoker) fire).mt$invokeSetFlammable(
							BuiltInRegistries.BLOCK.getValue(entry.id.get()),
							entry.igniteOdds.get(),
							entry.burnOdds.get()
					)
			);
		}
    }
}
