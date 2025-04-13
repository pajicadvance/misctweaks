package me.pajic.misctweaks;

import me.pajic.misctweaks.config.ModServerConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;

@Mod("misctweaks")
public class Main {

    public static final TagKey<Block> OBSIDIAN_LIKE = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(
                    "misctweaks",
                    "obsidian_like"
            ));
    public static final List<Item> DISCS = List.of(
            Items.MUSIC_DISC_13,
            Items.MUSIC_DISC_CAT,
            Items.MUSIC_DISC_BLOCKS,
            Items.MUSIC_DISC_CHIRP,
            Items.MUSIC_DISC_FAR,
            Items.MUSIC_DISC_MALL,
            Items.MUSIC_DISC_MELLOHI,
            Items.MUSIC_DISC_STAL,
            Items.MUSIC_DISC_STRAD,
            Items.MUSIC_DISC_WARD,
            Items.MUSIC_DISC_11,
            Items.MUSIC_DISC_WAIT
    );

    public Main(FMLJavaModLoadingContext context) {
        ModContainer modContainer = context.getContainer();
        modContainer.addConfig(new ModConfig(ModConfig.Type.SERVER, ModServerConfig.SERVER_SPEC, modContainer));
    }
}
