package pl.olafcio.avoid.net.block.values;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

import java.util.HashMap;

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

    private static final HashMap<NoteBlockInstrument, net.minecraft.world.level.block.state.properties.NoteBlockInstrument> MAP
                   = new HashMap<>();

    public final String chime;

    NoteBlockInstrument(String chime, String soundEvent) {
        this.chime = chime;
    }

    public static void clinit() {
        var avoids = values();
        var crafts = net.minecraft.world.level.block.state.properties.NoteBlockInstrument.values();

        for (var avoid : avoids) {
            for (var craft : crafts)
                if (craft.getSerializedName().equals(avoid.chime))
                    MAP.put(avoid, craft);

            for (var craft : crafts)
                if (craft.ordinal() == avoid.ordinal())
                    MAP.put(avoid, craft);
        }
    }

    static net.minecraft.world.level.block.state.properties.NoteBlockInstrument getObject(NoteBlockInstrument avoid) {
        return MAP.get(avoid);
    }
}
