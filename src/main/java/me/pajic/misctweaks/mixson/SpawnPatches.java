package me.pajic.misctweaks.mixson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.pajic.misctweaks.MiscTweaks;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class SpawnPatches {

	private static final Set<String> OCELOT_BIOMES = Set.of(
			"minecraft:worldgen/biome/jungle",
			"minecraft:worldgen/biome/bamboo_jungle"
	);

	public static void init() {
		MixsonHelper.registerMultiJson(
				"Fix ocelots being counted as monsters during spawning",
				index -> OCELOT_BIOMES.contains(index.id().toString()),
				context -> {
					if (!MiscTweaks.CONFIG.ocelotSpawnFix.get()) return;
					JsonObject spawnsByCategory = getSpawnsByCategory(context.getFile());
					if (spawnsByCategory == null) {
						logSkipped(context.getIndex().id().toString());
						return;
					}
					JsonArray monsters = getArray(spawnsByCategory, "monster");
					if (monsters == null) return;
					JsonArray creatures = getArray(spawnsByCategory, "creature");
					if (creatures == null) {
						creatures = new JsonArray();
						spawnsByCategory.add("creature", creatures);
					}
					for (int i = monsters.size() - 1; i >= 0; i--) {
						JsonElement spawn = monsters.get(i);
						if (isOcelot(spawn)) {
							monsters.remove(i);
							creatures.add(spawn);
						}
					}
				}
		);
	}

	private static @Nullable JsonObject getSpawnsByCategory(JsonElement biome) {
		JsonObject attributes = getObject(biome, "attributes");
		JsonObject naturalMobSpawns = getObject(attributes, "minecraft:gameplay/natural_mob_spawns");
		JsonObject spawnsByCategory = getObject(getObject(naturalMobSpawns, "argument"), "spawns_by_category");
		if (spawnsByCategory == null) spawnsByCategory = getObject(naturalMobSpawns, "spawns_by_category");
		return spawnsByCategory != null ? spawnsByCategory : getObject(biome, "spawners");
	}

	private static boolean isOcelot(JsonElement spawn) {
		if (!spawn.isJsonObject()) return false;
		JsonElement type = spawn.getAsJsonObject().get("type");
		return type != null && type.isJsonPrimitive() && type.getAsString().equals("minecraft:ocelot");
	}

	private static @Nullable JsonObject getObject(@Nullable JsonElement parent, String key) {
		if (parent == null || !parent.isJsonObject()) return null;
		JsonElement child = parent.getAsJsonObject().get(key);
		return child != null && child.isJsonObject() ? child.getAsJsonObject() : null;
	}

	private static @Nullable JsonArray getArray(@Nullable JsonElement parent, String key) {
		if (parent == null || !parent.isJsonObject()) return null;
		JsonElement child = parent.getAsJsonObject().get(key);
		return child != null && child.isJsonArray() ? child.getAsJsonArray() : null;
	}

	private static void logSkipped(String file) {
		MiscTweaks.LOGGER.warn("Ocelot spawn fix could not modify {} because it was changed by another mod or data pack. Skipping.", file);
	}
}
