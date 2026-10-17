package pl.olafcio.avoid.net.permission;

import java.util.Objects;

/**
 * A vote for whether an executor has a permission.
 */
public final class PermissionStatus {
    private final Boolean has;
    private final int priority;

    /**
     * @param has      Whether the vote is for or against the permission.
     * @param priority The priority of the vote compared to other votes.
     */
    public PermissionStatus(boolean has, int priority) {
        this.has = has;
        this.priority = priority;
    }

    public PermissionStatus(boolean has) {
        this(has, 1);
    }

    private PermissionStatus() {
        this.has = null;
        this.priority = 0;
    }

    /**
     * Return this if you don't want to participate in the vote.<br/>
     * The decision will be left to the remaining voters, and if none, Minecraft.
     */
    public static final PermissionStatus IGNORE
                  = new PermissionStatus();

    public Boolean has() {
        return has;
    }

    public int priority() {
        return priority;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PermissionStatus) obj;
        return Objects.equals(this.has, that.has) &&
                this.priority == that.priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(has, priority);
    }

    @Override
    public String toString() {
        return "PermissionStatus[" +
                "has=" + has + ", " +
                "priority=" + priority + ']';
    }

}
