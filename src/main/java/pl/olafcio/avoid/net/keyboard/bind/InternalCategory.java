package pl.olafcio.avoid.net.keyboard.bind;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;

@Environment(EnvType.CLIENT)
record InternalCategory(KeyMapping.Category category) {}
