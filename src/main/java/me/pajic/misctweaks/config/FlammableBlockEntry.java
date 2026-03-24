package me.pajic.misctweaks.config;

import me.fzzyhmstrs.fzzy_config.annotations.Translation;
import me.fzzyhmstrs.fzzy_config.util.Walkable;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

@Translation(prefix = "misctweaks.config.flammableBlockEntry")
public class FlammableBlockEntry implements Walkable {

	public ValidatedIdentifier id;
	public ValidatedInt igniteOdds;
	public ValidatedInt burnOdds;

	public FlammableBlockEntry(Identifier id, int igniteOdds, int burnOdds) {
		this.id = new ValidatedIdentifier(id);
		this.igniteOdds = new ValidatedInt(igniteOdds);
		this.burnOdds = new ValidatedInt(burnOdds);
	}

	public FlammableBlockEntry() {
		this.id = ValidatedIdentifier.ofRegistry(Identifier.withDefaultNamespace("cobweb"), BuiltInRegistries.BLOCK);
		this.igniteOdds = new ValidatedInt(60, 600, 5);
		this.burnOdds = new ValidatedInt(100, 1000, 5);
	}
}
