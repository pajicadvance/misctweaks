package me.pajic.misctweaks.mixin;

import me.pajic.misctweaks.Main;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? if <= 1.21.1
import net.minecraft.world.item.ElytraItem;
//? if >= 1.21.4
/*import net.minecraft.core.component.DataComponents;*/

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    //? if < 1.21.5
    @Shadow public abstract void stopFallFlying();

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void cancelElytraFlyingInLiquid(CallbackInfo ci) {
        if (
                Main.CONFIG.elytraSwimTweak() &&
                //? if >= 1.21.1
                isInLiquid() &&
                //? if < 1.21.1
                /*isInWaterOrBubble() || isInLava() &&*/
                //? if <= 1.21.1
                getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ElytraItem
                //? if >= 1.21.4
                /*getItemBySlot(EquipmentSlot.CHEST).has(DataComponents.GLIDER)*/
        ) {
            stopFallFlying();
            setSwimming(true);
        }
    }
}
