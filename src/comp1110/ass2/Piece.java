package comp1110.ass2;

public class Piece {
    private PieceType pieceType;
    private Location location;
    private Orientation orientation;

    public Location getLocation() {
        return location;
    }
    public Orientation getOrientation() {
        return orientation;
    }
    public PieceType getPieceType() { return pieceType; }

    /**
     * Get orientation for placement
     * @param placement A placement string
     * @return Orientation object
     */
    public static Orientation placementToOrientation(String placement) {
        return null;
    }

    /**
     * Get location for placement
     * @param placement A placement string
     * @return Location object
     */
    public static Location placementToLocation(String placement) {
        return null;
    }
}
