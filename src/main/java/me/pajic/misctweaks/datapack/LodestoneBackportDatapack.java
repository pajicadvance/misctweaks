package me.pajic.misctweaks.datapack;

//? if = 1.20.1 {
/*import me.pajic.misctweaks.Main;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
*///?}

public class LodestoneBackportDatapack {
    public static void init() {
        //? if = 1.20.1 {
        /*if (Main.CONFIG.lodestoneChangesBackport()) {
            FabricLoader.getInstance().getModContainer("misctweaks").ifPresent(modContainer ->
                    ResourceManagerHelper.registerBuiltinResourcePack(
                            new ResourceLocation("misctweaks:lodestone_backport"),
                            modContainer,
                            ResourcePackActivationType.ALWAYS_ENABLED
                    )
            );
            LootTableEvents.MODIFY.register((resourceManager, lootDataManager, resourceLocation, builder, lootTableSource) -> {
                if (lootTableSource.isBuiltin() && BuiltInLootTables.RUINED_PORTAL.equals(resourceLocation)) {
                    builder.pool(LootPool.lootPool()
                            .add(EmptyLootItem.emptyItem())
                            .add(LootItem.lootTableItem(Items.LODESTONE).setWeight(2))
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F), false))
                            .setBonusRolls(ConstantValue.exactly(0.0F))
                            .setRolls(ConstantValue.exactly(1.0F))
                            .build()
                    );
                }
            });
        }
        *///?}
    }
}
