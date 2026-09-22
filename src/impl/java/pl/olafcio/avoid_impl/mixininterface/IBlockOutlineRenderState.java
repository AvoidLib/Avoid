package pl.olafcio.avoid_impl.mixininterface;

public interface IBlockOutlineRenderState {
    //#########//
    // GETTERS //
    //#########//

    Integer color         ();
    Integer secondaryColor();

    Integer          color_highcontrast();
    Integer secondaryColor_highcontrast();

    Float lineWidth          ();
    float lineWidth_secondary();

    //#########//
    // SETTERS //
    //#########//

    void color         (Integer value);
    void secondaryColor(Integer value);

    void          color_highcontrast(Integer value);
    void secondaryColor_highcontrast(Integer value);

    void lineWidth          (Float value);
    void lineWidth_secondary(float value);
}
