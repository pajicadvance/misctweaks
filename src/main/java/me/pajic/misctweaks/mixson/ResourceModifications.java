package me.pajic.misctweaks.mixson;

import com.google.gson.*;
import me.pajic.misctweaks.config.ModClientConfig;
import me.pajic.misctweaks.config.ModServerConfig;
import net.neoforged.fml.loading.FMLLoader;
import net.ramixin.mixson.debug.DebugMode;
import net.ramixin.mixson.inline.Mixson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceModifications {

    public static final JsonElement lodestonePool = JsonParser.parseString("""
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

    private static final Logger LOGGER = LoggerFactory.getLogger("MiscTweaks-ResourceModifications");

    public static void init() {
        if (!FMLLoader.isProduction()) Mixson.setDebugMode(DebugMode.EXPORT);
        if (ModServerConfig.lodestoneChangesBackport) {
            Mixson.registerEvent(
                    Mixson.DEFAULT_PRIORITY,
                    "minecraft:recipe/lodestone",
                    "misctweaks:modify_lodestone_recipe",
                    context -> {
                        JsonElement value = context.getFile().getAsJsonObject()
                                .getAsJsonObject("key")
                                .get("#");
                        if (value != null) {
                            if (value.isJsonPrimitive()) context.getFile().getAsJsonObject()
                                    .getAsJsonObject("key")
                                    .addProperty("#", "minecraft:iron_ingot");
                            else context.getFile().getAsJsonObject()
                                    .getAsJsonObject("key")
                                    .getAsJsonObject("#")
                                    .addProperty("tag", "c:ingots/iron");
                        }
                        else {
                            LOGGER.error("MiscTweaks tried to modify the lodestone recipe, but it has been replaced by another mod or a datapack. Skipping patch.");
                        }
                    }
            );
            Mixson.registerEvent(
                    Mixson.DEFAULT_PRIORITY,
                    "minecraft:loot_table/chests/ruined_portal",
                    "misctweaks:add_lodestone_pool_to_ruined_portal_chest",
                    context -> context.getFile().getAsJsonObject()
                            .getAsJsonArray("pools")
                            .add(lodestonePool.deepCopy())
            );
            Mixson.registerEvent(
                    Mixson.DEFAULT_PRIORITY,
                    "minecraft:advancement/recipes/decorations/lodestone",
                    "misctweaks:modify_lodestone_recipe_unlock_condition",
                    context -> {
                        JsonArray items = context.getFile().getAsJsonObject()
                                .getAsJsonObject("criteria")
                                .getAsJsonObject("has_netherite_ingot")
                                .getAsJsonObject("conditions")
                                .getAsJsonArray("items");
                        for (int i = 0; i < items.size(); ++i) {
                            JsonObject item = items.get(i).getAsJsonObject();
                            if (item.get("items").getAsString().equals("minecraft:netherite_ingot")) {
                                item.addProperty("items", "minecraft:iron_ingot");
                                break;
                            }
                        }
                    }
            );
            Mixson.registerEvent(
                    Mixson.DEFAULT_PRIORITY,
                    "minecraft:advancement/nether/use_lodestone",
                    "misctweaks:move_use_lodestone_advancement",
                    context -> context.getFile().getAsJsonObject()
                            .addProperty("parent", "minecraft:adventure/root")
            );
        }
    }

    public static void clientInit() {
        if (ModClientConfig.lowerShield) ModClientConfig.shields.forEach(s -> {
            String[] split = s.split(":");
            if (split.length == 2) {
                Mixson.registerEvent(
                        Mixson.DEFAULT_PRIORITY,
                        split[0] + ":models/item/" + split[1],
                        "misctweaks:modify_" + split[0] + "_" + split[1] + "_model",
                        context -> {
                            if (
                                    context.getFile().getAsJsonObject().has("display") &&
                                    context.getFile().getAsJsonObject().getAsJsonObject("display")
                                            .has("firstperson_lefthand")
                            ) {
                                context.getFile().getAsJsonObject()
                                        .getAsJsonObject("display")
                                        .getAsJsonObject("firstperson_lefthand")
                                        .getAsJsonArray("translation")
                                        .set(1, new JsonPrimitive(-4));
                            }
                        }
                );
            }
        });
    }
}
