package pl.olafcio.avoid.net.services.list.session;

import org.jetbrains.annotations.Nullable;

public record PlayerTextures(
        @Nullable PlayerTexture skin,
        @Nullable PlayerTexture cape,
        @Nullable PlayerTexture elytra,
        SignatureSpec signatureState
) {
    public static final PlayerTextures EMPTY
                  = new PlayerTextures(null, null, null, SignatureSpec.SIGNED);
}
