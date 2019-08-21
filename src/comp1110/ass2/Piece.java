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
        switch(placement.substring(3,4)) {
            case "0": return Orientation.Zero;
            case "1": return Orientation.One;
            case "2": return Orientation.Two;
            case "3": return Orientation.Three;
            default: return null;
        }
    }

    /**
     * Get location for placement
     * @param placement A placement string
     * @return Location object
     */
    public static Location placementToLocation(String placement) {
        int x = Integer.parseInt(placement.substring(1,2));
        int y = Integer.parseInt(placement.substring(2,3));
        return new Location(x,y);
    }
}
