package DAGLibrary;

public class EmptyBoundBoxException extends Exception {
    Origin emptyOrigin_;

    public EmptyBoundBoxException() {}


    public EmptyBoundBoxException(Origin emptyOrigin) {
        emptyOrigin_ = emptyOrigin;
    }

    private String message = "Can not calculate Bound box of the origin with no children.";

    public Origin getOriginCause() {
        return emptyOrigin_;
    }

}
