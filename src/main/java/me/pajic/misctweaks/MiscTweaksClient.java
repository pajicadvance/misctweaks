package me.pajic.misctweaks;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.pajic.misctweaks.config.ModClientConfig;

public class MiscTweaksClient {

	public static ModClientConfig CONFIG = ConfigApiJava.registerAndLoadConfig(ModClientConfig::new, RegisterType.CLIENT);
	public static boolean RAISED_LOADED = MiscTweaks.xplat().isModLoaded("raised");

	public static void init() {
	}
}
