package pl.olafcio.avoid.net.client.interaction;

import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;
import pl.olafcio.avoid.annotations.Native;
import pl.olafcio.avoid.net._3d.Direction;
import pl.olafcio.avoid.net.block.pos.BlockPos;
import pl.olafcio.avoid.net.block.pos.BlockPosNative;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity.EntityNative;
import pl.olafcio.avoid.net.player_server.values.HandType;
import pl.olafcio.avoid.net.player_server.values.HandTypeNative;
import pl.olafcio.avoid.net.world.vect3.IVect3;
import pl.olafcio.avoid.net.world.vect3.Vect3Native;

@Native
@ApiStatus.Internal
final class cim_native {
    @ApiStatus.Internal
    private cim_native() {}

    static boolean nah() {
        return Minecraft.getInstance().gameMode == null;
    }

    static void interact(Entity entity, HandType type) {
        Minecraft.getInstance().gameMode.interact(
                Minecraft.getInstance().player,
                EntityNative.convert(entity),  // at:interact
                HandTypeNative.convertFrom(type)  // at:interact
        );
    }

    static void interactAt(Entity entity, IVect3 location, HandType type) {
        var target = EntityNative.convert(entity);

        Minecraft.getInstance().gameMode.interactAt(
                Minecraft.getInstance().player,
                target,
                new EntityHitResult(target, Vect3Native.convertFrom(location)),
                HandTypeNative.convertFrom(type)
        );
    }

    static void attack(Entity entity) {
        Minecraft.getInstance().gameMode.attack(
                Minecraft.getInstance().player,
                EntityNative.convert(entity)
        );
    }

    static void destroyBlock(BlockPos blockPos) {
        Minecraft.getInstance().gameMode.destroyBlock(
                BlockPosNative.convertFrom(blockPos)
        );
    }

    static void startDestroyBlock(BlockPos blockPos, Direction direction) {
        Minecraft.getInstance().gameMode.startDestroyBlock(
                BlockPosNative.convertFrom(blockPos),
                net.minecraft.core.Direction.valueOf(direction.name())
        );
    }

    static void continueDestroyBlock(BlockPos blockPos, Direction direction) {
        Minecraft.getInstance().gameMode.continueDestroyBlock(
                BlockPosNative.convertFrom(blockPos),
                net.minecraft.core.Direction.valueOf(direction.name())
        );
    }

    static void stopDestroyBlock() {
        Minecraft.getInstance().gameMode.stopDestroyBlock();
    }

    static boolean isDestroying() {
        return Minecraft.getInstance().gameMode.isDestroying();
    }

    static void useItem(HandType hand) {
        Minecraft.getInstance().gameMode.useItem(
                Minecraft.getInstance().player,
                HandTypeNative.convertFrom(hand)
        );
    }

    static void useItemOn(HandType hand, BlockPos blockPos, Direction direction, boolean isMiss, boolean isInside, boolean isWorldBorder) {
        var pos = BlockPosNative.convertFrom(blockPos);

        Minecraft.getInstance().gameMode.useItemOn(
                Minecraft.getInstance().player,
                HandTypeNative.convertFrom(hand),
                new BlockHitResult(isMiss, Vec3.atBottomCenterOf(pos), net.minecraft.core.Direction.valueOf(direction.name()), pos, isInside, isWorldBorder)
        );
    }
}
