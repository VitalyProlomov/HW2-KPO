package DAGLibrary;

import java.util.Set;

/**
 * Immutable value type instance, representing the coordinates of the diagonal of
 * rectangle that covers all the boundboxes of children (in origin case) or
 * is equal to the coords of the point itself (point case)
 */
public class BoundBox {
    final Coord2D topRightCoord_;
    final Coord2D bottomLeftCoord_;

    public BoundBox(Coord2D bottomLeftCoords, Coord2D topRightCoords) {
        bottomLeftCoord_ = bottomLeftCoords;
        topRightCoord_ = topRightCoords;
    }

    public Coord2D getTopRightCoord_() {
        return topRightCoord_;
    }

    public Coord2D getBottomLeftCoord_() {
        return bottomLeftCoord_;
    }

    /**
     * Method that counts the BoundBox for the given point and returns them
     * as a new BoundBox instance.
     * @param point Point whose bounds we are calculating
     * @return new BoundBox that represents the bounds of the point
     */
    protected static BoundBox updateBoundBox(Point point) {
        if (point.getClass() != Origin.class) {
            return new BoundBox(point.getPosition(), point.getPosition());
        }
        // Assigning null coords to show that we have not yet found a fitting point
        // to be added to the countedBox.
        BoundBox countedBox = new BoundBox(null, null);
        for (Point child: ((Origin) point).getChildren_()) {
            if (child.getClass() != Origin.class) {
                // There was no valid coordinate assigned to the childBox yet.
                if (countedBox.bottomLeftCoord_ == null) {
                    countedBox = updateBoundBox(child);
                } else {
                    countedBox = countedBox.unite(child.bounds_);
                }

            } else {
                if (countedBox.bottomLeftCoord_ == null) {
                    countedBox = (updateBoundBox(child)).shiftBox(child.position_);
                } else {
                    countedBox = countedBox.unite(updateBoundBox(child).shiftBox(child.position_));
                }
            }
        }
        return countedBox;
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


    /**
     * Shifts the coordinates of this BoundBox (the instance we are currently working with).
     * @param shift the given shift (Coord2D)
     * @return the new BoundBox with shifted coordinates
     */
    public BoundBox shiftBox(Coord2D shift) {
        return new BoundBox(this.bottomLeftCoord_.shift(shift), this.topRightCoord_.shift(shift));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != BoundBox.class) {
            return  false;
        }
        boolean topEqual = topRightCoord_.equals(((BoundBox) obj).getTopRightCoord_());
        boolean bottomEqual = bottomLeftCoord_.equals(((BoundBox) obj).getBottomLeftCoord_());
        return topEqual && bottomEqual;
    }

}
