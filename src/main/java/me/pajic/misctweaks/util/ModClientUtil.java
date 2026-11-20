package me.pajic.misctweaks.util;

import com.mojang.blaze3d.vertex.PoseStack;
import me.pajic.misctweaks.MiscTweaksClient;
import me.pajic.misctweaks.config.LoweredShieldEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ModClientUtil {

	public static int getOrCreateDimensionBrightness(@Nullable Identifier id) {
		if (id != null) {
			String idString = id.toString();
			if (MiscTweaksClient.CONFIG.perDimensionBrightness.get().containsKey(idString)) {
                return MiscTweaksClient.CONFIG.perDimensionBrightness.get().get(idString);
            }
			updateBrightnessMap(idString, -1);
			return -1;
		}
		return -2;
	}

	public static int getCurrentBrightness() {
		return getOrCreateDimensionBrightness(getCurrentDimensionId());
	}

	public static void saveCurrentBrightness(int brightness) {
		Identifier id = getCurrentDimensionId();
		if (id != null) updateBrightnessMap(id.toString(), brightness);
	}

	public static void updateBrightnessMap(String id, int brightness) {
		Map<String, Integer> updated = new HashMap<>(MiscTweaksClient.CONFIG.perDimensionBrightness.get());
		updated.put(id, brightness);
		MiscTweaksClient.CONFIG.perDimensionBrightness.validateAndSet(updated);
	}

	public static boolean shouldRaiseHotbar() {
		return MiscTweaksClient.CONFIG.raiseHotbarPixels.get() > 0 && !MiscTweaksClient.RAISED_LOADED;
	}

	public static int getRaisePixels() {
		return MiscTweaksClient.CONFIG.raiseHotbarPixels.get();
	}

	public static void setRaisePixels(int pixels) {
		MiscTweaksClient.CONFIG.raiseHotbarPixels.validateAndSet(pixels);
	}

	public static boolean getLowerShield() {
		return MiscTweaksClient.CONFIG.lowerShield.get();
	}

	public static void setLowerShield(boolean lowerShield) {
		MiscTweaksClient.CONFIG.lowerShield.validateAndSet(lowerShield);
	}

	public static boolean getLimitAmbientSoundRange() {
		return MiscTweaksClient.CONFIG.limitAmbientSoundRange.get();
	}

	public static void setLimitAmbientSoundRange(boolean limitAmbientSoundRange) {
		MiscTweaksClient.CONFIG.limitAmbientSoundRange.validateAndSet(limitAmbientSoundRange);
	}

	public static void applyShieldOffset(InteractionHand hand, boolean usingItem, ItemStack itemStack, PoseStack poseStack) {
		if (hand != InteractionHand.OFF_HAND || usingItem || itemStack.isEmpty() || !MiscTweaksClient.CONFIG.lowerShield.get()) return;
		Identifier id = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
		for (LoweredShieldEntry entry : MiscTweaksClient.CONFIG.shields.get()) {
			if (entry.id.get().equals(id)) {
				poseStack.translate(0.0F, entry.offset.get() / 16.0F, 0.0F);
				return;
			}
		}
	}

	public static boolean isInSoundRange(double x, double y, double z, int range) {
		Player player = Minecraft.getInstance().player;
		return player != null && player.distanceToSqr(x, y, z) <= (double) range * range;
	}

	public static void saveConfig() {
		MiscTweaksClient.CONFIG.save();
	}

	private @Nullable static Identifier getCurrentDimensionId() {
		ClientLevel level = Minecraft.getInstance().level;
		return level != null ? level.dimension().identifier() : null;
	}
}
