package me.pajic.misctweaks.config;

import me.fzzyhmstrs.fzzy_config.annotations.Action;
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction;
import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.ValidatedField;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedSet;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedAny;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.pajic.misctweaks.MiscTweaks;
import me.pajic.misctweaks.ai.AnimalConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.HashSet;
import java.util.Set;

@Version(version = 2)
public class ModConfig extends Config {

	private final ValidatedAny<AnimalConfig> animalConfig = new ValidatedAny<>(new AnimalConfig());
	private final ValidatedAny<FlammableBlockEntry> flammableBlockEntry = new ValidatedAny<>(new FlammableBlockEntry());

    public ModConfig() {
        super(MiscTweaks.id("config"));
		ValidatedField.Companion.attachProvider(
				animalConfig,
				Provider.Companion.getWIDGET_TITLE(),
				(entry, _) -> Component.literal(entry.id.get().toShortString())
		);
		ValidatedField.Companion.attachProvider(
				flammableBlockEntry,
				Provider.Companion.getWIDGET_TITLE(),
				(entry, _) -> Component.literal(entry.id.get().toShortString())
		);
    }

    public ValidatedBoolean sneakingPreventsBerryBushDamage = new ValidatedBoolean();
    public ValidatedBoolean armorPreventsBerryBushDamage = new ValidatedBoolean();
	public ValidatedBoolean armorReducesBerryBushSlow = new ValidatedBoolean();
	public ValidatedDouble berryBushSlowReduction = new ValidatedDouble(1.7, 2.2, 1.0);
    public ValidatedBoolean elytraSwimTweak = new ValidatedBoolean();
    public ValidatedBoolean soulSpeedNoDamage = new ValidatedBoolean();
    public ValidatedBoolean thornsNoDamage = new ValidatedBoolean();
    public ValidatedBoolean creeperExplosionDropsAllItems = new ValidatedBoolean();
	public StableBlockDrops stableBlockDrops = new StableBlockDrops();
	@RequiresAction(action = Action.RESTART) public ValidatedBoolean flammableCobweb = new ValidatedBoolean();
	@RequiresAction(action = Action.RESTART) public ValidatedSet<FlammableBlockEntry> flammableBlocks = flammableBlockEntry.toSet(
			new FlammableBlockEntry(Identifier.withDefaultNamespace("cobweb"), 600, 60)
	);
    public ValidatedBoolean fasterObsidianMining = new ValidatedBoolean();
    public ValidatedFloat obsidianMiningSpeedMultiplier = new ValidatedFloat(1.6F, 2.0F, 1.0F);
	public ValidatedSet<Identifier> obsidianBlocks = ValidatedIdentifier
			.ofRegistry(Identifier.withDefaultNamespace("obsidian"), BuiltInRegistries.BLOCK)
			.toSet(
					Identifier.withDefaultNamespace("obsidian"),
					Identifier.withDefaultNamespace("crying_obsidian"),
					Identifier.withDefaultNamespace("respawn_anchor")
			);
	@RequiresAction(action = Action.RESTART) public ValidatedBoolean animalsSearchForFood = new ValidatedBoolean();
	@RequiresAction(action = Action.RESTART) public ValidatedSet<AnimalConfig> animalConfiguration = animalConfig.toSet(
			new AnimalConfig(Identifier.withDefaultNamespace("wolf"), false)
	);
    public ValidatedBoolean randomizeDiscLoot = new ValidatedBoolean();
    public ValidatedBoolean preventShulkerDuplication = new ValidatedBoolean(false);

	public static class StableBlockDrops extends ConfigSection {
		public ValidatedBoolean noRandomHorizontalMovement = new ValidatedBoolean();
		public ValidatedBoolean alwaysSpawnDropInBlockCenter = new ValidatedBoolean();
		public ValidatedBoolean flingTowardsPlayer = new ValidatedBoolean();
		public ValidatedBoolean requireCrouchForFling = new ValidatedBoolean();
		public ValidatedDouble flingMaxRange = new ValidatedDouble(5, 7, 3);
	}

	@Override
	public void update(int deserializedVersion) {
		if (deserializedVersion == 1) {
			Set<AnimalConfig> set = new HashSet<>(animalConfiguration.get());
			set.add(new AnimalConfig(Identifier.withDefaultNamespace("wolf"), false));
			animalConfiguration.validateAndSet(set);
		}
	}
}
