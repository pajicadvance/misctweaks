package me.pajic.misctweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.misctweaks.Main;
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
        return Main.CONFIG.fasterObsidianMining.get() && state.is(Main.OBSIDIAN_LIKE) ?
                Main.CONFIG.obsidianMiningSpeedMultiplier.get() * (float) original : original;
    }
}
