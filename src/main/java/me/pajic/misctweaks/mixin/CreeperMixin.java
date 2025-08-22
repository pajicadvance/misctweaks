package me.pajic.misctweaks.mixin;

import me.pajic.misctweaks.Main;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
//? if >= 1.21.4
/*import net.minecraft.server.level.ServerLevel;*/

@Mixin(Creeper.class)
public abstract class CreeperMixin extends Monster {
    protected CreeperMixin(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @ModifyArg(
            method = "explodeCreeper",
            at = @At(
                    value = "INVOKE",
                    //? if <= 1.21.1
                    target = "Lnet/minecraft/world/level/Level;explode(Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Level$ExplosionInteraction;)Lnet/minecraft/world/level/Explosion;"
                    //? if >= 1.21.4
                    /*target = "Lnet/minecraft/server/level/ServerLevel;explode(Lnet/minecraft/world/entity/Entity;DDDFLnet/minecraft/world/level/Level$ExplosionInteraction;)V"*/
            ),
            index = 5
    )
    private Level.ExplosionInteraction creeperExplosionDropsAll(Level.ExplosionInteraction original) {
        //? if < 1.21.4
        boolean mobGriefing = level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
        //? if >= 1.21.4
        /*boolean mobGriefing = ((ServerLevel) level()).getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);*/
        return Main.CONFIG.creeperExplosionDropsAllItems.get() && mobGriefing ? Level.ExplosionInteraction.TNT : original;
    }
}