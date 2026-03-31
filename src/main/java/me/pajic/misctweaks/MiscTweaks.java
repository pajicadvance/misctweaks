package me.pajic.misctweaks;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.pajic.misctweaks.config.ModConfig;
import me.pajic.misctweaks.mixson.AssetPatches;
import me.pajic.misctweaks.mixson.MixsonHelper;
import me.pajic.misctweaks.platform.Platform;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? fabric {
import me.pajic.misctweaks.platform.fabric.FabricPlatform;
//?} neoforge {
/*import me.pajic.misctweaks.platform.neoforge.NeoforgePlatform;
 *///?}

@SuppressWarnings("LoggingSimilarMessage")
public class MiscTweaks {

	public static final String MOD_ID = /*$ mod_id*/ "misctweaks";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static final Platform PLATFORM = createPlatformInstance();
	public static ModConfig CONFIG = ConfigApiJava.registerAndLoadConfig(ModConfig::new);

	public static void onInitialize() {
		MixsonHelper.setDebugFlags();
	}

	public static void onInitializeClient() {
		MiscTweaksClient.init();
		AssetPatches.init();
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
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static void debugLog(String message, Object ... args) {
		if (PLATFORM.isDebug()) LOGGER.info(message, args);
	}
}
