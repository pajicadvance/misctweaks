package me.pajic.misctweaks.platform.fabric;

//? fabric {

import me.pajic.misctweaks.MiscTweaks;
import me.pajic.misctweaks.mixson.ResourceModifications;
import me.pajic.misctweaks.platform.Platform;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;

@SuppressWarnings("unused")
public class FabricEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		ResourceModifications.init();
		MiscTweaks.onInitialize();
		initConditionalCommonResources();
	}

	private void initConditionalCommonResources() {
		FabricLoader.getInstance().getModContainer(MiscTweaks.MOD_ID).ifPresent(modContainer -> {
			ResourceManagerHelper.registerBuiltinResourcePack(
					MiscTweaks.id(MiscTweaks.xplat().packPath(Platform.VersionedPackType.DATA)),
					modContainer,
					ResourcePackActivationType.ALWAYS_ENABLED
			);
			//? if < 1.21.10 {
			/*if (MiscTweaks.CONFIG.craftableSaddleBackport.get()) ResourceManagerHelper.registerBuiltinResourcePack(
					MiscTweaks.id(MiscTweaks.xplat().mcVersion().replace(".", "_") + "/craftable_saddle"),
					modContainer,
					ResourcePackActivationType.ALWAYS_ENABLED
			);
			if (MiscTweaks.CONFIG.lodestoneChangesBackport.get()) ResourceManagerHelper.registerBuiltinResourcePack(
					MiscTweaks.id(MiscTweaks.xplat().mcVersion().replace(".", "_") + "/lodestone_backport"),
					modContainer,
					ResourcePackActivationType.ALWAYS_ENABLED
			);
			*///?}
		});
	}
}
//?}
