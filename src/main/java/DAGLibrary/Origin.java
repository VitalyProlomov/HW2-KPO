package DAGLibrary;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the class of the coordinate plain, which is represented by its
 * starting point and its position referring to some other origin or space.
 */
public class Origin extends Point{
    private Set<Point> children_;

    public Origin(Coord2D shift) {
        // Property bounds_ will be initialized incorrectly, but we
        // do not care about it, because it is only reachable in get
        // method, where bounds_ will be updated specifically.
        super(shift);
        children_ = new HashSet<>();
    }

    public Set<Point> getChildren_() {
        return children_;
    }

    /**
     * Setter of children. Children could be null.
     * @param children_ could be null.
     */
    public void setChildren_(Set<Point> children_) {
        this.children_ = children_;
    }


    // Every time getBounds is called bounds_ is actually updated.
    // At other times it might be incorrect.
    public BoundBox getBounds() throws EmptyBoundBoxException {
        if (children_.size() == 0) {
            return null;
        }

        bounds_ = BoundBox.updateBoundBox(this);
        return bounds_;
    }


}
