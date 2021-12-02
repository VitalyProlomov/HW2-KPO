package DAGLibrary;

import java.util.Set;

public class BoundBox {
    Coord2D topRightCoord_;
    Coord2D bottomLeftCoord_;

    BoundBox( Coord2D bottomLeftCoords, Coord2D topRightCoords) {
        bottomLeftCoord_ = bottomLeftCoords;
        topRightCoord_ = topRightCoords;
    }

    public void  updateCoordinates(Origin origin) throws EmptyBoundBoxException {
        if (origin.getChildren_() == null || origin.getChildren_().size() == 0) {
            throw new EmptyBoundBoxException();
        }
        // That is done so that the coordinates of the previous bound box get erased.
        origin.bounds_.bottomLeftCoord_ = null;
        origin.bounds_.topRightCoord_ = null;

        for (Point point : origin.getChildren_()) {
            if (point.getClass() == Origin.class) {
                BoundBox child_origin_bound = countShiftedBoundBox((Origin)point);
                child_origin_bound = child_origin_bound.unite(origin.bounds_);
                origin.bounds_.topRightCoord_ = child_origin_bound.topRightCoord_;
                origin.bounds_.bottomLeftCoord_ = child_origin_bound.bottomLeftCoord_;
            } else {
                updateCoordinates(point);
            }
        }
    }

    private BoundBox countShiftedBoundBox(Origin origin) throws EmptyBoundBoxException {
        if (origin.getChildren_() == null || origin.getChildren_().size() == 0) {
            throw new EmptyBoundBoxException();
        }
        Coord2D shift = origin.getPosition();
        BoundBox result = null;

        // Adding all children points and origins to the BoundBox.
        for (Point point : origin.getChildren_()) {
            BoundBox tempBox = null;
            if (point.getClass() == Origin.class) {
                tempBox = countShiftedBoundBox((Origin)point);
                tempBox.topRightCoord_ = topRightCoord_.Shift(shift);
                tempBox.bottomLeftCoord_ = bottomLeftCoord_.Shift(shift);
                // !!! Check if tempBox != null. !!!
                result = tempBox.unite(result);
            } else {
                tempBox = point.bounds_;
                tempBox.topRightCoord_ = point.bounds_.topRightCoord_.Shift(shift);
                tempBox.bottomLeftCoord_ = point.bounds_.bottomLeftCoord_.Shift(shift);
                result = point.bounds_.unite(result);
            }
        }
        return result;
    }
    /**
     * Update coordinates of the BoundBox and changes
     * them if the given point exceeds existing boundaries.
     */
    public void updateCoordinates(Point point) throws EmptyBoundBoxException {
        if (point == null) {
            throw new NullPointerException();
        }

        if (point.getClass() == Origin.class) {
            updateCoordinates((Origin)point);
        }
        BoundBox result = point.bounds_.unite(this);
        bottomLeftCoord_ = result.bottomLeftCoord_;
        topRightCoord_ = result.topRightCoord_;
    }

    public void resetCoordinates(Point point) {
        this.bottomLeftCoord_ = point.position_;
        this.topRightCoord_ = point.position_;
    }


    public void resetCoordinates(Set<Point> points) throws EmptyBoundBoxException {
        if (points.size() == 0) {
            throw new EmptyBoundBoxException();
        }
        for (Point point: points) {
            if (point.getClass() == Origin.class) {

            }
            break;
        }
    }

    /**
     * Returns a new BoundBox that covers all the points of both united
     * BoundBoxes.
     * @param box any box (even null box can be input here and not cause an exception -
     *            in that case this BoundBox will be returned.
     * @return box that fits all the points of both BoundBoxes.
     */
    public BoundBox unite(BoundBox box) {
        if (box == null || (box.bottomLeftCoord_ == null && box.topRightCoord_ == null)) {
            return this;
        }

        double x_min = Math.min(this.bottomLeftCoord_.getX(), box.bottomLeftCoord_.getX());
        double y_min = Math.min(this.bottomLeftCoord_.getY(), box.bottomLeftCoord_.getY());
        Coord2D newMin = new Coord2D(x_min, y_min);

        double x_max = Math.max(this.topRightCoord_.getX(), box.topRightCoord_.getX());
        double y_max = Math.max(this.topRightCoord_.getY(), box.topRightCoord_.getY());
        Coord2D newMax = new Coord2D(x_max, y_max);
        return new BoundBox(newMin, newMax);
    }
}
