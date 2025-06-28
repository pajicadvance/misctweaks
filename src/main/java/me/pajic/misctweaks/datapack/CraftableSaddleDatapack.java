package me.pajic.misctweaks.datapack;

import me.pajic.misctweaks.Main;
import me.pajic.misctweaks.util.MultiVersionUtil;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

public class CraftableSaddleDatapack {
    public static void init() {
        //? if < 1.21.6 {
        if (Main.CONFIG.craftableSaddleBackport.get()) {
            FabricLoader.getInstance().getModContainer("misctweaks").ifPresent(modContainer ->
                    ResourceManagerHelper.registerBuiltinResourcePack(
                            MultiVersionUtil.fromNamespaceAndPath(MultiVersionUtil.getSaddleRecipePath()),
                            modContainer,
                            ResourcePackActivationType.ALWAYS_ENABLED
                    )
            );
        }
        //?}
    }
}
