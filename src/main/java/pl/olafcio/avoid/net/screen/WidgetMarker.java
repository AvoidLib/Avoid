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

    enum Options implements WidgetMarker {
        SKIN_CUSTOMIZATION,
        SOUNDS,
        VIDEO,
        CONTROLS,
        LANGUAGE,
        CHAT,
        RESOURCE_PACK,
        ACCESSIBILITY,
        TELEMETRY,
        CREDITS_AND_ATTRIBUTION
    }

    enum PauseScreen implements WidgetMarker {
        BACK_TO_GAME_BUTTON,
        ADVANCEMENTS_BUTTON,
        STATS_BUTTON,
        OPTIONS_BUTTON,
        OPEN_TO_LAN_BUTTON,
        PLAYER_REPORTING_BUTTON,
        DISCONNECT_BUTTON
    }
}
