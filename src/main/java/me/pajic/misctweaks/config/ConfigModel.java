package me.pajic.misctweaks.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;

import java.util.List;

@Modmenu(modId = "misctweaks")
@Config(name = "misctweaks", wrapperName = "ModConfig")
@Sync(Option.SyncMode.OVERRIDE_CLIENT)
@SuppressWarnings("unused")
public class ConfigModel {
    public boolean sneakingPreventsBerryBushDamage = true;
    public boolean legArmorPreventsBerryBushDamage = true;
    //? if < 1.21.5
    @RestartRequired public boolean lodestoneChangesBackport = true;
    public boolean elytraSwimTweak = true;
    public boolean soulSpeedNoDamage = true;
    public boolean thornsNoDamage = true;
    public boolean creeperExplosionDropsAllItems = true;
    public boolean flammableCobweb = true;
    public boolean fasterObsidianMining = true;
    @RangeConstraint(min = 1.0F, max = 2.0F) public float obsidianMiningSpeedMultiplier = 1.6F;
    public boolean randomizeDiscLoot = true;
    public boolean preventShulkerDuplication = false;

    //? if > 1.20.1 {
    @RestartRequired @Sync(Option.SyncMode.NONE) public boolean lowerShield = true;
    @RestartRequired @Sync(Option.SyncMode.NONE) public List<String> shields = List.of("minecraft:shield");
    //?}
}
