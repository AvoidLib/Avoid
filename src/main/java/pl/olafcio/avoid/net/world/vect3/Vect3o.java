package pl.olafcio.avoid.net.world.vect3;

import java.util.Objects;

public final class Vect3o implements IVect3 {
    public static final Vect3o ZERO = new Vect3o(0d, 0d, 0d);

    public double x;
    public double y;
    public double z;

    public Vect3o(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vect3o add(double x, double y, double z) {
        return new Vect3o(this.x + x, this.y + y, this.z + z);
    }

    public Vect3o subtract(double x, double y, double z) {
        return new Vect3o(this.x - x, this.y - y, this.z - z);
    }

    public Vect3o multiply(double x, double y, double z) {
        return new Vect3o(this.x * x, this.y * y, this.z * z);
    }

    public Vect3o divide(double x, double y, double z) {
        return new Vect3o(this.x / x, this.y / y, this.z / z);
    }

    public Vect3o add(double n) {
        return new Vect3o(this.x + n, this.y + n, this.z + n);
    }

    public Vect3o subtract(double n) {
        return new Vect3o(this.x - n, this.y - n, this.z - n);
    }

    public Vect3o withX(double x) {
        return new Vect3o(x, y, z);
    }

    public Vect3o withY(double y) {
        return new Vect3o(x, y, z);
    }

    public Vect3o withZ(double z) {
        return new Vect3o(x, y, z);
    }

    public Vect3o floor() {
        return new Vect3o(Math.floor(x), Math.floor(y), Math.floor(z));
    }

    public Vect3o round() {
        return new Vect3o(Math.round(x), Math.round(y), Math.round(z));
    }

    public Vect3o ceil() {
        return new Vect3o(Math.ceil(x), Math.ceil(y), Math.ceil(z));
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Vect3o) obj;
        return Double.doubleToLongBits(this.x) == Double.doubleToLongBits(that.x) &&
                Double.doubleToLongBits(this.y) == Double.doubleToLongBits(that.y) &&
                Double.doubleToLongBits(this.z) == Double.doubleToLongBits(that.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "Vect3o[" +
                "x=" + x + ", " +
                "y=" + y + ", " +
                "z=" + z + ']';
    }
}
