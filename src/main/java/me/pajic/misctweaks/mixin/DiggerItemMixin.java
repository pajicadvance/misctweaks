package me.pajic.misctweaks.mixin;

//? if 1.20.1 {

/*import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.misctweaks.MiscTweaks;
import me.pajic.misctweaks.ModTags;
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
        if (MiscTweaks.CONFIG.fasterObsidianMining.get() && state.is(ModTags.OBSIDIAN_LIKE)) {
            return original * MiscTweaks.CONFIG.obsidianMiningSpeedMultiplier.get();
        }
        return original;
    }
}
*///?}
