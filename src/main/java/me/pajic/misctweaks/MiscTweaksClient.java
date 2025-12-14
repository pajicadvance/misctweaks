package me.pajic.misctweaks;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.pajic.misctweaks.config.ModClientConfig;
import net.minecraft.resources.Identifier;

public class MiscTweaksClient {
	public static final Identifier CLIENT_CONFIG_RL = MiscTweaks.id("client_config");
	public static ModClientConfig CONFIG = ConfigApiJava.registerAndLoadConfig(ModClientConfig::new, RegisterType.CLIENT);

	public static void onInitializeClient() {
	}
}
