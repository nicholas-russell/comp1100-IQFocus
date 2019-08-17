package comp1110.ass2;

public class Piece {
    private PieceType pieceType;
    private Operation location;
    private Orientation orientation;

    public Operation getLocation() {
        return location;
    }
    public Orientation getOrientation() {
        return orientation;
    }
    public PieceType getPieceType() { return pieceType; }


    public static Orientation placementToOrientation(String placement) {

    }

    public static Operation placementToLocation(String placement) {

    }
}
