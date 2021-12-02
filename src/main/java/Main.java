import DAGLibrary.*;

import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws EmptyBoundBoxException {
        Coord2D c1 = new Coord2D(2,2);
        Coord2D c2 = new Coord2D(3, 3);
        Coord2D c3 = new Coord2D(0,0);

        Point p1 = new Point(c2);
        Point p2 = new Point(c3);
        Point p3 = new Point(new Coord2D(4, 0));
        Point p4 = new Point(new Coord2D(7, -100));

        Origin o1 = new Origin(c1);
        Origin o2 = new Origin(new Coord2D(6, 12));
        Origin o3 = new Origin(c3);

        HashSet<Point> children1 = new HashSet<>();
        HashSet<Point> children2 = new HashSet<>();
        HashSet<Point> children3 = new HashSet<>();

        children1.add(p1);
        children1.add(o2);
        children1.add(o3);
        o1.setChildren_(children1);


        children2.add(p2);
        children2.add(p3);
        o2.setChildren_(children2);

        children3.add(p4);
        o3.setChildren_(children3);

        o1.getBounds();
    }
}
