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

    /**
     * Returns whether the entity is flying.
     */
    public boolean isFlying() {
        return object.flying;
    }

    /**
     * Sets the entity's flying state.
     */
    public Abilities setFlying(boolean value) {
        object.flying = value;
        return this;
    }

    /**
     * Returns whether the entity is allowed to fly.
     */
    public boolean isAllowedToFly() {
        return object.mayfly;
    }

    /**
     * Sets the entity's fly allowance state.
     */
    public Abilities setAllowedToFly(boolean value) {
        object.mayfly = value;
        return this;
    }

    /**
     * Returns whether the entity is invulnerable.
     */
    public boolean isInvulnerable() {
        return object.invulnerable;
    }

    /**
     * Sets the entity's invulnerability state.
     */
    public Abilities setInvulnerable(boolean value) {
        object.invulnerable = value;
        return this;
    }

    /**
     * Returns whether the entity is allowed to build.
     */
    public boolean isAllowedToBuild() {
        return object.mayBuild;
    }

    /**
     * Sets the entity's build allowance state.
     */
    public Abilities setAllowedToBuild(boolean value) {
        object.mayBuild = value;
        return this;
    }

    /**
     * Returns the entity's fly speed.
     */
    public float getFlySpeed() {
        return object.getFlyingSpeed();
    }

    /**
     * Sets the entity's fly speed.
     */
    public void setFlySpeed(float value) {
        object.setFlyingSpeed(value);
    }

    /**
     * Returns the entity's walk speed.
     */
    public float getWalkSpeed() {
        return object.getWalkingSpeed();
    }

    /**
     * Sets the entity's walk speed.
     */
    public void setWalkSpeed(float value) {
        object.setWalkingSpeed(value);
    }
}
