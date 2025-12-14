package me.pajic.misctweaks;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.pajic.misctweaks.config.ModConfig;
import me.pajic.misctweaks.platform.Platform;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.ramixin.mixson.debug.DebugMode;
import net.ramixin.mixson.inline.Mixson;

//? fabric {
import me.pajic.misctweaks.platform.fabric.FabricPlatform;
//?} neoforge {
/*import me.pajic.misctweaks.platform.neoforge.NeoforgePlatform;
*///?}

@SuppressWarnings("LoggingSimilarMessage")
public class MiscTweaks {

	public static final String MOD_ID = /*$ mod_id*/ "misctweaks";
	public static final String MOD_VERSION = /*$ mod_version*/ "1.2.8";
	public static final String MOD_FRIENDLY_NAME = /*$ mod_name*/ "MiscTweaks";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Identifier CONFIG_RL = id("config");
	public static ModConfig CONFIG = ConfigApiJava.registerAndLoadConfig(ModConfig::new);
	private static final Platform PLATFORM = createPlatformInstance();

	public static void onInitialize() {
		if (PLATFORM.isDebug()) Mixson.setDebugMode(DebugMode.EXPORT);
	}

	public static Platform xplat() {
		return PLATFORM;
	}

	private static Platform createPlatformInstance() {
		//? fabric {
		return new FabricPlatform();
		//?} neoforge {
		/*return new NeoforgePlatform();
		*///?}
	}

	public static Identifier id(String path) {
		//? if 1.20.1
		//return new ResourceLocation(MOD_ID, path);
		//? if > 1.20.1
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static Identifier vanillaId(String path) {
		//? if 1.20.1
		//return new ResourceLocation(path);
		//? if > 1.20.1
		return Identifier.withDefaultNamespace(path);
	}

	public static void debugLog(String message, Object ... args) {
		if (PLATFORM.isDebug()) LOGGER.info(message, args);
	}
}
