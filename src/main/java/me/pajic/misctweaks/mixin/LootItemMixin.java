package me.pajic.misctweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.misctweaks.config.ModServerConfig;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(LootItem.class)
public class LootItemMixin {

    @Unique private final List<Item> discs = List.of(
            Items.MUSIC_DISC_13,
            Items.MUSIC_DISC_CAT,
            Items.MUSIC_DISC_BLOCKS,
            Items.MUSIC_DISC_CHIRP,
            Items.MUSIC_DISC_FAR,
            Items.MUSIC_DISC_MALL,
            Items.MUSIC_DISC_MELLOHI,
            Items.MUSIC_DISC_STAL,
            Items.MUSIC_DISC_STRAD,
            Items.MUSIC_DISC_WARD,
            Items.MUSIC_DISC_11,
            Items.MUSIC_DISC_WAIT
    );

    @ModifyExpressionValue(
            method = "createItemStack",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/core/Holder;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack swapMusicDisc(ItemStack original, @Local(argsOnly = true) LootContext lootContext) {
        if (ModServerConfig.randomizeDiscLoot && (original.is(Items.MUSIC_DISC_13) || original.is(Items.MUSIC_DISC_CAT))) {
            return new ItemStack(discs.get(lootContext.getRandom().nextInt(discs.size())));
        }
        return original;
    }
}
