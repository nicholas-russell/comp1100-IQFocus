package comp1110.ass2;

public enum PieceType {
    a,b,c,d,e,f,g,h,i,j;

    /*
     * Build a colormap has 10 arrays, each array represents a PieceType.
     * In each array, there are 4*3 = 12 states(All PieceType can be put in a 4*3 rectangle).
     * The state will be the four color or NUll(not on the piece).
     */
    private static State[][] colormap = new State[9][11];

   /*
    * Give x-offset, y-offset and the orientation of the piece, return a state on the piece.
    * If the position is not on the piece, return null.
    */
    public State GetStateOnPiece(int x,int y,Orientation orientation){


    }


}
