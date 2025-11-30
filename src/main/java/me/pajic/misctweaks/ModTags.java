package me.pajic.misctweaks;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;

import java.util.List;

public class ModTags {

	//? if < 1.21.1 {
    /*public static final TagKey<Item> LEG_ARMOR = TagKey.create(
            Registries.ITEM, MiscTweaks.id("leg_armor")
    );
    *///?}
	public static final TagKey<Block> OBSIDIAN_LIKE = TagKey.create(
			Registries.BLOCK, MiscTweaks.id("obsidian_like")
	);
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
}
