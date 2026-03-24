package me.pajic.misctweaks.sapling;

import me.fzzyhmstrs.fzzy_config.annotations.Translation;
import me.fzzyhmstrs.fzzy_config.util.Walkable;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedSet;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import net.minecraft.resources.Identifier;

import java.util.Set;

@Translation(prefix = "misctweaks.config.treeBiomePredicate")
public class TreeBiomePredicate implements Walkable {
    public ValidatedSet<Identifier> biomes;
    public ValidatedBoolean blacklist;

    public TreeBiomePredicate(Set<Identifier> biomes, boolean blacklist) {
        this.biomes = new ValidatedIdentifier().toSet(biomes);
        this.blacklist = new ValidatedBoolean(blacklist);
    }

    public TreeBiomePredicate(Set<Identifier> biomes) {
        this.biomes = new ValidatedIdentifier().toSet(biomes);
        this.blacklist = new ValidatedBoolean(false);
    }

    public TreeBiomePredicate() {
        this.biomes = new ValidatedIdentifier().toSet();
        this.blacklist = new ValidatedBoolean(false);
    }
}
