package comp1110.ass2;

public enum PieceType {
    A,B,C,D,E,F,G,H,I,J;

    /**
     * Build a colormap has 10 arrays, each array represents a PieceType.
     * In each array, there are 4*4 = 16 states(All PieceType can be put in a 4*4 rectangle).
     * The state will be one of the four colors in State or Empty.
     */
    private static State[][] colorMap = new State[10][16];

   /**
    * Give x-offset, y-offset and the orientation of the piece, return a state on the piece.
    * If the position is not on the piece, return null.
    *
    * @param x The x offset on the piece for the part you want to find
    * @param y The y offset on the piece for the part you want to find
    * @param orientation The orientation of the piece
    */
    public State getStateOnPiece(int x, int y, Orientation orientation) {
        return null;
    }


}
