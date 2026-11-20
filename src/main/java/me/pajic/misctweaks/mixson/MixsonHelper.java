package me.pajic.misctweaks.mixson;

import com.google.gson.JsonElement;
import me.pajic.misctweaks.MiscTweaks;
import me.pajic.misctweaks.platform.MultiLoaderUtil;
import net.ramixin.mixson.Mixson;
import net.ramixin.mixson.MixsonCodecs;
import net.ramixin.mixson.enums.DebugOption;
import net.ramixin.mixson.enums.ErrorPolicy;
import net.ramixin.mixson.enums.Lifetime;
import net.ramixin.mixson.util.Index;
import net.ramixin.mixson.util.functions.Event;

import java.awt.image.BufferedImage;
import java.util.function.Predicate;

public class MixsonHelper {

	private static final ErrorPolicy ERROR_POLICY = MultiLoaderUtil.INSTANCE.isDevEnv() ? ErrorPolicy.THROW : ErrorPolicy.LOG;

	public static void setDebugFlags() {
		if (MultiLoaderUtil.INSTANCE.isDevEnv()) {
			Mixson.enableDebugOption(DebugOption.BASIC_LOGGING);
			Mixson.enableDebugOption(DebugOption.EXTRA_LOGGING);
			Mixson.enableDebugOption(DebugOption.EXPORT_PATCHED_FILE);
		}
	}

    public static void registerSingleJson(String eventName, Index target, Event<JsonElement> event) {
        Mixson.registerEvent(
                MixsonCodecs.JSON_ELEMENT,
                Mixson.DEFAULT_PRIORITY,
                Lifetime.PERSISTENT,
                ERROR_POLICY,
                eventName,
                index -> index.idEquals(target),
                guardJson(eventName, event)
        );
    }

	public static void registerMultiJson(String eventName, Predicate<Index> resourcePredicate, Event<JsonElement> event) {
		Mixson.registerEvent(
				MixsonCodecs.JSON_ELEMENT,
				Mixson.DEFAULT_PRIORITY,
				Lifetime.PERSISTENT,
				ERROR_POLICY,
				eventName,
				resourcePredicate,
				guardJson(eventName, event)
		);
	}

	public static void registerSingleTexture(String eventName, String target, Event<BufferedImage> event) {
		Mixson.registerEvent(
				MixsonCodecs.PNG,
				Mixson.DEFAULT_PRIORITY,
				Lifetime.PERSISTENT,
				ERROR_POLICY,
				eventName,
				index -> index.idEquals(new Index(target)),
				guard(eventName, event)
		);
	}

	private static Event<JsonElement> guardJson(String eventName, Event<JsonElement> event) {
		if (MultiLoaderUtil.INSTANCE.isDevEnv()) return event;
		return context -> {
			JsonElement backup = context.getFile().deepCopy();
			try {
				event.runEvent(context);
			} catch (RuntimeException e) {
				context.setFile(backup);
				logFailure(eventName, context.getIndex(), e);
			}
		};
	}

	private static <T> Event<T> guard(String eventName, Event<T> event) {
		if (MultiLoaderUtil.INSTANCE.isDevEnv()) return event;
		return context -> {
			try {
				event.runEvent(context);
			} catch (RuntimeException e) {
				logFailure(eventName, context.getIndex(), e);
			}
		};
	}

	private static void logFailure(String eventName, Index index, RuntimeException e) {
		MiscTweaks.LOGGER.error("Failed to apply patch '{}' to {}, skipping. This is likely caused by another mod or data pack changing the same file.", eventName, index.id(), e);
	}
}
