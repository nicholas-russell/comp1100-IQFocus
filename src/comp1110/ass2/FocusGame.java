package comp1110.ass2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static comp1110.ass2.State.*;

/**
 * This class provides the text interface for the IQ Focus Game
 *
 * The game is based directly on Smart Games' IQ-Focus game
 * (https://www.smartgames.eu/uk/one-player-games/iq-focus)
 */
public class FocusGame {
    /**
     * Create all the states on the board, only bottom left and bottom right will
     * be the state Null(in fact not belongs to the board) and other state will
     * be Empty at the start(can be replaced by other colors).
     */
    //private State[][] board = new State [5][9];
    private State[][] board = {
            {EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY},
            {EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY},
            {EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY},
            {EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY},
            {NULL,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,EMPTY,NULL},
    };

    /**
     * Determine whether a piece placement is well-formed according to the
     * following criteria:
     * - it consists of exactly four characters
     * - the first character is in the range a .. j (shape)
     * - the second character is in the range 0 .. 8 (column)
     * - the third character is in the range 0 .. 4 (row)
     * - the fourth character is in the range 0 .. 3 (orientation)
     *
     * @param piecePlacement A string describing a piece placement
     * @return True if the piece placement is well-formed
     */
    static boolean isPiecePlacementWellFormed(String piecePlacement) {
        if (piecePlacement.length() != 4)
            return false;
        else {
            if (piecePlacement.charAt(0) < 'a' || piecePlacement.charAt(0) > 'j')
                return false;
            if (piecePlacement.charAt(1) - '0' < 0 || piecePlacement.charAt(1) - '0' > 8)
                return false;
            if (piecePlacement.charAt(2) - '0' < 0 || piecePlacement.charAt(2) - '0' > 4)
                return false;
            if (piecePlacement.charAt(3) - '0' < 0 || piecePlacement.charAt(3) - '0' > 3)
                return false;
        }

        return true;

    }

    /**
     * Determine whether a placement string is well-formed:
     * - it consists of exactly N four-character piece placements (where N = 1 .. 10);
     * - each piece placement is well-formed
     * - no shape appears more than once in the placement
     *
     * @param placement A string describing a placement of one or more pieces
     * @return True if the placement is well-formed
     */
    public static boolean isPlacementStringWellFormed(String placement) {
        String [] appear = new String[placement.length()/4];
        if (placement.length() % 4 != 0 || placement.length() / 4 < 1 || placement.length() / 4 > 10) {
            return false;
        }
        for (int i = 0; i < placement.length(); i += 4) {
            if (Arrays.asList(appear).contains(String.valueOf(placement.charAt(i))))
                return false;
            else if (!isPiecePlacementWellFormed(placement.substring(i, i + 4)))
                return false;
            else
                appear[i / 4] = String.valueOf(placement.charAt(i));

        }

        return true;

    }

    /**
     * Determine whether a placement string is valid.
     *
     * To be valid, the placement string must be:
     * - well-formed, and
     * - each piece placement must be a valid placement according to the
     *   rules of the game:
     *   - pieces must be entirely on the board
     *   - pieces must not overlap each other
     *
     * @param placement A placement string
     * @return True if the placement sequence is valid
     */
    public static boolean isPlacementStringValid(String placement) {
        boolean result = new FocusGame().addPieceToBoard(placement);
        return result;
    }


    /**
     * Given a string describing a placement of pieces and a string describing
     * a challenge, return a set of all possible next viable piece placements
     * which cover a specific board cell.
     *
     * For a piece placement to be viable
     * - it must be valid
     * - it must be consistent with the challenge
     *
     * @param placement A viable placement string
     * @param challenge The game's challenge is represented as a 9-character string
     *                  which represents the color of the 3*3 central board area
     *                  squares indexed as follows:
     *                  [0] [1] [2]
     *                  [3] [4] [5]
     *                  [6] [7] [8]
     *                  each character may be any of
     *                  - 'R' = RED square
     *                  - 'B' = Blue square
     *                  - 'G' = Green square
     *                  - 'W' = White square
     * @param col      The cell's column.
     * @param row      The cell's row.
     * @return A set of viable piece placements, or null if there are none.
     */
    static Set<String> getViablePiecePlacements(String placement, String challenge, int col, int row) {
        // FIXME Task 6: determine the set of all viable piece placements given existing placements and a challenge
        Set result = new HashSet();
        System.out.println(placement);
        ArrayList<PieceType> AvaliablePiece = new ArrayList<PieceType>();
        for(int i=0+'a';i<'a'+10;i++){
            if(!placement.contains(String.valueOf((char)i)))
                AvaliablePiece.add(PieceType.valueOf(String.valueOf((char)(i+'A'-'a'))));
        }
        System.out.println(AvaliablePiece);
        return null;
    }

    public boolean pieceCover(String placement,int col, int row){
        FocusGame Board =  new FocusGame();
        boolean valid = Board.addPieceToBoard(placement);
        boolean cover = (Board.board[col][row] != EMPTY);
        boolean result = valid && cover;
        return result;
    }

    public boolean consistentWithChallenge(String placement, String challenge){
        FocusGame Board = new FocusGame();
        if(Board.addPieceToBoard(placement)){
            for(int i=0;i<9;i++){
                State tmp = Board.board[1+i/3][3+i%3];
                switch(challenge.charAt(i)){
                    case 'R':
                        if(tmp!=RED) return false;
                        break;
                    case 'W':
                        if(tmp!=WHITE) return false;
                        break;
                    case 'B':
                        if(tmp!=BLUE) return false;
                        break;
                    case 'G':
                        if(tmp!=GREEN) return false;
                        break;
                }
            }
            return true;
        }
        else return false;
    }

    /**
     * Return the canonical encoding of the solution to a particular challenge.
     *
     * A given challenge can only solved with a single placement of pieces.
     *
     * Since some piece placements can be described two ways (due to symmetry),
     * you need to use a canonical encoding of the placement, which means you
     * must:
     * - Order the placement sequence by piece IDs
     * - If a piece exhibits rotational symmetry, only return the lowest
     *   orientation value (0 or 1)
     *
     * @param challenge A challenge string.
     * @return A placement string describing a canonical encoding of the solution to
     * the challenge.
     */
    public static String getSolution(String challenge) {
        // FIXME Task 9: determine the solution to the game, given a particular challenge
        return null;
    }

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
     * Puts the piece on the board and updates the board state; only after checking boardState of each board square for
     * empty.
     * @param placement The placement string to add piece to board
     */
    public boolean addPieceToBoard(String placement) {
        boolean result = true;
        result = isPlacementStringWellFormed(placement);
        if(result) {
            for (int i = 0; i < placement.length(); i += 4) {
                Piece p = new Piece(placement.substring(i, i + 4));
                int x = p.getLocation().getX();
                int y = p.getLocation().getY();

                /*This Loop checks if board location where piece is being placed has the boardstate empty, then replaces
                with corresponding square on PieceColorMap if found true*/

                for (int j = 0; j < 16; j++) {
                    if (y + j % 4 < 5 && x + j / 4 < 9 && board[y + j % 4][x + j / 4] == EMPTY)
                        board[y + j % 4][x + j / 4] = p.getPieceType().getStateOnPiece(j / 4, j % 4, p.getOrientation());
                    else if (p.getPieceType().getStateOnPiece(j / 4, j % 4, p.getOrientation()) != EMPTY) {
                        result = false;
                    }
                }
            }
        }
        return result;
    }
}
