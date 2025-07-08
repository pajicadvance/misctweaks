package me.pajic.misctweaks.config;

import me.fzzyhmstrs.fzzy_config.annotations.Action;
import me.fzzyhmstrs.fzzy_config.annotations.ClientModifiable;
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction;
import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedFloat;
import me.pajic.misctweaks.ClientMain;
import me.pajic.misctweaks.util.MultiVersionUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

@ClientModifiable
@Version(version = 1)
public class ModClientConfig extends Config {
    public ModClientConfig() {
        super(ClientMain.CLIENT_CONFIG_RL);
    }
    //? if > 1.20.1 {
    @RequiresAction(action = Action.RESTART)
    public ValidatedBoolean lowerShield = new ValidatedBoolean(true);
    @RequiresAction(action = Action.RESTART)
    public ValidatedList<ResourceLocation> shields = ValidatedIdentifier.ofRegistry(MultiVersionUtil.fromNamespaceAndPath("minecraft", "shield"), BuiltInRegistries.ITEM).toList(MultiVersionUtil.fromNamespaceAndPath("minecraft", "shield"));
    @RequiresAction(action = Action.RESTART)
    public ValidatedFloat offset = new ValidatedFloat(-4.0F, 0F, -5.0F);
    //?}
}
