package comp1110.ass2;

import java.util.Arrays;
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
        // FIXME Task 2: determine whether a piece placement is well-formed
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
        // FIXME Task 3: determine whether a placement is well-formed
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
        // FIXME Task 5: determine whether a placement string is valid

        int numberPiecePlacements = placement.length() / 4;
        int w;
        int u;
        int acc = 0;
        int p = 0;


        int a = 4;
        int b = 5;
        int c = 5;
        int d = 4;
        int e = 5;
        int f = 3;
        int g = 4;
        int h = 5;
        int i = 3;
        int j = 5;

        // convert pieces into number of squares!
       public static void numberOfSquares(String placement) {

            for (u =0, u < placement.length(), u+= 4){
                if (placement.charAt(u) == 'a' ) {
                   p = p + 4;
                }
                if (placement.charAt(u) == 'd'){
                    p = p + 4

                }
            }

        }

        String[] xCoords = new String[];
         String[] yCoords = new String[];

        // adds Coord to Respective Array
        public static String[] addToArray (String[]){

            for (w = 2, w < placement.length(), w += 4) {

                char coord = placement.charAt(w);

                acc++;

            }
        }


        // Checkes both placement and pieceplacement are true then checks state
        if (isPiecePlacementWellFormed(placement) == true && isPlacementStringWellFormed(placement) == true) {
            return false;


        }
        return false;
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
        return null;
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
     * Put the piece on the board and update the board state
     *
     * @param placement The placement string to add piece to board
     */
    public void addPieceToBoard(String placement) {}
}
