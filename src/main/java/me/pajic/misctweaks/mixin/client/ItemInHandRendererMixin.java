package me.pajic.misctweaks.mixin.client;

//? <26.3 {

/*import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import me.pajic.misctweaks.util.ModClientUtil;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

	@Inject(
			method = {"renderArmWithItem", "submitArmWithItem"},
			require = 1,
			at = @At(
					value = "INVOKE",
					//? >=26.1
					target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;I)V",
					//? <26.1
					//target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
					ordinal = 1
			)
	)
	private void lowerShield(
			CallbackInfo ci,
			@Local(argsOnly = true) AbstractClientPlayer player,
			@Local(argsOnly = true) InteractionHand hand,
			@Local(argsOnly = true) ItemStack itemStack,
			@Local(argsOnly = true) PoseStack poseStack
	) {
		boolean usingItem = player.isUsingItem() && player.getUseItemRemainingTicks() > 0 && player.getUsedItemHand() == hand;
		ModClientUtil.applyShieldOffset(hand, usingItem, itemStack, poseStack);
	}
}
*///?}
