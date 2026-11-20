package me.pajic.misctweaks.ai;

import me.pajic.misctweaks.MiscTweaks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnimalConfigs {

	private static volatile @Nullable Cache cache;

	public static AnimalConfig forType(EntityType<?> type) {
		Set<? extends AnimalConfig> source = MiscTweaks.CONFIG.animalConfiguration.get();
		Cache current = cache;
		if (current == null || current.source() != source) {
			Map<Identifier, AnimalConfig> configs = new HashMap<>();
			for (AnimalConfig config : source) configs.putIfAbsent(config.id.get(), config);
			current = new Cache(source, Map.copyOf(configs));
			cache = current;
		}
		AnimalConfig config = current.configs().get(BuiltInRegistries.ENTITY_TYPE.getKey(type));
		return config != null ? config : DefaultHolder.DEFAULT;
	}

	private record Cache(Set<? extends AnimalConfig> source, Map<Identifier, AnimalConfig> configs) {}

	private static class DefaultHolder {
		private static final AnimalConfig DEFAULT = new AnimalConfig();
	}
}
