package me.pajic.misctweaks.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = "misctweaks", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModServerConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue SNEAKING_PREVENTS_BERRY_BUSH_DAMAGE = BUILDER
            .translation("text.config.misctweaks.option.sneakingPreventsBerryBushDamage")
            .define("sneakingPreventsBerryBushDamage", true);
    private static final ForgeConfigSpec.BooleanValue LEG_ARMOR_PREVENTS_BERRY_BUSH_DAMAGE = BUILDER
            .translation("text.config.misctweaks.option.legArmorPreventsBerryBushDamage")
            .define("legArmorPreventsBerryBushDamage", true);
    private static final ForgeConfigSpec.BooleanValue LODESTONE_CHANGES_BACKPORT = BUILDER
            .translation("text.config.misctweaks.option.lodestoneChangesBackport")
            .define("lodestoneChangesBackport", true);
    private static final ForgeConfigSpec.BooleanValue ELYTRA_SWIM_TWEAK = BUILDER
            .translation("text.config.misctweaks.option.elytraSwimTweak")
            .define("elytraSwimTweak", true);
    private static final ForgeConfigSpec.BooleanValue SOUL_SPEED_NO_DAMAGE = BUILDER
            .translation("text.config.misctweaks.option.soulSpeedNoDamage")
            .define("soulSpeedNoDamage", true);
    private static final ForgeConfigSpec.BooleanValue THORNS_NO_DAMAGE = BUILDER
            .translation("text.config.misctweaks.option.thornsNoDamage")
            .define("thornsNoDamage", true);
    private static final ForgeConfigSpec.BooleanValue CREEPER_EXPLOSION_DROPS_ALL_ITEMS = BUILDER
            .translation("text.config.misctweaks.option.creeperExplosionDropsAllItems")
            .define("creeperExplosionDropsAllItems", true);
    private static final ForgeConfigSpec.BooleanValue FLAMMABLE_COBWEB = BUILDER
            .translation("text.config.misctweaks.option.flammableCobweb")
            .define("flammableCobweb", true);
    private static final ForgeConfigSpec.BooleanValue FASTER_OBSIDIAN_MINING = BUILDER
            .translation("text.config.misctweaks.option.fasterObsidianMining")
            .define("fasterObsidianMining", true);
    private static final ForgeConfigSpec.DoubleValue OBSIDIAN_MINING_MULTIPLIER = BUILDER
            .translation("text.config.misctweaks.option.obsidianMiningSpeedMultiplier")
            .defineInRange("obsidianMiningSpeedMultiplier", 1.6, 1.0, 2.0);
    private static final ForgeConfigSpec.BooleanValue RANDOMIZE_DISC_LOOT = BUILDER
            .translation("text.config.misctweaks.option.randomizeDiscLoot")
            .define("randomizeDiscLoot", true);
    private static final ForgeConfigSpec.BooleanValue PREVENT_SHULKER_DUPLICATION = BUILDER
            .translation("text.config.misctweaks.option.preventShulkerDuplication")
            .define("preventShulkerDuplication", false);

    public static final ForgeConfigSpec SERVER_SPEC = BUILDER.build();

    public static boolean sneakingPreventsBerryBushDamage = true;
    public static boolean legArmorPreventsBerryBushDamage = true;
    public static boolean lodestoneChangesBackport = true;
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
            lodestoneChangesBackport = LODESTONE_CHANGES_BACKPORT.get();
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
