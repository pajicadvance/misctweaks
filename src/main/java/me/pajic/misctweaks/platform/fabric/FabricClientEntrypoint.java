package me.pajic.misctweaks.platform.fabric;

//? fabric {

import me.pajic.misctweaks.MiscTweaksClient;
import me.pajic.misctweaks.mixson.ClientResourceModifications;
import net.fabricmc.api.ClientModInitializer;

@SuppressWarnings("unused")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ClientResourceModifications.init();
		MiscTweaksClient.onInitializeClient();
	}
}
//?}
