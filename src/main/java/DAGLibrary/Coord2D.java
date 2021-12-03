package DAGLibrary;

/**
 * Class that represents fixed coordinates on
 * the coordinate system.
 */
public class Coord2D {
    private final double x_coord_;
    private final double y_coord_;

    public Coord2D(double x, double y) {
        this.x_coord_ = x;
        this.y_coord_ = y;
    }

    public double getX() {
        return x_coord_;
    }

    public double getY() {
        return y_coord_;
    }

    public Coord2D shift(Coord2D coord) {
        return new Coord2D(this.getX() + coord.getX(), this.getY() + coord.getY());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj.getClass() != Coord2D.class) {
            return false;
        }
        return x_coord_ == ((Coord2D) obj).x_coord_ && y_coord_ == ((Coord2D) obj).y_coord_;
    }


}
