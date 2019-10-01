package comp1110.ass2;

/**
 * Piece class for pieces on the board
 *
 * @author Nicholas Russell, Matthew Tein, Yuhui Wang
 * @version 0.1
 * @since 29/09/2019
 */

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

    public Piece(String placement) {
        this.pieceType = placementToPieceType(placement);
        this.location = placementToLocation(placement);
        this.orientation = placementToOrientation(placement);
    }

    /**
     *
     *
     * @param placement - placement string for Piece
     * @return true if placementToLocation Returns correct location
     */


    public static boolean placementToPieceTypeCheck(String placement) {
        if(placementToPieceType(placement) == PieceType.A){
            return true;
        }
        return false; }

    /**
     * Takes in placement string and returns the PieceType enum
     * @param placement Placement string for piece
     * @return PieceType enum
     */
    public static PieceType placementToPieceType(String placement) {
        switch (placement.substring(0,1)) {
            case "a": return PieceType.A;
            case "b": return PieceType.B;
            case "c": return PieceType.C;
            case "d": return PieceType.D;
            case "e": return PieceType.E;
            case "f": return PieceType.F;
            case "g": return PieceType.G;
            case "h": return PieceType.H;
            case "i": return PieceType.I;
            case "j": return PieceType.J;
            default: return null;
        }
    }

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

    @Override
    public String toString() {
        return "Piece:" +
                " this piece is of type " + this.pieceType.name() +
                " and is located at " + this.location +
                " and is facing " + this.orientation + ".";
    }
}
