package me.pajic.misctweaks.mixin.client;

//? >=26.3 {

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import me.pajic.misctweaks.util.ModClientUtil;
import net.minecraft.client.renderer.FirstPersonHandsAndItemsRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.state.level.FirstPersonHandsAndItemsRenderState;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FirstPersonHandsAndItemsRenderer.class)
public class FirstPersonHandsAndItemsRendererMixin {

	@Inject(
			method = "submitArmWithItem",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/item/ItemStackRenderState;submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;III)V",
					ordinal = 1
			)
	)
	private void lowerShield(
			CallbackInfo ci,
			@Local(argsOnly = true) PlayerRenderState playerState,
			@Local(argsOnly = true) FirstPersonHandsAndItemsRenderState state,
			@Local(argsOnly = true) InteractionHand hand,
			@Local(argsOnly = true) ItemStack itemStack,
			@Local(argsOnly = true) PoseStack poseStack
	) {
        AvatarRenderState state1 = playerState.avatarRenderState;
        if (state1 != null) {
            boolean usingItem = state1.isUsingItem && state.useItemRemainingTicks > 0 && playerState.avatarRenderState.useItemHand == hand;
            ModClientUtil.applyShieldOffset(hand, usingItem, itemStack, poseStack);
        }
	}
}
//?}
