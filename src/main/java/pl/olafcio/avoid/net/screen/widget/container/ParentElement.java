package pl.olafcio.avoid.net.screen.widget.container;

import pl.olafcio.avoid.net.screen.widget.able.Renderable;

public interface ParentElement {
    //===============================//
    //== standard children methods ==//
    //===============================//

    void append(Renderable widget);
    void removeChild(Renderable widget);

    //===============================//
    //== extended children methods ==//
    //===============================//

    void prepend(Renderable widget);

    //==============================//
    //== viewing children methods ==//
    //==============================//

    Iterable<Renderable> children();
             Renderable  child(int index);
}
