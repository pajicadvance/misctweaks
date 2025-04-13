package me.pajic.misctweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.misctweaks.Main;
import me.pajic.misctweaks.config.ModServerConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LootItem.class)
public class LootItemMixin {

    @ModifyExpressionValue(
            method = "createItemStack",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/item/ItemStack;"
            )
    )
    private ItemStack swapMusicDisc(ItemStack original, @Local(argsOnly = true) LootContext lootContext) {
        if (ModServerConfig.randomizeDiscLoot && (original.is(Items.MUSIC_DISC_13) || original.is(Items.MUSIC_DISC_CAT))) {
            return new ItemStack(Main.DISCS.get(lootContext.getRandom().nextInt(Main.DISCS.size())));
        }
        return original;
    }
}
