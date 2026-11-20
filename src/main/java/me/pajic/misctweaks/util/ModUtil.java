package me.pajic.misctweaks.util;

import me.pajic.misctweaks.MiscTweaks;
import me.pajic.misctweaks.mixin.FireBlockInvoker;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModUtil {

    public static final List<String> BLOCK_SUGGESTIONS = new ArrayList<>();
    public static boolean suppressEnchantmentItemDamage = false;
    public static boolean poppingBlockResource = false;
    public static @Nullable Player blockBreaker = null;

	public static final List<Item> DISCS = List.of(
			Items.MUSIC_DISC_13,
			Items.MUSIC_DISC_CAT,
			Items.MUSIC_DISC_BLOCKS,
			Items.MUSIC_DISC_CHIRP,
			Items.MUSIC_DISC_FAR,
			Items.MUSIC_DISC_MALL,
			Items.MUSIC_DISC_MELLOHI,
			Items.MUSIC_DISC_STAL,
			Items.MUSIC_DISC_STRAD,
			Items.MUSIC_DISC_WARD,
			Items.MUSIC_DISC_11,
			Items.MUSIC_DISC_WAIT
	);

    public static void onLevelLoad(LevelAccessor level) {
        //~ if <26.1 'lookupOrThrow' -> 'registryOrThrow'
        Registry<Block> registry = level.registryAccess().lookupOrThrow(Registries.BLOCK);
        ModUtil.BLOCK_SUGGESTIONS.clear();
        ModUtil.BLOCK_SUGGESTIONS.addAll(registry.keySet().stream().map(Identifier::toString).toList());
        //~ if <26.1 'listTagIds' -> 'getTagNames'
        //~ if <26.1 'tag.location()' -> 'tag.location()'
        registry.listTagIds().forEach(tag -> ModUtil.BLOCK_SUGGESTIONS.add("#" + tag.location()));

        if (!level.isClientSide() && MiscTweaks.CONFIG.flammableCobweb.get()) {
            FireBlock fire = (FireBlock) Blocks.FIRE;
            MiscTweaks.CONFIG.flammableBlocks.get().forEach(entry -> {
                String id = entry.id.get();
                boolean isTag = id.startsWith("#");
                Identifier parsed = Identifier.tryParse(isTag ? id.substring(1) : id);
                if (parsed == null) {
                    MiscTweaks.LOGGER.warn("Invalid id '{}' in flammable blocks config, skipping", id);
                    return;
                }
                if (isTag) {
                    registry.getTagOrEmpty(TagKey.create(Registries.BLOCK, parsed))
                            .forEach(blockHolder -> ((FireBlockInvoker) fire).mt$invokeSetFlammable(
                                    blockHolder.value(),
                                    entry.igniteOdds.get(),
                                    entry.burnOdds.get()
                            ));
                } else {
                    Optional<Block> block = registry.getOptional(parsed);
                    if (block.isPresent()) ((FireBlockInvoker) fire).mt$invokeSetFlammable(
                            block.get(),
                            entry.igniteOdds.get(),
                            entry.burnOdds.get()
                    );
                    else MiscTweaks.LOGGER.warn("Unknown block '{}' in flammable blocks config, skipping", id);
                }
            });
        }
    }
}
