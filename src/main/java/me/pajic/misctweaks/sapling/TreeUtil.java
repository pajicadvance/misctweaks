package me.pajic.misctweaks.sapling;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import me.pajic.misctweaks.MiscTweaks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TreeUtil {

	public static List<TreeFeatureEntry> getTreesForVariant(String variant) {
		return switch (variant) {
			case "acacia" -> MiscTweaks.CONFIG.improvedSaplings.acaciaTreePool;
			case "azalea" -> MiscTweaks.CONFIG.improvedSaplings.azaleaTreePool;
			case "birch" -> MiscTweaks.CONFIG.improvedSaplings.birchTreePool;
			case "cherry" -> MiscTweaks.CONFIG.improvedSaplings.cherryTreePool;
			case "dark_oak" -> MiscTweaks.CONFIG.improvedSaplings.darkOakTreePool;
			case "jungle" -> MiscTweaks.CONFIG.improvedSaplings.jungleTreePool;
			case "mangrove" -> MiscTweaks.CONFIG.improvedSaplings.mangroveTreePool;
			case "oak" -> MiscTweaks.CONFIG.improvedSaplings.oakTreePool;
			case "pale_oak" -> MiscTweaks.CONFIG.improvedSaplings.paleOakTreePool;
			case "spruce" -> MiscTweaks.CONFIG.improvedSaplings.spruceTreePool;
			default -> List.of();
		};
	}

    public static List<TreeFeatureEntry> filterTrees(List<TreeFeatureEntry> list, TreeFeatureType type, Holder<Biome> biome) {
        return list.stream().filter(
                fe -> fe.type.get() == type &&
                biomeTest(biome, fe.biomePredicate.get())
        ).toList();
    }

    private static boolean biomeTest(Holder<Biome> biome, TreeBiomePredicate predicate) {
        if (predicate.biomes.get().isEmpty()) return true;
        return predicate.blacklist.get() ?
                predicate.biomes.get().stream().noneMatch(biome::is) :
                predicate.biomes.get().stream().anyMatch(biome::is);
    }

    @Nullable
    public static ResourceKey<ConfiguredFeature<?, ?>> getRandomTree(
			ServerLevel level,
			RandomSource randomSource,
			List<TreeFeatureEntry> trees
	) {
		if (!trees.isEmpty()) {
			IntList pool = new IntArrayList();
			for (int i = 0; i < trees.size(); i++) {
				TreeFeatureEntry entry = trees.get(i);
				Holder<ConfiguredFeature<?, ?>> holder = level.registryAccess()
						.lookupOrThrow(Registries.CONFIGURED_FEATURE)
						./*? if >= 1.21.11 {*/get/*?} else {*//*getHolder*//*?}*/(ResourceKey.create(Registries.CONFIGURED_FEATURE, entry.id.get()))
						.orElse(null);
				if (holder != null) for (int j = 0; j < entry.weight.get(); j++) pool.add(i);
			}
			Identifier id = trees.get(pool.getInt(randomSource.nextInt(pool.size()))).id.get();
			MiscTweaks.debugLog(id.toString());
			return ResourceKey.create(Registries.CONFIGURED_FEATURE, id);
		}
		return null;
    }

    @Nullable
    public static Holder<Biome> getBiome(ServerLevel level, BlockPos pos) {
        return level != null && pos != null ? level.getBiome(pos) : null;
    }
}
