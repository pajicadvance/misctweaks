package me.pajic.misctweaks.mixin;

import me.pajic.misctweaks.MiscTweaks;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void cancelElytraFlyingInLiquid(CallbackInfo ci) {
		Player self = (Player) (Object) this;
        if (
				MiscTweaks.CONFIG.elytraSwimTweak.get() &&
                self.isFallFlying() &&
				self.isInLiquid() &&
				self.getItemBySlot(EquipmentSlot.CHEST).has(DataComponents.GLIDER)
        ) {
            self.stopFallFlying();
			self.setSwimming(true);
        }
    }
}
