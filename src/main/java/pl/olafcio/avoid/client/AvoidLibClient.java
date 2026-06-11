package pl.olafcio.avoid.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.ApiStatus;

public class AvoidLibClient implements ClientModInitializer {
    @ApiStatus.Internal
    public static Minecraft mc;

    @Override
    public void onInitializeClient() {
    }
}
