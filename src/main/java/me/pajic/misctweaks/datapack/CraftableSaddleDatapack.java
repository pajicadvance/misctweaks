package me.pajic.misctweaks.datapack;

import me.pajic.misctweaks.Main;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;

public class CraftableSaddleDatapack {
    @SubscribeEvent
    public static void registerDatapack(AddPackFindersEvent event) {
        //? if < 1.21.6 {
        /*if (Main.CONFIG.craftableSaddleBackport.get()) event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath(
                        "misctweaks",
                        //? if 1.21.1
                        /^"craftable_saddle_old"^/
                        //? if > 1.21.1
                        "craftable_saddle"
                ),
                PackType.SERVER_DATA,
                Component.literal("Craftable Saddle Backport"),
                PackSource.BUILT_IN,
                true,
                Pack.Position.TOP
        );
        *///?}
    }
}
