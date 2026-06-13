package pl.olafcio.avoid.net.block.values;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

import java.util.HashMap;

public enum NoteBlockInstrument {
    HARP("harp", SoundEvents.NOTE_BLOCK_HARP),
    BASEDRUM("basedrum", SoundEvents.NOTE_BLOCK_BASEDRUM),
    SNARE("snare", SoundEvents.NOTE_BLOCK_SNARE),
    HAT("hat", SoundEvents.NOTE_BLOCK_HAT),
    BASS("bass", SoundEvents.NOTE_BLOCK_BASS),
    FLUTE("flute", SoundEvents.NOTE_BLOCK_FLUTE),
    BELL("bell", SoundEvents.NOTE_BLOCK_BELL),
    GUITAR("guitar", SoundEvents.NOTE_BLOCK_GUITAR),
    CHIME("chime", SoundEvents.NOTE_BLOCK_CHIME),
    XYLOPHONE("xylophone", SoundEvents.NOTE_BLOCK_XYLOPHONE),
    IRON_XYLOPHONE("iron_xylophone", SoundEvents.NOTE_BLOCK_IRON_XYLOPHONE),
    COW_BELL("cow_bell", SoundEvents.NOTE_BLOCK_COW_BELL),
    DIDGERIDOO("didgeridoo", SoundEvents.NOTE_BLOCK_DIDGERIDOO),
    BIT("bit", SoundEvents.NOTE_BLOCK_BIT),
    BANJO("banjo", SoundEvents.NOTE_BLOCK_BANJO),
    PLING("pling", SoundEvents.NOTE_BLOCK_PLING),
    ZOMBIE("zombie", SoundEvents.NOTE_BLOCK_IMITATE_ZOMBIE),
    SKELETON("skeleton", SoundEvents.NOTE_BLOCK_IMITATE_SKELETON),
    CREEPER("creeper", SoundEvents.NOTE_BLOCK_IMITATE_CREEPER),
    DRAGON("dragon", SoundEvents.NOTE_BLOCK_IMITATE_ENDER_DRAGON),
    WITHER_SKELETON("wither_skeleton", SoundEvents.NOTE_BLOCK_IMITATE_WITHER_SKELETON),
    PIGLIN("piglin", SoundEvents.NOTE_BLOCK_IMITATE_PIGLIN),
    CUSTOM_HEAD("custom_head", SoundEvents.UI_BUTTON_CLICK);

    private static final HashMap<NoteBlockInstrument, net.minecraft.world.level.block.state.properties.NoteBlockInstrument> MAP
                   = new HashMap<>();

    public final String chime;

    NoteBlockInstrument(String chime, Holder.Reference<SoundEvent> noteBlockChime) {
        this.chime = chime;
    }

    static {
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
