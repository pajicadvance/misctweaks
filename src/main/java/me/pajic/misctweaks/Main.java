package me.pajic.misctweaks;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.pajic.misctweaks.config.ModCommonConfig;
import me.pajic.misctweaks.datapack.CraftableSaddleDatapack;
import me.pajic.misctweaks.mixson.ResourceModifications;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod("misctweaks")
public class Main {
    public static final String MOD_ID = "misctweaks";
    public static final ResourceLocation CONFIG_RL = ResourceLocation.fromNamespaceAndPath(MOD_ID, "config");
    public static ModCommonConfig CONFIG = ConfigApiJava.registerAndLoadConfig(ModCommonConfig::new);

    public static final TagKey<Block> OBSIDIAN_LIKE = TagKey.create(
            Registries.BLOCK,
            ResourceLocation.fromNamespaceAndPath(
                    MOD_ID, "obsidian_like"
            ));

    public Main(IEventBus modEventBus) {
        modEventBus.addListener(CraftableSaddleDatapack::registerDatapack);
        ResourceModifications.init();
    }
}
