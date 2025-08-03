package me.pajic.misctweaks.config;

import me.fzzyhmstrs.fzzy_config.annotations.Action;
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction;
import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.pajic.misctweaks.Main;

@Version(version = 1)
public class ModCommonConfig extends Config {
    public ModCommonConfig() {
        super(Main.CONFIG_RL);
    }

    //? if < 1.21.5 {
    @RequiresAction(action = Action.RESTART)
    public ValidatedBoolean lodestoneChangesBackport = new ValidatedBoolean(true);
    //?}
    //? if < 1.21.6 {
    @RequiresAction(action = Action.RESTART)
    public ValidatedBoolean craftableSaddleBackport =  new ValidatedBoolean(true);
    //?}
    public ValidatedBoolean sneakingPreventsBerryBushDamage = new ValidatedBoolean(true);
    public ValidatedBoolean legArmorPreventsBerryBushDamage = new ValidatedBoolean(true);
    public ValidatedBoolean elytraSwimTweak = new ValidatedBoolean(true);
    public ValidatedBoolean soulSpeedNoDamage = new ValidatedBoolean(true);
    public ValidatedBoolean thornsNoDamage = new ValidatedBoolean(true);
    public ValidatedBoolean creeperExplosionDropsAllItems = new ValidatedBoolean(true);
    public ValidatedBoolean flammableCobweb = new ValidatedBoolean(true);
    public ValidatedBoolean fasterObsidianMining = new ValidatedBoolean(true);
    public ValidatedFloat obsidianMiningSpeedMultiplier = new ValidatedFloat(1.6F, 2.0F, 1.0F);
    public ValidatedBoolean randomizeDiscLoot = new ValidatedBoolean(true);
    public ValidatedBoolean preventShulkerDuplication = new ValidatedBoolean(false);
}
