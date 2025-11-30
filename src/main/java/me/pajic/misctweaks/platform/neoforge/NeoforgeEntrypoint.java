package me.pajic.misctweaks.platform.neoforge;

//? neoforge {

/*import me.pajic.misctweaks.MiscTweaks;
import me.pajic.misctweaks.mixson.ResourceModifications;
import me.pajic.misctweaks.platform.Platform;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(MiscTweaks.MOD_ID)
@EventBusSubscriber(modid = MiscTweaks.MOD_ID)
public class NeoforgeEntrypoint {

	@SubscribeEvent
	private static void onCommonSetup(FMLCommonSetupEvent event) {
		MiscTweaks.onInitialize();
	}

	@SubscribeEvent
	private static void initCommonResources(AddPackFindersEvent event) {
		event.addPackFinders(
				MiscTweaks.id(MiscTweaks.xplat().packPath(Platform.VersionedPackType.DATA)),
				PackType.SERVER_DATA,
				Component.literal("MiscTweaks " + MiscTweaks.xplat().mcVersion() + " Data Pack"),
				PackSource.BUILT_IN,
				true,
				Pack.Position.TOP
		);
		//? if < 1.21.10 {
		/^if (MiscTweaks.CONFIG.craftableSaddleBackport.get()) event.addPackFinders(
				MiscTweaks.id("resourcepacks/" + MiscTweaks.xplat().mcVersion().replace(".", "_") + "/craftable_saddle"),
				PackType.SERVER_DATA,
				Component.literal("Craftable saddle for " + MiscTweaks.xplat().mcVersion()),
				PackSource.BUILT_IN,
				true,
				Pack.Position.TOP
		);
		^///?}
	}

	@SubscribeEvent
	private static void initRegistry(RegisterEvent event) {
		ResourceModifications.init();
	}
}
*///?}
