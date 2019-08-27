package comp1110.ass2;

import static comp1110.ass2.State.*;

public enum PieceType {
    A('A'),B('B'),C('C'),D('D'),E('E'),F('F'),G('G'),H('H'),I('I'),J('J');

    State[] colorMap;
    int height;
    int length;

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
            PieceType(char ch){
                switch(ch){
                    case 'A':
                        this.colorMap = new State[]{GREEN,WHITE,RED,EMPTY,
                                                    EMPTY,RED,EMPTY,EMPTY,
                                                    EMPTY,EMPTY,EMPTY,EMPTY,
                                                    EMPTY,EMPTY,EMPTY,EMPTY};
                        this.length = 3;
                        this.height = 2;
                        break;
                    case 'B':
                        this.colorMap = new State[]{EMPTY,BLUE,GREEN,GREEN,
                                                    WHITE,WHITE,EMPTY,EMPTY,
                                                    EMPTY,EMPTY,EMPTY,EMPTY,
                                                    EMPTY,EMPTY,EMPTY,EMPTY};
                        this.length = 4;
                        this.height = 2;
                        break;
                    case 'C':
                        this.colorMap = new State[]{EMPTY,EMPTY,GREEN,EMPTY,
                                                    RED,RED,WHITE,BLUE,
                                                    EMPTY,EMPTY,EMPTY,EMPTY,
                                                    EMPTY,EMPTY,EMPTY,EMPTY};
                        this.length = 4;
                        this.height = 2;
                        break;
                    case 'D':
                        this.colorMap = new State[]{RED,RED,RED,EMPTY,
                                                    EMPTY,EMPTY,BLUE,EMPTY,
                                                    EMPTY,EMPTY,EMPTY,EMPTY,
                                                    EMPTY,EMPTY,EMPTY,EMPTY};
                        this.length = 3;
                        this.height = 2;
                        break;
                    case 'E':
                        this.colorMap = new State[]{BLUE,BLUE,BLUE,EMPTY,
                                                    RED,RED,EMPTY,EMPTY,
                                                    EMPTY,EMPTY,EMPTY,EMPTY,
                                                    EMPTY,EMPTY,EMPTY,EMPTY};
                        this.length = 3;
                        this.height = 2;
                        break;
                    case 'F':
                        this.colorMap = new State[]{WHITE,WHITE,WHITE,EMPTY,
                                                    EMPTY,EMPTY,EMPTY,EMPTY,
                                                    EMPTY,EMPTY,EMPTY,EMPTY,
                                                    EMPTY,EMPTY,EMPTY,EMPTY};
                        this.length = 3;
                        this.height = 1;
                        break;
                    case 'G':
                        this.colorMap = new State[]{WHITE,BLUE,EMPTY,EMPTY,
                                                    EMPTY,BLUE,WHITE,EMPTY,
                                                    EMPTY,EMPTY,EMPTY,EMPTY,
                                                    EMPTY,EMPTY,EMPTY,EMPTY};
                        this.length = 3;
                        this.height = 2;
                        break;
                    case 'H':
                        this.colorMap = new State[]{RED,GREEN,GREEN,EMPTY,
                                                    WHITE,EMPTY,EMPTY,EMPTY,
                                                    WHITE,EMPTY,EMPTY,EMPTY,
                                                    EMPTY,EMPTY,EMPTY,EMPTY};
                        this.length = 3;
                        this.height = 3;
                        break;
                    case 'I':
                        this.colorMap = new State[]{BLUE,BLUE,EMPTY,EMPTY,
                                                    EMPTY,WHITE,EMPTY,EMPTY,
                                                    EMPTY,EMPTY,EMPTY,EMPTY,
                                                    EMPTY,EMPTY,EMPTY,EMPTY};
                        this.length = 2;
                        this.height = 2;
                        break;
                    case 'J':
                        this.colorMap = new State[]{GREEN,GREEN,WHITE,RED,
                                                    GREEN,EMPTY,EMPTY,EMPTY,
                                                    EMPTY,EMPTY,EMPTY,EMPTY,
                                                    EMPTY,EMPTY,EMPTY,EMPTY};
                        this.length = 4;
                        this.height = 2;
                        break;
                }
                height--;
                length--;
            }

   /**
    * Give x-offset, y-offset and the orientation of the piece, return a state on the piece.
    * If the position is not on the piece, return null.
    *
    * @param x The x offset on the piece for the part you want to find
    * @param y The y offset on the piece for the part you want to find
    * @param orientation The orientation of the piece
    */
    public State getStateOnPiece(int x, int y, Orientation orientation) {
        int newx = x;
        int newy = y;
        switch (orientation){
            case One:
                newx = y;
                newy = height - x;
                break;
            case Two:
                newx = length - x;
                newy = height - y;
                break;
            case Three:
                newx = length - y;
                newy = x;

        }
        if(newx <= length  && newy <= height && newx >= 0 && newy >= 0)
            return colorMap[newy * 4 + newx];
        else
            return EMPTY;
    }


}
