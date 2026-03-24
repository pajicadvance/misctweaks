package me.pajic.misctweaks.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.misctweaks.MiscTweaks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("ConstantValue")
@Mixin(Mob.class)
public class MobMixin {

    @WrapMethod(method = "canHoldItem")
    private boolean allowAnimalFoodPickup(ItemStack stack, Operation<Boolean> original) {
        if (MiscTweaks.CONFIG.animalsSearchForFood.get() && (Mob) (Object) this instanceof Animal animal) {
            return !animal.isBaby() && animal.getAge() == 0 && animal.canFallInLove() && animal.isFood(stack);
        }
        return original.call(stack);
    }

    @WrapWithCondition(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Mob;pickUpItem(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/item/ItemEntity;)V"
            )
    )
    private boolean animalPickupFood(Mob instance, ServerLevel level, ItemEntity itemEntity) {
        if (MiscTweaks.CONFIG.animalsSearchForFood.get() && instance instanceof Animal animal && animal.isFood(itemEntity.getItem())) {
            if (!animal.isInLove()) {
                ItemStack itemStack = itemEntity.getItem();
                animal.onItemPickup(itemEntity);
                animal.take(itemEntity, 1);
                itemStack.shrink(1);
                if (itemStack.isEmpty()) itemEntity.discard();
                animal.setInLove(itemEntity.getOwner() instanceof Player player ? player : null);
            }
            return false;
        }
        return true;
    }

    @WrapMethod(method = "canReplaceCurrentItem")
    private boolean preventBadCheck(ItemStack candidate, ItemStack existing, EquipmentSlot slot, Operation<Boolean> original) {
        if (MiscTweaks.CONFIG.animalsSearchForFood.get() && (Mob) (Object) this instanceof Animal animal && animal.isFood(candidate)) return false;
        return original.call(candidate, existing, slot);
    }
}
