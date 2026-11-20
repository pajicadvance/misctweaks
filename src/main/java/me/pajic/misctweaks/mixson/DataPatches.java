package me.pajic.misctweaks.mixson;

//? <26.1 {

/*import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.pajic.misctweaks.MiscTweaks;
import net.ramixin.mixson.util.Index;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public class DataPatches {

    private static final JsonElement LODESTONE_POOL = JsonParser.parseString("""
        {
          "bonus_rolls": 0.0,
          "entries": [
            {
              "type": "minecraft:empty"
            },
            {
              "type": "minecraft:item",
              "functions": [
                {
                  "add": false,
                  "count": {
                    "type": "minecraft:uniform",
                    "max": 2.0,
                    "min": 1.0
                  },
                  "function": "minecraft:set_count"
                }
              ],
              "name": "minecraft:lodestone",
              "weight": 2
            }
          ],
          "rolls": 1.0
        }
    """);

    private static final JsonElement LEATHER_COUNT_FUNCTION = JsonParser.parseString("""
        {
          "add": false,
          "count": {
            "type": "minecraft:uniform",
            "max": 5.0,
            "min": 1.0
          },
          "function": "minecraft:set_count"
        }
    """);

    private static final Set<String> SADDLE_LOOT_TABLES = Set.of(
            "minecraft:loot_table/chests/ancient_city",
            "minecraft:loot_table/chests/desert_pyramid",
            "minecraft:loot_table/chests/jungle_temple",
            "minecraft:loot_table/chests/simple_dungeon",
            "minecraft:loot_table/chests/stronghold_corridor"
    );

    public static void init() {
        if (MiscTweaks.CONFIG.lodestoneChangesBackport.get()) {
            MixsonHelper.registerSingleJson(
                    "Modify lodestone recipe",
                    new Index("minecraft:recipe/lodestone"),
                    context -> {
                        JsonObject key = getObject(context.getFile(), "key");
                        if (key == null || !key.has("#")) {
                            logSkipped("lodestone recipe");
                            return;
                        }
                        JsonObject ingredient = new JsonObject();
                        //? if fabric
                        ingredient.addProperty("item", "minecraft:iron_ingot");
                        //? if neoforge
                        //ingredient.addProperty("tag", "c:ingots/iron");
                        key.add("#", ingredient);
                    }
            );
            MixsonHelper.registerSingleJson(
                    "Add lodestones to ruined portal chests",
                    new Index("minecraft:loot_table/chests/ruined_portal"),
                    context -> {
                        JsonArray pools = getArray(context.getFile(), "pools");
                        if (pools == null) {
                            logSkipped("ruined portal loot table");
                            return;
                        }
                        if (!pools.contains(LODESTONE_POOL)) pools.add(LODESTONE_POOL.deepCopy());
                    }
            );
            MixsonHelper.registerSingleJson(
                    "Modify lodestone recipe unlock condition",
                    new Index("minecraft:advancement/recipes/decorations/lodestone"),
                    context -> {
                        JsonObject criteria = getObject(context.getFile(), "criteria");
                        if (criteria == null) {
                            logSkipped("lodestone recipe advancement");
                            return;
                        }
                        boolean patched = false;
                        for (Map.Entry<String, JsonElement> criterion : criteria.entrySet()) {
                            JsonArray items = getArray(getObject(criterion.getValue(), "conditions"), "items");
                            if (items == null) continue;
                            for (JsonElement element : items) {
                                if (!element.isJsonObject()) continue;
                                JsonElement predicateItems = element.getAsJsonObject().get("items");
                                if (
                                        predicateItems != null &&
                                        predicateItems.isJsonPrimitive() &&
                                        predicateItems.getAsString().equals("minecraft:netherite_ingot")
                                ) {
                                    element.getAsJsonObject().addProperty("items", "minecraft:iron_ingot");
                                    patched = true;
                                }
                            }
                        }
                        if (!patched) logSkipped("lodestone recipe advancement");
                    }
            );
            MixsonHelper.registerSingleJson(
                    "Move lodestone usage advancement",
                    new Index("minecraft:advancement/nether/use_lodestone"),
                    context -> {
                        if (context.getFile().isJsonObject()) context.getFile().getAsJsonObject().addProperty("parent", "minecraft:adventure/root");
                        else logSkipped("lodestone usage advancement");
                    }
            );
        }
        if (MiscTweaks.CONFIG.craftableSaddleBackport.get()) {
            MixsonHelper.registerMultiJson(
                    "Replace saddles with leather in chest loot",
                    index -> SADDLE_LOOT_TABLES.contains(index.id().toString()),
                    context -> {
                        JsonArray pools = getArray(context.getFile(), "pools");
                        boolean patched = false;
                        if (pools != null) {
                            for (JsonElement pool : pools) {
                                patched |= replaceSaddles(getArray(pool, "entries"));
                            }
                        }
                        if (!patched) logSkipped("Craftable saddle backport", context.getIndex().id().toString());
                    }
            );
        }
    }

    private static boolean replaceSaddles(@Nullable JsonArray entries) {
        if (entries == null) return false;
        boolean patched = false;
        for (JsonElement element : entries) {
            if (!element.isJsonObject()) continue;
            JsonObject entry = element.getAsJsonObject();
            patched |= replaceSaddles(getArray(entry, "children"));
            JsonElement name = entry.get("name");
            if (name == null || !name.isJsonPrimitive() || !name.getAsString().equals("minecraft:saddle")) continue;
            entry.addProperty("name", "minecraft:leather");
            JsonArray functions = new JsonArray();
            JsonArray oldFunctions = getArray(entry, "functions");
            if (oldFunctions != null) {
                for (JsonElement function : oldFunctions) {
                    JsonObject functionObject = function.isJsonObject() ? function.getAsJsonObject() : null;
                    JsonElement functionType = functionObject != null ? functionObject.get("function") : null;
                    if (functionType == null || !functionType.isJsonPrimitive() || !functionType.getAsString().equals("minecraft:set_count")) functions.add(function);
                }
            }
            functions.add(LEATHER_COUNT_FUNCTION.deepCopy());
            entry.add("functions", functions);
            patched = true;
        }
        return patched;
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
        logSkipped("Lodestone changes backport", file);
    }

    private static void logSkipped(String feature, String file) {
        MiscTweaks.LOGGER.warn("{} could not modify the {} because it was changed by another mod or data pack. Skipping this part of the backport.", feature, file);
    }
}
*///?}
