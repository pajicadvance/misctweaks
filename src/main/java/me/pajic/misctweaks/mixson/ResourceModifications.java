package me.pajic.misctweaks.mixson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.pajic.misctweaks.MiscTweaks;
import net.ramixin.mixson.debug.DebugMode;
import net.ramixin.mixson.inline.Mixson;

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

	public static void init() {
		//? if < 1.21.10 {
        /*if (MiscTweaks.CONFIG.lodestoneChangesBackport.get()) {
            Mixson.registerEvent(
                    Mixson.DEFAULT_PRIORITY,
                    rl -> rl.toString().equals("minecraft:recipe/lodestone"),
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
									//? if fabric
                                    .addProperty("item", "minecraft:iron_ingot");
									//? if neoforge
									//.addProperty("tag", "c:ingots/iron");
                        }
                        else {
                            MiscTweaks.LOGGER.error("MiscTweaks tried to modify the lodestone recipe, but it has been replaced by another mod or a datapack. Skipping patch.");
                        }
                    },
					true
            );
            Mixson.registerEvent(
                    Mixson.DEFAULT_PRIORITY,
                    rl -> rl.toString().equals("minecraft:loot_table/chests/ruined_portal"),
                    "misctweaks:add_lodestone_pool_to_ruined_portal_chest",
                    context -> context.getFile().getAsJsonObject()
                            .getAsJsonArray("pools")
                            .add(lodestonePool.deepCopy()),
					true
            );
            Mixson.registerEvent(
                    Mixson.DEFAULT_PRIORITY,
                    rl -> rl.toString().equals("minecraft:advancement/recipes/decorations/lodestone"),
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
                    },
					true
            );
            Mixson.registerEvent(
                    Mixson.DEFAULT_PRIORITY,
                    rl -> rl.toString().equals("minecraft:advancement/nether/use_lodestone"),
                    "misctweaks:move_use_lodestone_advancement",
                    context -> context.getFile().getAsJsonObject()
                            .addProperty("parent", "minecraft:adventure/root"),
					true
            );
        }
        *///?}
	}
}
