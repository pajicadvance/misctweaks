package me.pajic.misctweaks.mixson;

import com.google.gson.JsonPrimitive;
import me.pajic.misctweaks.MiscTweaksClient;

public class AssetPatches {

	public static void init() {
		if (MiscTweaksClient.CONFIG.lowerShield.get()) MiscTweaksClient.CONFIG.shields.forEach(entry -> {
			String namespace = entry.id.get().getNamespace();
			String path = entry.id.get().getPath();
			MixsonHelper.registerMultiJsonPersistent(
					"Patch shield models",
					index -> index.id().getNamespace().equals(namespace) && index.id().getPath().equals("models/item/" + path),
					context -> {
						if (
								context.getFile().getAsJsonObject().has("display") &&
								context.getFile().getAsJsonObject().getAsJsonObject("display").has("firstperson_lefthand")
						) {
							context.getFile().getAsJsonObject()
									.getAsJsonObject("display")
									.getAsJsonObject("firstperson_lefthand")
									.getAsJsonArray("translation")
									.set(1, new JsonPrimitive(entry.offset.get()));
						}
					}
			);
		});
	}
}
