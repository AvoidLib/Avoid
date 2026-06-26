package pl.olafcio.avoid.mods.event;

public abstract class Forceable {
    private Boolean force = null;
    private final boolean naturalValue;

    protected Forceable(boolean naturalValue) {
        this.naturalValue = naturalValue;
    }

    public final void naturalize() {
        setForce(getNaturalValue());
    }

    public final void succeed() {
        setForce(true);
    }

    public final void fail() {
        setForce(false);
    }

    public Boolean getForce() {
        return force;
    }

    public void setForce(boolean value) {
        this.force = value;
    }

    public boolean isForced() {
        return getForce() != null;
    }

    public boolean willSucceed() {
        return getForce() == true;
    }

    public boolean willFail() {
        return getForce() == false;
    }

    public boolean wouldNaturallySucceed() {
        return getNaturalValue();
    }

    public boolean wouldNaturallyFail() {
        return !getNaturalValue();
    }

    public boolean getNaturalValue() {
        return this.naturalValue;
    }
}
