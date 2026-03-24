package me.pajic.misctweaks.config;

import me.fzzyhmstrs.fzzy_config.annotations.Action;
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction;
import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.ValidatedField;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedSet;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedAny;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.pajic.misctweaks.MiscTweaks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

@Version(version = 1)
public class ModClientConfig extends Config {

	private final ValidatedAny<LoweredShieldEntry> loweredShieldEntry = new ValidatedAny<>(new LoweredShieldEntry());

    public ModClientConfig() {
        super(MiscTweaks.id("client_config"));
		ValidatedField.Companion.attachProvider(
				loweredShieldEntry,
				Provider.Companion.getWIDGET_TITLE(),
				(entry, _) -> Component.literal(entry.id.get().toShortString())
		);
    }

    @RequiresAction(action = Action.RELOAD_RESOURCES)
    public ValidatedBoolean lowerShield = new ValidatedBoolean(true);
    @RequiresAction(action = Action.RELOAD_RESOURCES)
    public ValidatedSet<LoweredShieldEntry> shields = loweredShieldEntry.toSet(
			new LoweredShieldEntry(Identifier.withDefaultNamespace("shield"), -4.0F)
	);
}
