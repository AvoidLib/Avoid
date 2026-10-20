package pl.olafcio.avoid_impl.mixininterface;

public interface IGuiGraphics {
    void avoidlib$tickDeltaOVR(Float value);

    void avoidlib$prevMouseX(int value);
    void avoidlib$prevMouseY(int value);

    int avoidlib$prevMouseX();
    int avoidlib$prevMouseY();
}
