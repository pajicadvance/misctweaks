package me.pajic.misctweaks;

import me.pajic.misctweaks.mixson.ResourceModifications;
import net.fabricmc.api.ClientModInitializer;

public class ClientMain implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ResourceModifications.clientInit();
    }
}
