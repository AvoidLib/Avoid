package pl.olafcio.avoid.net.screen;

public sealed interface WidgetMarker {
    enum TitleScreen implements WidgetMarker {
        SINGLEPLAYER,
        MULTIPLAYER,
        REALMS,
        OPTIONS,
        QUIT_GAME
    }

    enum Multiplayer implements WidgetMarker {
        SELECT_BUTTON,
        EDIT_BUTTON,
        DELETE_BUTTON
    }

    enum Singleplayer implements WidgetMarker {
        SELECT_BUTTON,
        DELETE_BUTTON,
        RENAME_BUTTON,
        COPY_BUTTON,
        SEARCH_BOX
    }
}
