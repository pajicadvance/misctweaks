package me.pajic.misctweaks.sapling;

import me.fzzyhmstrs.fzzy_config.util.EnumTranslatable;
import org.jetbrains.annotations.NotNull;

public enum TreeFeatureType implements EnumTranslatable {
    TREE, MEGA_TREE, FLOWER_TREE;

	@Override
	public @NotNull String prefix() {
		return "misctweaks.treeFeatureType";
	}
}
