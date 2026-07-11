package pl.olafcio.avoid.net.entity_type.values;

import net.minecraft.world.entity.MobCategory;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;

@ApiStatus.Internal
@Native
public final class CategoryNative {
    @ApiStatus.Internal
    private CategoryNative() {}

    public static MobCategory convertFrom(Category category) {
             if (category == Category.MONSTER)                    return MobCategory.MONSTER;
        else if (category == Category.CREATURE)                   return MobCategory.CREATURE;
        else if (category == Category.AMBIENT)                    return MobCategory.AMBIENT;
        else if (category == Category.AXOLOTLS)                   return MobCategory.AXOLOTLS;
        else if (category == Category.UNDERGROUND_WATER_CREATURE) return MobCategory.UNDERGROUND_WATER_CREATURE;
        else if (category == Category.WATER_CREATURE)             return MobCategory.WATER_CREATURE;
        else if (category == Category.WATER_AMBIENT)              return MobCategory.WATER_AMBIENT;
        else if (category == Category.MISC)                       return MobCategory.MISC;

        throw new UnsupportedOperationException("Unknown Avoid category '" + category + "'");
    }
}
