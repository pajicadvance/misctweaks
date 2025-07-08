package me.pajic.misctweaks;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.pajic.misctweaks.config.ModClientConfig;
import me.pajic.misctweaks.mixson.ResourceModifications;
import me.pajic.misctweaks.util.MultiVersionUtil;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.resources.ResourceLocation;

public class ClientMain implements ClientModInitializer {
    public static final ResourceLocation CLIENT_CONFIG_RL = MultiVersionUtil.fromNamespaceAndPath("client_config");
    //? if > 1.20.1
    public static ModClientConfig CLIENT_CONFIG = ConfigApiJava.registerAndLoadConfig(ModClientConfig::new, RegisterType.CLIENT);

    @Override
    public void onInitializeClient() {
        //? if > 1.20.1
        ResourceModifications.clientInit();
    }
}
