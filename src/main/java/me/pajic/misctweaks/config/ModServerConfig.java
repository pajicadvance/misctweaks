package me.pajic.misctweaks.config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = "misctweaks", bus = EventBusSubscriber.Bus.MOD)
public class ModServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue SNEAKING_PREVENTS_BERRY_BUSH_DAMAGE = BUILDER
            .translation("text.config.misctweaks.option.sneakingPreventsBerryBushDamage")
            .define("sneakingPreventsBerryBushDamage", true);
    private static final ModConfigSpec.BooleanValue LEG_ARMOR_PREVENTS_BERRY_BUSH_DAMAGE = BUILDER
            .translation("text.config.misctweaks.option.legArmorPreventsBerryBushDamage")
            .define("legArmorPreventsBerryBushDamage", true);
    //? if < 1.21.5 {
    private static final ModConfigSpec.BooleanValue LODESTONE_CHANGES_BACKPORT = BUILDER
            .translation("text.config.misctweaks.option.lodestoneChangesBackport")
            .define("lodestoneChangesBackport", true);
    //?}
    //? if < 1.21.6 {
    private static final ModConfigSpec.BooleanValue CRAFTABLE_SADDLE_BACKPORT = BUILDER
            .translation("text.config.misctweaks.option.craftableSaddleBackport")
            .define("craftableSaddleBackport", true);
    //?}
    private static final ModConfigSpec.BooleanValue ELYTRA_SWIM_TWEAK = BUILDER
            .translation("text.config.misctweaks.option.elytraSwimTweak")
            .define("elytraSwimTweak", true);
    private static final ModConfigSpec.BooleanValue SOUL_SPEED_NO_DAMAGE = BUILDER
            .translation("text.config.misctweaks.option.soulSpeedNoDamage")
            .define("soulSpeedNoDamage", true);
    private static final ModConfigSpec.BooleanValue THORNS_NO_DAMAGE = BUILDER
            .translation("text.config.misctweaks.option.thornsNoDamage")
            .define("thornsNoDamage", true);
    private static final ModConfigSpec.BooleanValue CREEPER_EXPLOSION_DROPS_ALL_ITEMS = BUILDER
            .translation("text.config.misctweaks.option.creeperExplosionDropsAllItems")
            .define("creeperExplosionDropsAllItems", true);
    private static final ModConfigSpec.BooleanValue FLAMMABLE_COBWEB = BUILDER
            .translation("text.config.misctweaks.option.flammableCobweb")
            .define("flammableCobweb", true);
    private static final ModConfigSpec.BooleanValue FASTER_OBSIDIAN_MINING = BUILDER
            .translation("text.config.misctweaks.option.fasterObsidianMining")
            .define("fasterObsidianMining", true);
    private static final ModConfigSpec.DoubleValue OBSIDIAN_MINING_MULTIPLIER = BUILDER
            .translation("text.config.misctweaks.option.obsidianMiningSpeedMultiplier")
            .defineInRange("obsidianMiningSpeedMultiplier", 1.6, 1.0, 2.0);
    private static final ModConfigSpec.BooleanValue RANDOMIZE_DISC_LOOT = BUILDER
            .translation("text.config.misctweaks.option.randomizeDiscLoot")
            .define("randomizeDiscLoot", true);
    private static final ModConfigSpec.BooleanValue PREVENT_SHULKER_DUPLICATION = BUILDER
            .translation("text.config.misctweaks.option.preventShulkerDuplication")
            .define("preventShulkerDuplication", false);

    public static final ModConfigSpec SERVER_SPEC = BUILDER.build();

    public static boolean sneakingPreventsBerryBushDamage = true;
    public static boolean legArmorPreventsBerryBushDamage = true;
    //? if < 1.21.5
    public static boolean lodestoneChangesBackport = true;
    //? if < 1.21.6
    public static boolean craftableSaddleBackport = true;
    public static boolean elytraSwimTweak = true;
    public static boolean soulSpeedNoDamage = true;
    public static boolean thornsNoDamage = true;
    public static boolean creeperExplosionDropsAllItems = true;
    public static boolean flammableCobweb = true;
    public static boolean fasterObsidianMining = true;
    public static float obsidianMiningSpeedMultiplier = 1.6F;
    public static boolean randomizeDiscLoot = true;
    public static boolean preventShulkerDuplication = false;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent.Loading event) {
        updateConfig(event);
    }

    @SubscribeEvent
    static void onChange(final ModConfigEvent.Reloading event) {
        updateConfig(event);
    }

    private static void updateConfig(ModConfigEvent event) {
        if (event.getConfig().getSpec() == SERVER_SPEC) {
            sneakingPreventsBerryBushDamage = SNEAKING_PREVENTS_BERRY_BUSH_DAMAGE.get();
            legArmorPreventsBerryBushDamage = LEG_ARMOR_PREVENTS_BERRY_BUSH_DAMAGE.get();
            //? if < 1.21.5
            lodestoneChangesBackport = LODESTONE_CHANGES_BACKPORT.get();
            //? if < 1.21.6
            craftableSaddleBackport = CRAFTABLE_SADDLE_BACKPORT.get();
            elytraSwimTweak = ELYTRA_SWIM_TWEAK.get();
            soulSpeedNoDamage = SOUL_SPEED_NO_DAMAGE.get();
            thornsNoDamage = THORNS_NO_DAMAGE.get();
            creeperExplosionDropsAllItems = CREEPER_EXPLOSION_DROPS_ALL_ITEMS.get();
            flammableCobweb = FLAMMABLE_COBWEB.get();
            fasterObsidianMining = FASTER_OBSIDIAN_MINING.get();
            obsidianMiningSpeedMultiplier = OBSIDIAN_MINING_MULTIPLIER.get().floatValue();
            randomizeDiscLoot = RANDOMIZE_DISC_LOOT.get();
            preventShulkerDuplication = PREVENT_SHULKER_DUPLICATION.get();
        }
    }
}
