package me.pajic.misctweaks.mixin;

import me.pajic.misctweaks.MiscTweaks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level./*? if > 1.21.10 {*//*gamerules.*//*?}*/GameRules;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Creeper.class)
public abstract class CreeperMixin extends Monster {
    protected CreeperMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @SuppressWarnings("resource")
    @ModifyArg(
            method = "explodeCreeper",
            at = @At(
                    value = "INVOKE",
                    //? if < 1.21.10
                    //target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Level$ExplosionInteraction;)Lnet/minecraft/world/level/Explosion;"
                    //? if >= 1.21.10
                    target = "Lnet/minecraft/server/level/ServerLevel;explode(Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Level$ExplosionInteraction;)V"
            ),
            index = 5
    )
    private Level.ExplosionInteraction creeperExplosionDropsAll(Level.ExplosionInteraction original) {
        //? if < 1.21.10
        //boolean mobGriefing = level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
        //? if >= 1.21.10
        boolean mobGriefing = ((ServerLevel) level()).getGameRules()./*? if > 1.21.10 {*//*get*//*?} else {*/getBoolean/*?}*/(GameRules./*? if > 1.21.10 {*//*MOB_GRIEFING*//*?} else {*/RULE_MOBGRIEFING/*?}*/);
        return MiscTweaks.CONFIG.creeperExplosionDropsAllItems.get() && mobGriefing ? Level.ExplosionInteraction.TNT : original;
    }
}
