package me.pajic.misctweaks.config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = "misctweaks", bus = EventBusSubscriber.Bus.MOD)
public class ModClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue LOWER_SHIELD = BUILDER
            .translation("text.config.misctweaks.option.lowerShield")
            .gameRestart()
            .define("lowerShield", true);
    private static final ModConfigSpec.ConfigValue<List<? extends String>> SHIELDS = BUILDER
            .translation("text.config.misctweaks.option.shields")
            .gameRestart()
            .defineListAllowEmpty("shields", List.of("minecraft:shield"), () -> "", o -> true);

    public static final ModConfigSpec CLIENT_SPEC = BUILDER.build();

    public static boolean lowerShield;
    public static List<String> shields;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent.Loading event) {
        updateConfig(event);
    }

    @SubscribeEvent
    static void onChange(final ModConfigEvent.Reloading event) {
        updateConfig(event);
    }

    private static void updateConfig(ModConfigEvent event) {
        if (event.getConfig().getSpec() == CLIENT_SPEC) {
            lowerShield = LOWER_SHIELD.get();
            shields = new ArrayList<>(SHIELDS.get());
        }
    }
}
