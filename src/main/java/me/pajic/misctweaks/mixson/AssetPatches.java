package me.pajic.misctweaks.mixson;

import me.pajic.misctweaks.util.ModClientUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class AssetPatches {

	public static void init() {
		MixsonHelper.registerSingleTexture(
				"Patch hotbar selection sprite",
				"minecraft:textures/gui/sprites/hud/hotbar_selection",
				context -> {
					if (ModClientUtil.shouldRaiseHotbar()) {
						BufferedImage original = context.getFile();
						int width = original.getWidth();
						int height = original.getHeight();
						if (width % 24 == 0) {
							int rowsToCopy = width / 24;
							BufferedImage patched = new BufferedImage(width, height + rowsToCopy, BufferedImage.TYPE_INT_ARGB);
							Graphics2D g = patched.createGraphics();
							g.drawImage(original, 0, 0, null);
							g.dispose();
							int[] pixels = new int[width * rowsToCopy];
							original.getRGB(0, 0, width, rowsToCopy, pixels, 0, width);
							for (int i = 0; i < rowsToCopy; i++)
								patched.setRGB(
										0, height + (rowsToCopy - i - 1),
										width, 1,
										Arrays.copyOfRange(pixels, i * width, (i + 1) * width),
										0, width
								);
							context.setFile(patched);
						}
					}
				}
		);
	}
}
