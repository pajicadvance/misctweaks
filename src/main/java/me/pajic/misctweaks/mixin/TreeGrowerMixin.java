package me.pajic.misctweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.misctweaks.MiscTweaks;
import me.pajic.misctweaks.sapling.TreeFeatureEntry;
import me.pajic.misctweaks.sapling.TreeFeatureType;
import me.pajic.misctweaks.sapling.TreeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(TreeGrower.class)
public class TreeGrowerMixin {

    @Unique ServerLevel mt$level = null;
    @Unique BlockPos mt$pos = null;

    @Shadow @Final private String name;

	@Inject(
			method = "growTree",
			at = @At("HEAD")
	)
	private void updateLevelAndPos(
			ServerLevel serverLevel,
			ChunkGenerator chunkGenerator,
			BlockPos blockPos,
			BlockState blockState,
			RandomSource randomSource,
			CallbackInfoReturnable<Boolean> cir
	) {
		if (MiscTweaks.CONFIG.improvedSaplings.enabled.get()) {
			if (mt$level == null || !mt$level.equals(serverLevel)) mt$level = serverLevel;
			if (mt$pos == null || !mt$pos.equals(blockPos)) mt$pos = blockPos;
		}
	}

    @WrapMethod(method = "getConfiguredFeature")
    private @Nullable ResourceKey<ConfiguredFeature<?, ?>> extendTreePool(
            RandomSource randomSource,
            boolean isFlowers,
            Operation<ResourceKey<ConfiguredFeature<?, ?>>> original
    ) {
        if (MiscTweaks.CONFIG.improvedSaplings.enabled.get()) {
			List<TreeFeatureEntry> trees = TreeUtil.getTreesForVariant(name);
            Holder<Biome> biome = TreeUtil.getBiome(mt$level, mt$pos);
            if (biome != null) {
                if (isFlowers) return TreeUtil.getRandomTree(
						mt$level, randomSource, TreeUtil.filterTrees(trees, TreeFeatureType.FLOWER_TREE, biome)
                );
                else return TreeUtil.getRandomTree(
						mt$level, randomSource, TreeUtil.filterTrees(trees, TreeFeatureType.TREE, biome)
                );
            }
        }
        return original.call(randomSource, isFlowers);
    }

    @WrapMethod(method = "getConfiguredMegaFeature")
    private @Nullable ResourceKey<ConfiguredFeature<?, ?>> extendMegaTreePool(
            RandomSource randomSource,
            Operation<ResourceKey<ConfiguredFeature<?, ?>>> original
    ) {
        if (MiscTweaks.CONFIG.improvedSaplings.enabled.get()) {
			List<TreeFeatureEntry> trees = TreeUtil.getTreesForVariant(name);
            Holder<Biome> biome = TreeUtil.getBiome(mt$level, mt$pos);
            if (biome != null) return TreeUtil.getRandomTree(
					mt$level, randomSource, TreeUtil.filterTrees(trees, TreeFeatureType.MEGA_TREE, biome)
            );
        }
        return original.call(randomSource);
    }
}
