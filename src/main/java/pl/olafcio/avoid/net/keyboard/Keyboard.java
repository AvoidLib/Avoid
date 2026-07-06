package pl.olafcio.avoid.net.keyboard;

/**
 * This code has been inspired from GLFW.<br/>
 * <br/>
 * Check them out here:<br/>
 * > <a href="https://github.com/glfw/glfw">https://github.com/glfw/glfw</a><br/>
 * <br/>
 * Here is their license:<br/>
 * <pre>
 * {@code
 * Copyright © 2002-2006 Marcus Geelnard
 *
 * Copyright © 2006-2019 Camilla Löwy
 *
 * This software is provided ‘as-is’, without any express or implied warranty. In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose, including commercial applications, and to alter it and redistribute it freely, subject to the following restrictions:
 *
 * The origin of this software must not be misrepresented; you must not claim that you wrote the original software. If you use this software in a product, an acknowledgment in the product documentation would be appreciated but is not required.
 *
 * Altered source versions must be plainly marked as such, and must not be misrepresented as being the original software.
 *
 * This notice may not be removed or altered from any source distribution.
 * }
 * </pre>
 */
public enum Keyboard {
    ;

    /** Printable keys. */
    public static final int
            SPACE         = 32,
            APOSTROPHE    = 39,
            COMMA         = 44,
            MINUS         = 45,
            PERIOD        = 46,
            SLASH         = 47;

    /** Digit keys. */
    public static final int
            NUM_0             = 48,
            NUM_1             = 49,
            NUM_2             = 50,
            NUM_3             = 51,
            NUM_4             = 52,
            NUM_5             = 53,
            NUM_6             = 54,
            NUM_7             = 55,
            NUM_8             = 56,
            NUM_9             = 57;

    /** Printable keys. */
    public static final int
            SEMICOLON     = 59,
            EQUAL         = 61;

    /** Letter keys. */
    public static final int
            A             = 65,
            B             = 66,
            C             = 67,
            D             = 68,
            E             = 69,
            F             = 70,
            G             = 71,
            H             = 72,
            I             = 73,
            J             = 74,
            K             = 75,
            L             = 76,
            M             = 77,
            N             = 78,
            O             = 79,
            P             = 80,
            Q             = 81,
            R             = 82,
            S             = 83,
            T             = 84,
            U             = 85,
            V             = 86,
            W             = 87,
            X             = 88,
            Y             = 89,
            Z             = 90;

    /** Printable keys. */
    public static final int
            LEFT_BRACKET  = 91,
            BACKSLASH     = 92,
            RIGHT_BRACKET = 93,
            GRAVE_ACCENT  = 96,
            WORLD_1       = 161,
            WORLD_2       = 162;

    /** Function keys. */
    public static final int
            ESCAPE        = 256,
            ENTER         = 257,
            TAB           = 258,
            BACKSPACE     = 259,
            INSERT        = 260,
            DELETE        = 261,
            RIGHT         = 262,
            LEFT          = 263,
            DOWN          = 264,
            UP            = 265,
            PAGE_UP       = 266,
            PAGE_DOWN     = 267,
            HOME          = 268,
            END           = 269,
            CAPS_LOCK     = 280,
            SCROLL_LOCK   = 281,
            NUM_LOCK      = 282,
            PRINT_SCREEN  = 283,
            PAUSE         = 284;

    /** F-keys. */
    public static final int
            F1            = 290,
            F2            = 291,
            F3            = 292,
            F4            = 293,
            F5            = 294,
            F6            = 295,
            F7            = 296,
            F8            = 297,
            F9            = 298,
            F10           = 299,
            F11           = 300,
            F12           = 301,
            F13           = 302,
            F14           = 303,
            F15           = 304,
            F16           = 305,
            F17           = 306,
            F18           = 307,
            F19           = 308,
            F20           = 309,
            F21           = 310,
            F22           = 311,
            F23           = 312,
            F24           = 313,
            F25           = 314;

    /** Numpad keys. */
    public static final int
            NUMPAD_0          = 320,
            NUMPAD_1          = 321,
            NUMPAD_2          = 322,
            NUMPAD_3          = 323,
            NUMPAD_4          = 324,
            NUMPAD_5          = 325,
            NUMPAD_6          = 326,
            NUMPAD_7          = 327,
            NUMPAD_8          = 328,
            NUMPAD_9          = 329,
            NUMPAD_DECIMAL    = 330,
            NUMPAD_DIVIDE     = 331,
            NUMPAD_MULTIPLY   = 332,
            NUMPAD_SUBTRACT   = 333,
            NUMPAD_ADD        = 334,
            NUMPAD_ENTER      = 335,
            NUMPAD_EQUAL      = 336;

    /** Modifier keys. */
    public static final int
            LEFT_SHIFT    = 340,
            LEFT_CONTROL  = 341,
            LEFT_ALT      = 342,
            LEFT_SUPER    = 343,
            RIGHT_SHIFT   = 344,
            RIGHT_CONTROL = 345,
            RIGHT_ALT     = 346,
            RIGHT_SUPER   = 347;

    /** Menu key. */
    public static final int
            MENU          = 348;
}
