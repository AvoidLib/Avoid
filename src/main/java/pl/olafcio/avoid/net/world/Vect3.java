package pl.olafcio.avoid.net.world;

public record Vect3(double x, double y, double z) implements IVect3 {
    public static final Vect3 ZERO = new Vect3(0d, 0d, 0d);

    public Vect3 add(double x, double y, double z) {
        return new Vect3(this.x + x, this.y + y, this.z + z);
    }

    public Vect3 subtract(double x, double y, double z) {
        return new Vect3(this.x - x, this.y - y, this.z - z);
    }

    public Vect3 multiply(double x, double y, double z) {
        return new Vect3(this.x * x, this.y * y, this.z * z);
    }

    public Vect3 divide(double x, double y, double z) {
        return new Vect3(this.x / x, this.y / y, this.z / z);
    }

    public Vect3 add(double n) {
        return new Vect3(this.x + n, this.y + n, this.z + n);
    }

    public Vect3 subtract(double n) {
        return new Vect3(this.x - n, this.y - n, this.z - n);
    }

    public Vect3 withX(double x) {
        return new Vect3(x, y, z);
    }

    public Vect3 withY(double y) {
        return new Vect3(x, y, z);
    }

    public Vect3 withZ(double z) {
        return new Vect3(x, y, z);
    }

    public Vect3 floor() {
        return new Vect3(Math.floor(x), Math.floor(y), Math.floor(z));
    }

    public Vect3 round() {
        return new Vect3(Math.round(x), Math.round(y), Math.round(z));
    }

    public Vect3 ceil() {
        return new Vect3(Math.ceil(x), Math.ceil(y), Math.ceil(z));
    }
}
