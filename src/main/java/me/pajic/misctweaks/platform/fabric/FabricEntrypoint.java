package me.pajic.misctweaks.platform.fabric;

//? fabric {

import me.pajic.misctweaks.MiscTweaks;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import net.fabricmc.api.ModInitializer;

@Entrypoint("main")
public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		MiscTweaks.onInitialize();
	}
}
//?}
