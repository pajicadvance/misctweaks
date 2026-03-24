package me.pajic.misctweaks.platform.fabric;

//? fabric {

import me.pajic.misctweaks.MiscTweaks;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ClientModInitializer;

@Entrypoint("client")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		MiscTweaks.onInitializeClient();
	}
}
//?}
