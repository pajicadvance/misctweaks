package me.pajic.misctweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.misctweaks.Main;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DiggerItem.class)
public class DiggerItemMixin {

    @ModifyReturnValue(
            method = "getDestroySpeed",
            at = @At("RETURN")
    )
    private float modifyDestroySpeed(float original, @Local(argsOnly = true) BlockState state) {
        if (Main.CONFIG.fasterObsidianMining.get() && state.is(Main.OBSIDIAN_LIKE)) {
            return original * Main.CONFIG.obsidianMiningSpeedMultiplier.get();
        }
        return original;
    }
}
