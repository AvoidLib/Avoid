package pl.olafcio.avoid.net._3d;

import org.joml.Vector3f;
import org.joml.Vector3i;

/**
 * A 3D non-half direction. For example, when you look at the sky, your direction is {@link #UP}.
 * <br/><br/>
 * These numbers generally round to the nearest direction.<br/>
 * Sometimes you kinda look left, yet it may say it's north.
 * <br/><br/>
 * These directions are the folowing:
 * <table cellspacing="8" style="border:1px solid green; margin-top:7.7px">
 *     <tr style="background: green; color: #000">
 *         <th>Enum name</th>
 *         <th>Alternative name</th>
 *         <th>Equation</th>
 *     </tr>
 *     <tr>
 *         <td style="border-right:1px solid green">{@link #DOWN}</td>
 *         <td style="border-right:1px solid green">bottom</td>
 *         <td>y - 1</td>
 *     </tr>
 *     <tr>
 *         <td style="border-right:1px solid green">{@link #UP}</td>
 *         <td style="border-right:1px solid green">top</td>
 *         <td>y + 1</td>
 *     </tr>
 *     <tr>
 *         <td style="border-right:1px solid green">{@link #NORTH}</td>
 *         <td style="border-right:1px solid green">forward</td>
 *         <td>z - 1</td>
 *     </tr>
 *     <tr>
 *         <td style="border-right:1px solid green">{@link #SOUTH}</td>
 *         <td style="border-right:1px solid green">back</td>
 *         <td>z + 1</td>
 *     </tr>
 *     <tr>
 *         <td style="border-right:1px solid green">{@link #WEST}</td>
 *         <td style="border-right:1px solid green">left</td>
 *         <td>x - 1</td>
 *     </tr>
 *     <tr>
 *         <td style="border-right:1px solid green">{@link #EAST}</td>
 *         <td style="border-right:1px solid green">right</td>
 *         <td>x + 1</td>
 *     </tr>
 * </table>
 */
public enum Direction {
    DOWN(1, "down", Sign.Nega, Axis.Y, new Vector3i(0, -1, 0)),
    UP(0, "up", Sign.Posi, Axis.Y, new Vector3i(0, 1, 0)),
    NORTH(3, "north", Sign.Nega, Axis.Z, new Vector3i(0, 0, -1)),
    SOUTH(2, "south", Sign.Posi, Axis.Z, new Vector3i(0, 0, 1)),
    WEST(5, "west", Sign.Nega, Axis.X, new Vector3i(-1, 0, 0)),
    EAST(4, "east", Sign.Posi, Axis.X, new Vector3i(1, 0, 0));

    public static final Direction[] values = Direction.values();

    private final int oppositeIndex;
    private final String name;
    private final Sign sign;
    private final Axis axis;
    private final Vector3i vector3i;
    private final Vector3f vector3f;

    Direction(int oppositeIndex, String name, Sign sign, Axis axis, Vector3i vector3i) {
        this.oppositeIndex = oppositeIndex;
        this.name = name;
        this.sign = sign;
        this.axis = axis;
        this.vector3i = vector3i;
        this.vector3f = new Vector3f(vector3i.x(), vector3i.y(), vector3i.z());
    }

    public Direction getOpposite() {
        return values[this.oppositeIndex];
    }

    public String getName() {
        return name;
    }

    public Sign getSign() {
        return sign;
    }

    public Axis getAxis() {
        return axis;
    }

    public Vector3i getVector3i() {
        return vector3i;
    }

    public Vector3f getVector3f() {
        return vector3f;
    }

    public enum Axis {
        X, Y, Z
    }

    public enum Sign {
        Posi,
        Nega
    }
}
