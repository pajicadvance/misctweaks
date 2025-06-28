package me.pajic.misctweaks;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.pajic.misctweaks.config.ModConfig;
import me.pajic.misctweaks.datapack.CraftableSaddleDatapack;
import me.pajic.misctweaks.datapack.LodestoneBackportDatapack;
import me.pajic.misctweaks.mixson.ResourceModifications;
import me.pajic.misctweaks.util.MultiVersionUtil;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class Main implements ModInitializer {
    public static final String MOD_ID = "misctweaks";
    public static final ResourceLocation CONFIG_RL = MultiVersionUtil.fromNamespaceAndPath("config");
    public static ModConfig CONFIG = ConfigApiJava.registerAndLoadConfig(ModConfig::new);

    //? if < 1.21.1 {
    /*public static final TagKey<Item> LEG_ARMOR = TagKey.create(
            Registries.ITEM,
            new ResourceLocation("leg_armor")
    );
    *///?}
    public static final TagKey<Block> OBSIDIAN_LIKE = TagKey.create(
            Registries.BLOCK,
            MultiVersionUtil.fromNamespaceAndPath("obsidian_like")
    );

    @Override
    public void onInitialize() {
        //? if > 1.20.1
        ResourceModifications.init();
        //? if = 1.20.1
        /*LodestoneBackportDatapack.init();*/
        //? if < 1.21.6
        CraftableSaddleDatapack.init();
    }
}
