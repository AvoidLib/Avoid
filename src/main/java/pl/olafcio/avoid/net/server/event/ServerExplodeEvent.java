package pl.olafcio.avoid.net.server.event;

import org.jspecify.annotations.Nullable;
import pl.olafcio.avoid.mods.event.Cancellable;
import pl.olafcio.avoid.net.entity.Entity;
import pl.olafcio.avoid.net.entity.values.Damage;
import pl.olafcio.avoid.net.id.Identification;
import pl.olafcio.avoid.net.world.World;
import pl.olafcio.avoid.net.world.vect3.IVect3;

public final class ServerExplodeEvent extends Cancellable {
    private final @Nullable Entity entity;
    private final @Nullable Damage damage;
    private final World world;
    private final IVect3 position;
    private float radius;
    private final float vanillaRadius;
    private final boolean fire;
    private final Identification sound;

    public ServerExplodeEvent(@Nullable Entity entity, @Nullable Damage damage, World world, IVect3 position, float radius, boolean fire, Identification sound) {
        this.entity = entity;
        this.damage = damage;
        this.world = world;
        this.position = position;
        this.radius = this.vanillaRadius = radius;
        this.fire = fire;
        this.sound = sound;
    }

    public @Nullable Entity getEntity() {
        return entity;
    }

    public @Nullable Damage getDamage() {
        return damage;
    }

    public World getWorld() {
        return world;
    }

    public IVect3 getPosition() {
        return position;
    }

    public float getRadius() {
        return radius;
    }

    public float getInitialRadius() {
        return vanillaRadius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public boolean isFire() {
        return fire;
    }

    public Identification getSound() {
        return sound;
    }
}
