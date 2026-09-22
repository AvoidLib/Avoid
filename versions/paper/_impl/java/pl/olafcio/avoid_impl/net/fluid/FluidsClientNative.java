package pl.olafcio.avoid.net.fluid;

import pl.olafcio.avoid.annotations.dist.Dist;
import pl.olafcio.avoid.annotations.dist.OnlyIn;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@Native
@OnlyIn(Dist.CLIENT)
@ApiStatus.Internal
class FluidsClientNative {
    @ApiStatus.Internal
    private FluidsClientNative() {}

    static void handleLayer(AvoidFluid source, Class<? extends Fluid> klass, AvoidFluid flowing) {
    }
}
