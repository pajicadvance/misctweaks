package me.pajic.misctweaks.datapack;

import me.pajic.misctweaks.Main;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;

public class CraftableSaddleDatapack {
    public static void init() {
        //? if < 1.21.6 {
        if (Main.CONFIG.craftableSaddleBackport()) {
            FabricLoader.getInstance().getModContainer("misctweaks").ifPresent(modContainer ->
                    ResourceManagerHelper.registerBuiltinResourcePack(
                            //? if < 1.21.1
                            /*new ResourceLocation(*/
                            //? if >= 1.21.1
                            ResourceLocation.fromNamespaceAndPath(
                                    "misctweaks",
                                    //? if < 1.21.1
                                    /*"craftable_saddle_even_older"*/
                                    //? if 1.21.1
                                    "craftable_saddle_old"
                                    //? if > 1.21.1
                                    /*"craftable_saddle"*/
                            ),
                            modContainer,
                            ResourcePackActivationType.ALWAYS_ENABLED
                    )
            );
        }
        //?}
    }
}
