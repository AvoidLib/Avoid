package pl.olafcio.avoid.net.block.values;

public enum NoteBlockInstrument {
    HARP("harp", "NOTE_BLOCK_HARP"),
    BASEDRUM("basedrum", "NOTE_BLOCK_BASEDRUM"),
    SNARE("snare", "NOTE_BLOCK_SNARE"),
    HAT("hat", "NOTE_BLOCK_HAT"),
    BASS("bass", "NOTE_BLOCK_BASS"),
    FLUTE("flute", "NOTE_BLOCK_FLUTE"),
    BELL("bell", "NOTE_BLOCK_BELL"),
    GUITAR("guitar", "NOTE_BLOCK_GUITAR"),
    CHIME("chime", "NOTE_BLOCK_CHIME"),
    XYLOPHONE("xylophone", "NOTE_BLOCK_XYLOPHONE"),
    IRON_XYLOPHONE("iron_xylophone", "NOTE_BLOCK_IRON_XYLOPHONE"),
    COW_BELL("cow_bell", "NOTE_BLOCK_COW_BELL"),
    DIDGERIDOO("didgeridoo", "NOTE_BLOCK_DIDGERIDOO"),
    BIT("bit", "NOTE_BLOCK_BIT"),
    BANJO("banjo", "NOTE_BLOCK_BANJO"),
    PLING("pling", "NOTE_BLOCK_PLING"),
    ZOMBIE("zombie", "NOTE_BLOCK_IMITATE_ZOMBIE"),
    SKELETON("skeleton", "NOTE_BLOCK_IMITATE_SKELETON"),
    CREEPER("creeper", "NOTE_BLOCK_IMITATE_CREEPER"),
    DRAGON("dragon", "NOTE_BLOCK_IMITATE_ENDER_DRAGON"),
    WITHER_SKELETON("wither_skeleton", "NOTE_BLOCK_IMITATE_WITHER_SKELETON"),
    PIGLIN("piglin", "NOTE_BLOCK_IMITATE_PIGLIN"),
    CUSTOM_HEAD("custom_head", "UI_BUTTON_CLICK");

    public final String chime;

    NoteBlockInstrument(String chime, String soundEvent) {
        this.chime = chime;
    }
}
