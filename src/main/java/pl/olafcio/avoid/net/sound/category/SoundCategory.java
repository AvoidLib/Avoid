package pl.olafcio.avoid.net.sound.category;

public enum SoundCategory {
    MASTER("master"),
    MUSIC("music"),
    RECORDS("record"),
    WEATHER("weather"),
    BLOCKS("block"),
    HOSTILE("hostile"),
    NEUTRAL("neutral"),
    PLAYERS("player"),
    AMBIENT("ambient"),
    VOICE("voice"),
    UI("ui");

    private final String name;

    SoundCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
