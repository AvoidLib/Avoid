package pl.olafcio.avoid.net.keyboard.bind;

import net.minecraft.client.KeyMapping;
import pl.olafcio.avoid.annotations.dist.MCEnvironment;

@MCEnvironment("client")
record InternalCategory(KeyMapping.Category category) {}
