package me.pajic.misctweaks;

import me.pajic.misctweaks.config.ModServerConfig;
import me.pajic.misctweaks.mixson.ResourceModifications;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod("misctweaks")
public class Main {

    public static final TagKey<Block> OBSIDIAN_LIKE = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(
                    "misctweaks",
                    "obsidian_like"
            ));

    public Main(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, ModServerConfig.SERVER_SPEC);
        ResourceModifications.init();
    }
}
