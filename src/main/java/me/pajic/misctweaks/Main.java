package me.pajic.misctweaks;

import me.pajic.misctweaks.config.ModConfig;
import me.pajic.misctweaks.mixson.ResourceModifications;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class Main implements ModInitializer {

    public static final ModConfig CONFIG = ModConfig.createAndLoad();
    public static final TagKey<Block> OBSIDIAN_LIKE = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(
            "misctweaks",
            "obsidian_like"
    ));

    @Override
    public void onInitialize() {
        ResourceModifications.init();
    }
}
