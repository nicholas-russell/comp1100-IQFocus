package comp1110.ass2;

/**
 * Defines different states on board
 *
 * @author Yuhui Wang & Matthew Tein
 * @version 1.0
 * @since 18/08/2019
 */

public enum State {
    WHITE, RED, BLUE, GREEN,// The four colours a square may have
    EMPTY,// Locations on board haven't been used by placement
    NULL;// Bottom left and right(not belongs to the board) or the square in piece has no colour

    /**
     * Since Null is a hypothesis, we should not consider it.
     * Empty can only be updated by color state and for the Null state,we should ignore it.
     * i,e.Null cannot update other value and when we put a placement on board, if squares of
     * Null are outside the board, we should delete the Null square.
     */
    public void ignoreNull() {
    }


    public static State getStateOnTile(int x, int y) {
        State square = FocusGame.board[y][x];
        return square;

    }

    public static State getColorStateFromChar(char input){
        State result;


        switch(input){
            case 'W': result = WHITE;
              break;
            case'R': result = RED;
              break;
            case 'B': result = BLUE;
              break;
            case 'G': result = GREEN;
            break;
             default: result = WHITE;
            break;

        }
        return result;
    }
}