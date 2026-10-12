package pl.olafcio.avoid_impl.net.entity_type;

import com.google.common.collect.ImmutableSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import pl.olafcio.avoid_impl.net.entity_type.save.SaveController;
import pl.olafcio.avoid_impl.net.entity_type.sync.Controller;

import java.util.ArrayList;
import java.util.Optional;

@ApiStatus.Internal
public final class AvoidEntityType<T extends @NotNull Entity> extends EntityType<T> {
    public final ArrayList<Controller<?, ?, ?>> synchs;
    public final ArrayList<SaveController<?, ?, ?>> saved;
    public final int lastID;

    AvoidEntityType(ArrayList<Controller<?, ?, ?>> synchs, int lastID, ArrayList<SaveController<?, ?, ?>> saved, EntityFactory<T> entityFactory, MobCategory mobCategory, boolean bl, boolean bl2, boolean bl3, boolean bl4, ImmutableSet<Block> immutableSet, EntityDimensions entityDimensions, float f, int i, int j, String string, Optional<ResourceKey<LootTable>> optional, FeatureFlagSet featureFlagSet, boolean bl5) {
        super(entityFactory, mobCategory, bl, bl2, bl3, bl4, immutableSet, entityDimensions, f, i, j, string, optional, featureFlagSet, bl5);

        this.synchs = synchs;
        this.saved = saved;

        this.lastID = lastID;
    }
}
