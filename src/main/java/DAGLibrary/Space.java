package DAGLibrary;

import java.util.HashSet;
import java.util.Set;

public class Space extends Origin {
    Set<Point> children_;

    public Space() {
        super(new Coord2D(0,0));
    }

    
}
