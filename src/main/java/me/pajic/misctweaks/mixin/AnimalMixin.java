package me.pajic.misctweaks.mixin;

import me.pajic.misctweaks.MiscTweaks;
import me.pajic.misctweaks.ai.AnimalConfig;
import me.pajic.misctweaks.ai.AnimalConfigs;
import me.pajic.misctweaks.ai.SearchForFoodGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Animal.class)
public abstract class AnimalMixin extends Mob {

    protected AnimalMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void addSearchForFoodGoal(EntityType<? extends Animal> type, Level level, CallbackInfo ci) {
        if (level.isClientSide() || !MiscTweaks.CONFIG.animalsSearchForFood.get()) return;
        AnimalConfig config = AnimalConfigs.forType(type);
        if (config.doSearch.get()) {
            goalSelector.addGoal(
                    config.searchGoalPriority.get(),
                    new SearchForFoodGoal<>((Animal) (Object) this, config.searchSpeed.get(), config.searchRange.get())
            );
        }
    }
}
