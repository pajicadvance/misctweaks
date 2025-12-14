package me.pajic.misctweaks.mixson;

import com.google.gson.JsonPrimitive;
import me.pajic.misctweaks.MiscTweaksClient;
import net.ramixin.mixson.inline.Mixson;

public class ClientResourceModifications {

	private static boolean initialized = false;

	public static void init() {
		if (initialized) return;
		if (MiscTweaksClient.CONFIG.lowerShield.get()) MiscTweaksClient.CONFIG.shields.forEach(id -> {
			String namespace = id.getNamespace();
			String path = id.getPath();
			Mixson.registerEvent(
					Mixson.DEFAULT_PRIORITY,
					rl -> rl.getNamespace().equals(namespace) && rl.getPath().equals("models/item/" + path),
					"misctweaks:modify_" + namespace + "_" + path + "_model",
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
									.set(1, new JsonPrimitive(MiscTweaksClient.CONFIG.offset.get()));
						}
					},
					true
			);
		});
		initialized = true;
	}
}
