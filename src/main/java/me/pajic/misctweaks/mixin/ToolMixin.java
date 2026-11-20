package me.pajic.misctweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.misctweaks.MiscTweaks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
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
		return MiscTweaks.CONFIG.fasterObsidianMining.get() && MiscTweaks.CONFIG.obsidianBlocks.get().stream().anyMatch(id -> {
            boolean isTag = id.startsWith("#");
            Identifier parsed = Identifier.tryParse(isTag ? id.substring(1) : id);
            if (parsed == null) return false;
            if (isTag) return state.is(TagKey.create(Registries.BLOCK, parsed));
            else return state.is(ResourceKey.create(Registries.BLOCK, parsed));
        }) ? MiscTweaks.CONFIG.obsidianMiningSpeedMultiplier.get() * (float) original : original;
	}
}
