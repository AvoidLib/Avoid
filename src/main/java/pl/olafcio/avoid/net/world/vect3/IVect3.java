package pl.olafcio.avoid.net.world.vect3;

public sealed interface IVect3 permits Vect3, Vect3o {
    double x();
    double y();
    double z();

    IVect3 add(double x, double y, double z);
    IVect3 subtract(double x, double y, double z);
    IVect3 multiply(double x, double y, double z);
    IVect3 divide(double x, double y, double z);

    IVect3 add(double n);
    IVect3 subtract(double n);

    IVect3 withX(double x);
    IVect3 withY(double y);
    IVect3 withZ(double z);

    IVect3 floor();
    IVect3 round();
    IVect3 ceil();
}
