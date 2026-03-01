package me.pajic.misctweaks.sapling;

import me.fzzyhmstrs.fzzy_config.annotations.Translation;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedAny;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedEnum;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.resources.Identifier;

@Translation(prefix = "misctweaks.config.treeFeatureEntry")
public class TreeFeatureEntry {
    public ValidatedIdentifier id;
    public ValidatedEnum<TreeFeatureType> type;
	public ValidatedInt weight;
    public ValidatedAny<TreeBiomePredicate> biomePredicate;

    public TreeFeatureEntry(Identifier id, TreeFeatureType type, int weight, TreeBiomePredicate biomePredicate) {
        this.id = new ValidatedIdentifier(id);
        this.type = new ValidatedEnum<>(type);
        this.weight = new ValidatedInt(weight, Integer.MAX_VALUE, 1);
        this.biomePredicate = new ValidatedAny<>(biomePredicate);
    }

    public TreeFeatureEntry(Identifier id, TreeFeatureType type, int weight) {
        this(id, type, weight, new TreeBiomePredicate());
    }

	public TreeFeatureEntry(Identifier id, TreeFeatureType type) {
		this(id, type, 1, new TreeBiomePredicate());
	}

	public TreeFeatureEntry(Identifier id, TreeBiomePredicate biomePredicate) {
		this(id, TreeFeatureType.TREE, 1, biomePredicate);
	}

    public TreeFeatureEntry(Identifier id, int weight, TreeBiomePredicate biomePredicate) {
        this(id, TreeFeatureType.TREE, weight, biomePredicate);
    }

    public TreeFeatureEntry(Identifier id, int weight) {
        this(id, TreeFeatureType.TREE, weight, new TreeBiomePredicate());
    }

	public TreeFeatureEntry(Identifier id) {
		this(id, TreeFeatureType.TREE, 1, new TreeBiomePredicate());
	}

    public TreeFeatureEntry() {
        this.id = new ValidatedIdentifier();
        this.type = new ValidatedEnum<>(TreeFeatureType.TREE);
		this.weight = new ValidatedInt(1, Integer.MAX_VALUE, 1);
        this.biomePredicate = new ValidatedAny<>(new TreeBiomePredicate());
    }
}
