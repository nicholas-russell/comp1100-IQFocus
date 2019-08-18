package comp1110.ass2;

public class Operation {


    /**
     * Create all the states on the board, only bottom left and bottom right will
     * be the state Null(in fact not belongs to the board) and other state will
     * be Empty at the start(can be replaced by other colors).
     */
    private State[][] board = new State [5][9];

    /**
     * Set the board to the initial state , i.e. Change all the color state to
     * the Empty state.
     */
    public void resetBoard() {}

   /**
    * Give a placement and return the boardstate before the placement is put
    * on the board.
    * @param placement The placement string to undo
    */
    public void undoOperation(String placement) {}


    /**
     * Put the piece on the board and update the board state
     *
     * @param placement The placement string to add piece to board
     */
    public void addPieceToBoard(String placement) {}

}
