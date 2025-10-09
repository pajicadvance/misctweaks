package me.pajic.misctweaks.util;

import me.pajic.misctweaks.Main;
import net.minecraft.resources.ResourceLocation;

public class MultiVersionUtil {

    public static ResourceLocation fromNamespaceAndPath(String namespace, String path) {
        //? if >= 1.21.1
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
        //? if 1.20.1
        /*return new ResourceLocation(namespace, path);*/
    }

    public static ResourceLocation fromNamespaceAndPath(String path) {
        //? if >= 1.21.1
        return ResourceLocation.fromNamespaceAndPath(Main.MOD_ID, path);
        //? if 1.20.1
        /*return new ResourceLocation(Main.MOD_ID, path);*/
    }

    public static String getSaddleRecipePath() {
        //? if < 1.21.1
        /*return "craftable_saddle_even_older";*/
        //? if 1.21.1
        /*return "craftable_saddle_old";*/
        //? if > 1.21.1
        return "craftable_saddle";
    }
}
