package DAGLibrary;

/**
 * Represents physical point that can move on the coordinate system.
 */
public class Point {
    protected Coord2D position_;
    protected final BoundBox bounds_;

    public Point(Coord2D coordinates) {
        this.position_ = coordinates;
        bounds_ = new BoundBox(coordinates, coordinates);
    }

    public Coord2D getPosition() {
        return position_;
    }

    public void setPosition(Coord2D coords) {
        position_ = coords;
        bounds_.resetCoordinates(this);
    }
}
