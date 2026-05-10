package me.pajic.misctweaks.mixin.client;

import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.misctweaks.MiscTweaksClient;
import me.pajic.misctweaks.util.ModClientUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(Gui.class)
public class GuiMixin {

	@Inject(
			method = {"extractHotbar", "extractHotbarAndDecorations"},
			require = 1,
			at = @At("HEAD")
	)
	private void raiseHotbarStart(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
		if (ModClientUtil.shouldRaiseHotbar()) {
			graphics.pose().pushMatrix();
			graphics.pose().translate(0, -MiscTweaksClient.CONFIG.raiseHotbarPixels.get());
		}
	}

	@Inject(
			method = {"maybeExtractSpectatorTooltip", "extractHotbarAndDecorations"},
			require = 1,
			at = @At("TAIL")
	)
	private void raiseHotbarEnd(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
		if (ModClientUtil.shouldRaiseHotbar()) graphics.pose().popMatrix();
	}

	@ModifyArg(
			method = "extractItemHotbar",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
					ordinal = 1
			),
			index = 5
	)
	private int fixHotbarSelector(int height) {
		return ModClientUtil.shouldRaiseHotbar() ? height + 1 : height;
	}
}
