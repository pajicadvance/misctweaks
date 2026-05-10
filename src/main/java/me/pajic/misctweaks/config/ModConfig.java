package me.pajic.misctweaks.config;

import me.fzzyhmstrs.fzzy_config.annotations.Action;
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction;
import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigSection;
import me.fzzyhmstrs.fzzy_config.validation.ValidatedField;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedSet;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedAny;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.pajic.misctweaks.MiscTweaks;
import me.pajic.misctweaks.ai.AnimalConfig;
import me.pajic.misctweaks.sapling.TreeBiomePredicate;
import me.pajic.misctweaks.sapling.TreeFeatureEntry;
import me.pajic.misctweaks.sapling.TreeFeatureType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.Set;

@Version(version = 1)
public class ModConfig extends Config {

	private final ValidatedAny<TreeFeatureEntry> treeFeatureEntry = new ValidatedAny<>(new TreeFeatureEntry());
	private final ValidatedAny<AnimalConfig> animalConfig = new ValidatedAny<>(new AnimalConfig());
	private final ValidatedAny<FlammableBlockEntry> flammableBlockEntry = new ValidatedAny<>(new FlammableBlockEntry());

    public ModConfig() {
        super(MiscTweaks.id("config"));
		ValidatedField.Companion.attachProvider(
				treeFeatureEntry,
				Provider.Companion.getWIDGET_TITLE(),
				(entry, _) -> Component.literal(entry.id.get().toShortString())
		);
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
	public ValidatedList<Identifier> obsidianBlocks = ValidatedIdentifier
			.ofRegistry(Identifier.withDefaultNamespace("obsidian"), BuiltInRegistries.BLOCK)
			.toList(
					Identifier.withDefaultNamespace("obsidian"),
					Identifier.withDefaultNamespace("crying_obsidian"),
					Identifier.withDefaultNamespace("respawn_anchor")
			);
	@RequiresAction(action = Action.RESTART) public ValidatedBoolean animalsSearchForFood = new ValidatedBoolean();
	@RequiresAction(action = Action.RESTART) public ValidatedSet<AnimalConfig> animalConfiguration = animalConfig.toSet();
	public ImprovedSaplings improvedSaplings = new ImprovedSaplings();
    public ValidatedBoolean randomizeDiscLoot = new ValidatedBoolean();
    public ValidatedBoolean preventShulkerDuplication = new ValidatedBoolean(false);

	public class StableBlockDrops extends ConfigSection {
		public ValidatedBoolean noRandomHorizontalMovement = new ValidatedBoolean();
		public ValidatedBoolean alwaysSpawnDropInBlockCenter = new ValidatedBoolean();
		public ValidatedBoolean flingTowardsPlayer = new ValidatedBoolean();
		public ValidatedBoolean requireCrouchForFling = new ValidatedBoolean();
		public ValidatedDouble flingMaxRange = new ValidatedDouble(5, 7, 3);
	}

	public class ImprovedSaplings extends ConfigSection {
		public ValidatedBoolean enabled = new ValidatedBoolean(false);
		public ValidatedList<TreeFeatureEntry> acaciaTreePool = treeFeatureEntry.toList(
				new TreeFeatureEntry(TreeFeatures.ACACIA.identifier()),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/acacia/classic")),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/acacia/modified")),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/acacia/modified_2"))
		);
		public ValidatedList<TreeFeatureEntry> azaleaTreePool = treeFeatureEntry.toList(
				new TreeFeatureEntry(TreeFeatures.AZALEA_TREE.identifier())
		);
		public ValidatedList<TreeFeatureEntry> birchTreePool = treeFeatureEntry.toList(
				new TreeFeatureEntry(TreeFeatures.BIRCH.identifier()),
				new TreeFeatureEntry(TreeFeatures.BIRCH_BEES_005.identifier(), TreeFeatureType.FLOWER_TREE),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/birch/classic")),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/birch/exp")),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/birch/exp2")),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/birch/tall")),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/birch/tall_modified")),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/birch/young"))
		);
		public ValidatedList<TreeFeatureEntry> cherryTreePool = treeFeatureEntry.toList(
				new TreeFeatureEntry(TreeFeatures.CHERRY.identifier()),
				new TreeFeatureEntry(TreeFeatures.CHERRY_BEES_005.identifier(), TreeFeatureType.FLOWER_TREE),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/cherry/big")),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/cherry/classic"))
		);
		public ValidatedList<TreeFeatureEntry> darkOakTreePool = treeFeatureEntry.toList(
				new TreeFeatureEntry(TreeFeatures.DARK_OAK.identifier(), TreeFeatureType.MEGA_TREE),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/dark_oak/classic"), TreeFeatureType.MEGA_TREE),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/dark_oak/huge"), TreeFeatureType.MEGA_TREE),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/dark_oak/modern"), TreeFeatureType.MEGA_TREE),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/dark_oak/slim"))
		);
		public ValidatedList<TreeFeatureEntry> jungleTreePool = treeFeatureEntry.toList(
				new TreeFeatureEntry(TreeFeatures.MEGA_JUNGLE_TREE.identifier(), TreeFeatureType.MEGA_TREE),
				new TreeFeatureEntry(TreeFeatures.JUNGLE_TREE_NO_VINE.identifier())
		);
		public ValidatedList<TreeFeatureEntry> mangroveTreePool = treeFeatureEntry.toList(
				new TreeFeatureEntry(TreeFeatures.MANGROVE.identifier(), 3),
				new TreeFeatureEntry(TreeFeatures.TALL_MANGROVE.identifier(), 17)
		);
		public ValidatedList<TreeFeatureEntry> oakTreePool = treeFeatureEntry.toList(
				new TreeFeatureEntry(TreeFeatures.OAK.identifier(), 9),
				new TreeFeatureEntry(TreeFeatures.FANCY_OAK.identifier(), 1),
				new TreeFeatureEntry(TreeFeatures.OAK_BEES_002.identifier(), TreeFeatureType.FLOWER_TREE, 9),
				new TreeFeatureEntry(TreeFeatures.OAK_BEES_005.identifier(), TreeFeatureType.FLOWER_TREE, 1),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/oak/swamp/classic"), new TreeBiomePredicate(Set.of(Identifier.withDefaultNamespace("swamp")))),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/oak/swamp/modern"), new TreeBiomePredicate(Set.of(Identifier.withDefaultNamespace("swamp")))),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/oak/swamp/young"), new TreeBiomePredicate(Set.of(Identifier.withDefaultNamespace("swamp")))),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/oak/classic"), new TreeBiomePredicate(Set.of(Identifier.withDefaultNamespace("swamp")), true)),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/oak/classic_double_bees"), TreeFeatureType.FLOWER_TREE),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/oak/elder"), new TreeBiomePredicate(Set.of(Identifier.withDefaultNamespace("swamp")), true)),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/oak/fancy"), new TreeBiomePredicate(Set.of(Identifier.withDefaultNamespace("swamp")), true)),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/oak/fancy_double_bees"), TreeFeatureType.FLOWER_TREE),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/oak/tall"), new TreeBiomePredicate(Set.of(Identifier.withDefaultNamespace("swamp")), true)),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/oak/young"), new TreeBiomePredicate(Set.of(Identifier.withDefaultNamespace("swamp")), true))
		);
		public ValidatedList<TreeFeatureEntry> paleOakTreePool = treeFeatureEntry.toList(
				new TreeFeatureEntry(TreeFeatures.PALE_OAK.identifier(), TreeFeatureType.MEGA_TREE)
		);
		public ValidatedList<TreeFeatureEntry> spruceTreePool = treeFeatureEntry.toList(
				new TreeFeatureEntry(TreeFeatures.MEGA_SPRUCE.identifier(), TreeFeatureType.MEGA_TREE),
				new TreeFeatureEntry(TreeFeatures.MEGA_PINE.identifier(), TreeFeatureType.MEGA_TREE),
				new TreeFeatureEntry(TreeFeatures.SPRUCE.identifier()),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/spruce/classic")),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/spruce/mega"), TreeFeatureType.MEGA_TREE),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/spruce/short")),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/spruce/tall")),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/spruce/young")),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/spruce/mega/grand_mega"), TreeFeatureType.MEGA_TREE),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/spruce/mega/mega"), TreeFeatureType.MEGA_TREE),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/spruce/pine/grand_mega"), TreeFeatureType.MEGA_TREE),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/spruce/pine/mega"), TreeFeatureType.MEGA_TREE),
				new TreeFeatureEntry(Identifier.fromNamespaceAndPath("geophilic", "tree/spruce/pine/normal"))
		);
	}
}
