package me.pajic.misctweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.misctweaks.util.ModUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {

	@Shadow @Final protected ServerPlayer player;

	@WrapMethod(method = "destroyBlock")
	private boolean markPlayerBreakingBlock(BlockPos pos, Operation<Boolean> original) {
		Player previous = ModUtil.blockBreaker;
		ModUtil.blockBreaker = player;
        try {
            return original.call(pos);
        } finally {
            ModUtil.blockBreaker = previous;
        }
	}
}
