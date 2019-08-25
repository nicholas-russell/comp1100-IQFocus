package comp1110.ass2;

import static comp1110.ass2.State.*;

public enum PieceType {
    A,B,C,D,E,F,G,H,I,J;

    /**
     * Build a colormap has 10 arrays, each array represents a PieceType.
     * In each array, there are 4*4 = 16 states(All PieceType can be put in a 4*4 rectangle).
     * The state will be one of the four colors in State or Empty.

     *  0 1 2 3     8 4 0   11 10 9 8   3 7 11
     *  4 5 6 7     9 5 1   7 6 5 4     2 6 10
     *  8 9 10 11   10 6 2  3 2 1 0     1 5 9
     *              11 7 3              0 4 8
     *
     */
    //private static State[][] colorMap = new State[10][12];
    private static State[][] colorMap = {
            {   GREEN,WHITE,RED,NULL,
                NULL,RED,NULL,NULL,
                NULL,NULL,NULL,NULL,
            },
            {   NULL,NULL,NULL,NULL,
                NULL,NULL,NULL,NULL,
                NULL,NULL,NULL,NULL,
            },



    };

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
