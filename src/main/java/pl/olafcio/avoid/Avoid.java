package pl.olafcio.avoid;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;

public class Avoid implements ModInitializer {
    @ApiStatus.Internal
    public static Minecraft mc;

    @ApiStatus.Internal
    public static final Logger LOGGER
                      = LogUtils.getLogger();

    @Override
    public void onInitialize() {}
}
