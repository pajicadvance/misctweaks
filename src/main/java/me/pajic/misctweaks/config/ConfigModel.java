package me.pajic.misctweaks.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.RangeConstraint;
import io.wispforest.owo.config.annotation.Sync;

@Modmenu(modId = "misctweaks")
@Config(name = "misctweaks", wrapperName = "ModConfig")
@Sync(Option.SyncMode.OVERRIDE_CLIENT)
@SuppressWarnings("unused")
public class ConfigModel {
    public boolean sneakingPreventsBerryBushDamage = true;
    public boolean legArmorPreventsBerryBushDamage = true;
    public boolean lodestoneChangesBackport = true;
    public boolean elytraSwimTweak = true;
    public boolean soulSpeedNoDamage = true;
    public boolean thornsNoDamage = true;
    public boolean creeperExplosionDropsAllItems = true;
    public boolean flammableCobweb = true;
    public boolean fasterObsidianMining = true;
    @RangeConstraint(min = 1.0F, max = 2.0F) public float obsidianMiningSpeedMultiplier = 1.6F;
    public boolean randomizeDiscLoot = true;
    public boolean preventShulkerDuplication = false;
}
