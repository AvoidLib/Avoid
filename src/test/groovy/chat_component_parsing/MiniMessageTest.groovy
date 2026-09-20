package chat_component_parsing

import org.junit.jupiter.api.Test
import pl.olafcio.avoid.net.chat.component.Colors
import pl.olafcio.avoid.net.chat.component.Components
import pl.olafcio.avoid.net.chat.component.type.ParentComponent

import static org.junit.jupiter.api.Assertions.*

class MiniMessageTest {
    @Test
    void main() {
        def component = Components.Parse.minimessage("<red>Tuff!")

        assertInstanceOf ParentComponent, component, "'component' isn't a ParentComponent"

        component = (ParentComponent) component

        assertTrue component.children.size() == 1, "expected exactly 1 child component"

        var style = component.children[0].style()

        assertTrue style.color.getRGB() == Colors.RED.getRGB(), "expected color RED, got ${style.color}"

        assertNull style.bold, "expected unset bold"
        assertNull style.italic, "expected unset italic"
        assertNull style.underlined, "expected unset underlined"
        assertNull style.strikethrough, "expected unset strikethrough"
        assertNull style.obfuscated, "expected unset obfuscated"

        var pstyle = component.style()

        assertNull pstyle.bold, "expected unset bold [parent]"
        assertNull pstyle.italic, "expected unset italic [parent]"
        assertNull pstyle.underlined, "expected unset underlined [parent]"
        assertNull pstyle.strikethrough, "expected unset strikethrough [parent]"
        assertNull pstyle.obfuscated, "expected unset obfuscated [parent]"
        assertNull pstyle.color, "expected unset color [parent]"
    }
}
