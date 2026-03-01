package me.pajic.misctweaks.mixin;

import me.pajic.misctweaks.ModUtil;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.misctweaks.MiscTweaks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//? if < 1.21.10
//import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

@Mixin(LootItem.class)
public abstract class LootItemMixin {

    @SuppressWarnings("unchecked")
	@ModifyArg(
            method = "createItemStack",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"
            )
    )
    private <T> T swapLootItem(T stack, @Local(argsOnly = true) LootContext lootContext) {
		ItemStack original = (ItemStack) stack;
        if (MiscTweaks.CONFIG.randomizeDiscLoot.get() && (original.is(Items.MUSIC_DISC_13) || original.is(Items.MUSIC_DISC_CAT))) {
            return (T) new ItemStack(ModUtil.DISCS.get(lootContext.getRandom().nextInt(ModUtil.DISCS.size())));
        }
        //? if < 1.21.10 {
        /*if (MiscTweaks.CONFIG.craftableSaddleBackport.get() && original.is(Items.SADDLE)) {
            ItemStack leather = new ItemStack(Items.LEATHER);
            leather.setCount(UniformGenerator.between(1.0F, 5.0F).getInt(lootContext));
            return (T) leather;
        }
        *///?}
        return (T) original;
    }
}
