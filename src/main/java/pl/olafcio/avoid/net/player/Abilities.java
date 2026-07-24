package pl.olafcio.avoid.net.player;

/**
 * The abilities of a player.<br/>
 * For more info, see {@link Player#getAbilities}.
 */
public final class Abilities {
    private final net.minecraft.world.entity.player.Abilities object;

    Abilities(net.minecraft.world.entity.player.Abilities object) {
        this.object = object;
    }

    public boolean isFlying() {
        return object.flying;
    }

    public Abilities setFlying(boolean value) {
        object.flying = value;
        return this;
    }

    public boolean isAllowedToFly() {
        return object.mayfly;
    }

    public Abilities setAllowedToFly(boolean value) {
        object.mayfly = value;
        return this;
    }

    public boolean isInvulnerable() {
        return object.invulnerable;
    }

    public Abilities setInvulnerable(boolean value) {
        object.invulnerable = value;
        return this;
    }

    public boolean isAllowedToBuild() {
        return object.mayBuild;
    }

    public Abilities setAllowedToBuild(boolean value) {
        object.mayBuild = value;
        return this;
    }

    public float getFlySpeed() {
        return object.getFlyingSpeed();
    }

    public void setFlySpeed(float value) {
        object.setFlyingSpeed(value);
    }

    public float getWalkSpeed() {
        return object.getWalkingSpeed();
    }

    public void setWalkSpeed(float value) {
        object.setWalkingSpeed(value);
    }
}
