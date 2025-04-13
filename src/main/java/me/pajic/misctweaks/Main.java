package me.pajic.misctweaks;

import me.pajic.misctweaks.config.ModConfig;
import me.pajic.misctweaks.datapack.LodestoneBackportDatapack;
import me.pajic.misctweaks.mixson.ResourceModifications;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class Main implements ModInitializer {

    public static final ModConfig CONFIG = ModConfig.createAndLoad();
    //? if < 1.21.1 {
    /*public static final TagKey<Item> LEG_ARMOR = TagKey.create(
            Registries.ITEM,
            new ResourceLocation("leg_armor")
    );
    *///?}
    public static final TagKey<Block> OBSIDIAN_LIKE = TagKey.create(
            Registries.BLOCK,
            //? if >= 1.21.1
            ResourceLocation.fromNamespaceAndPath("misctweaks", "obsidian_like")
            //? if < 1.21.1
            /*new ResourceLocation("misctweaks", "obsidian_like")*/
    );

    @Override
    public void onInitialize() {
        //? if > 1.20.1
        ResourceModifications.init();
        //? if = 1.20.1
        /*LodestoneBackportDatapack.init();*/
    }
}
