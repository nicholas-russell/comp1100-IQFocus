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
    //public State[][] board = new State [5][9];
    State[][] saved = new State[5][9];
    public State[][] board = {
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {NULL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, NULL},
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
        String[] appear = new String[placement.length() / 4];
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
     * <p>
     * To be valid, the placement string must be:
     * - well-formed, and
     * - each piece placement must be a valid placement according to the
     * rules of the game:
     * - pieces must be entirely on the board
     * - pieces must not overlap each other
     *
     * @param placement A placement string
     * @return True if the placement sequence is valid
     */
    public static boolean isPlacementStringValid(String placement) {
        boolean result = new FocusGame().addPiecesToBoard(placement);
        return result;
    }


    /**
     * Given a string describing a placement of pieces and a string describing
     * a challenge, return a set of all possible next viable piece placements
     * which cover a specific board cell.
     * <p>
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
     * @param col       The cell's column.
     * @param row       The cell's row.
     * @return A set of viable piece placements, or null if there are none.
     */
    static Set<String> getViablePiecePlacements(String placement, String challenge, int col, int row) {
        // FIXME Task 6: determine the set of all viable piece placements given existing placements and a challenge
        Set result = new HashSet();
        System.out.println(placement);
        ArrayList<PieceType> AvaliablePiece = new ArrayList<PieceType>();
        for (int i = 'a'; i < 'a' + 10; i++) {
            if (!placement.contains(String.valueOf((char) i)))
                AvaliablePiece.add(PieceType.valueOf(String.valueOf((char) (i + 'A' - 'a'))));
        }
        System.out.println(AvaliablePiece);
        return null;
    }

    public boolean pieceCover(String placement, int col, int row) {
        FocusGame Board = new FocusGame();
        boolean valid = Board.addPiecesToBoard(placement);
        boolean cover = (Board.board[col][row] != EMPTY);
        boolean result = valid && cover;
        return result;
    }


    public boolean consistentWithChallenge(String placement, String challenge) {
        FocusGame Board = new FocusGame();
        if (Board.addPiecesToBoard(placement)) {
            for (int i = 0; i < 9; i++) {
                State tmp = Board.board[1 + i / 3][3 + i % 3];
                switch (challenge.charAt(i)) {
                    case 'R':
                        if (tmp != RED) return false;
                        break;
                    case 'W':
                        if (tmp != WHITE) return false;
                        break;
                    case 'B':
                        if (tmp != BLUE) return false;
                        break;
                    case 'G':
                        if (tmp != GREEN) return false;
                        break;
                }
            }
            return true;
        } else return false;
    }

    /**
     * Return the canonical encoding of the solution to a particular challenge.
     * <p>
     * A given challenge can only solved with a single placement of pieces.
     * <p>
     * Since some piece placements can be described two ways (due to symmetry),
     * you need to use a canonical encoding of the placement, which means you
     * must:
     * - Order the placement sequence by piece IDs
     * - If a piece exhibits rotational symmetry, only return the lowest
     * orientation value (0 or 1)
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
    public void resetBoard() {
        board = new State[][]{
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                {NULL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, NULL}
        };

    }

    /**
     * Save the current board state of the game.(An necessary function for a game and also important
     * method for finding solution)
     */
    public void saveState() {
        saved = board.clone();
        for (int i = 0; i < board.length; i++)
            saved[i] = board[i].clone();
    }

    /**
     * Load the current board state of the game.(An necessary function for a game and also important
     * method for finding solution)
     */
    public void loadState() {

        for (int i = 0; i < board.length; i++)
            board[i] = saved[i].clone();
    }

    /**
     * Give a placement and return the boardstate before the placement is put
     * on the board.
     *
     * @param placement The string placement represents the current board state
     * @param piece     The piece placement need to be undone.
     */
    public void undoOperation(String placement, String piece) {
        boolean result = true;
        result = placement.contains(piece);
        if (result) {
            Piece p = new Piece(piece);
            int x = p.getLocation().getX();
            int y = p.getLocation().getY();

            for (int i = 0; i < 16; i++) {
                if (y + i % 4 >= 0 && x + i / 4 >= 0 && y + i % 4 < 5 && x + i / 4 < 9 &&
                        p.getPieceType().getStateOnPiece(i / 4, i % 4, p.getOrientation()) != EMPTY)
                    board[y + i % 4][x + i / 4] = EMPTY;
            }
        }
    }


    /**
     * Put the pieces on the board and update the board state.Only when all the pieces in the placement string
     * can be added to board,if one piece cannot be added to the board then there will be no change.
     *
     * @param placement The placement string to add piece to board
     * @return True if all the pieces in the placement string can be added to the board.
     */


    public boolean addPiecesToBoard(String placement) {
        boolean result = true;
        result = isPlacementStringWellFormed(placement);
        saveState();
        if (result) {
            for (int i = 0; i < placement.length(); i += 4) {
                Piece p = new Piece(placement.substring(i, i + 4));
                int x = p.getLocation().getX();
                int y = p.getLocation().getY();


                /*If the boardstate is not EMPTY(overlap) or the piece is off the board,the pieces cannot be
                  added to the board and the return result should be false.*/
                for (int j = 0; j < 16; j++) {
                    if ((p.getPieceType().getStateOnPiece(j / 4, j % 4, p.getOrientation()) != EMPTY)
                            && (y + j % 4 < 0 || x + j / 4 < 0 || y + j % 4 >= 5 || x + j / 4 >= 9 ||
                            (board[y + j % 4][x + j / 4] != EMPTY)))
                        result = false;
                }


                if (result) {
                    for (int j = 0; j < 16; j++) {
                        if (y + j % 4 >= 0 && x + j / 4 >= 0 && y + j % 4 < 5 && x + j / 4 < 9 &&
                                board[y + j % 4][x + j / 4] != NULL &&
                                p.getPieceType().getStateOnPiece(j / 4, j % 4, p.getOrientation()) != EMPTY)
                            board[y + j % 4][x + j / 4] = p.getPieceType().getStateOnPiece(j / 4, j % 4, p.getOrientation());
                    }
                }

            }
        }

        if (!result)
            loadState();
        for (State[] row : board) {
            for (State s : row) {
                System.out.print(s.toString() + ",");
            }
            System.out.println("");
        }
        return result;
    }

    public boolean checkPieceToBoard(String piecePlacement) {
        Piece p = new Piece(piecePlacement);
        int x = p.getLocation().getX();
        int y = p.getLocation().getY();

        for (int j = 0; j < 16; j++) {
            if ((p.getPieceType().getStateOnPiece(j / 4, j % 4, p.getOrientation()) != EMPTY)
                    && (y + j % 4 < 0 || x + j / 4 < 0 || y + j % 4 >= 5 || x + j / 4 >= 9 ||
                    (board[y + j % 4][x + j / 4] != EMPTY))) {
                return false;
            }
        }
        return true;
    }


    public void addPieceToBoard(String piecePlacement){
        Piece p = new Piece(piecePlacement);
        int x = p.getLocation().getX();
        int y = p.getLocation().getY();

        for (int j = 0; j < 16; j++) {
            if (y + j % 4 >= 0 && x + j / 4 >= 0 && y + j % 4 < 5 && x + j / 4 < 9 &&
                    board[y + j % 4][x + j / 4] != NULL &&
                    p.getPieceType().getStateOnPiece(j / 4, j % 4, p.getOrientation()) != EMPTY)
                board[y + j % 4][x + j / 4] = p.getPieceType().getStateOnPiece(j / 4, j % 4, p.getOrientation());
        }
        for (State[] row : board) {
            for (State s : row) {
                System.out.print(s.toString() + ",");
            }
            System.out.println("");
        }
    }

    /**
     * Gets the placement string for the current Board state
     * @return placement string of current board state
     */
    public String getBoardPlacementString() {
        return "";
    }

}
