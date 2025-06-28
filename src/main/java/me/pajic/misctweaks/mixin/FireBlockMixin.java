package me.pajic.misctweaks.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.misctweaks.Main;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireBlock.class)
public class FireBlockMixin {

    @Inject(
            method = "bootStrap",
            at = @At("TAIL")
    )
    private static void setCobwebFlammable(CallbackInfo ci, @Local FireBlock fireBlock) {
        if (Main.CONFIG.flammableCobweb.get()) fireBlock.setFlammable(Blocks.COBWEB, 500, 60);
    }
}
