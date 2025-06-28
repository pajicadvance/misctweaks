package me.pajic.misctweaks;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.pajic.misctweaks.config.ModClientConfig;
import me.pajic.misctweaks.mixson.ResourceModifications;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(value = "misctweaks", dist = Dist.CLIENT)
public class ClientMain {
    public static final ResourceLocation CLIENT_CONFIG_RL = ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, "client_config");
    public static ModClientConfig CLIENT_CONFIG = ConfigApiJava.registerAndLoadConfig(ModClientConfig::new, RegisterType.CLIENT);

    public ClientMain(IEventBus modEventBus) {
        modEventBus.addListener(this::onInitialize);
    }

    public void onInitialize(FMLCommonSetupEvent event) {
        ResourceModifications.clientInit();
    }
}
