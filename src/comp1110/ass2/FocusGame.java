package comp1110.ass2;

import java.lang.reflect.Array;
import java.util.*;

import static comp1110.ass2.State.*;

/**
 * This class provides the text interface for the IQ Focus Game
 *
 * The game is based directly on Smart Games' IQ-Focus game
 * (https://www.smartgames.eu/uk/one-player-games/iq-focus)
 *
 * @author Yuhui Wang, Matthew Tein
 * @version 0.2-d2f
 * @since 01/10/2019
 */

public class FocusGame {
    /**
     * Create all the states on the board, only bottom left and bottom right will
     * be the state Null(in fact not belongs to the board) and other state will
     * be Empty at the start(can be replaced by other colors).
     */

    public int currentChallengeNumber;
    public Solution currentChallenge;

    public int getChallengeNumber() {
        return currentChallengeNumber + 1;
    }

    public void newGame() {
        currentChallengeNumber = 0;
        currentChallenge = Solution.getChallenge(currentChallengeNumber);
    }

    public void nextChallenge() {
        currentChallengeNumber++;
        currentChallenge = Solution.getChallenge(currentChallengeNumber);
    }

    public String getChallenge() {
        return currentChallenge.getObjective();
    }

    public boolean checkCompletion() {
        return currentChallenge.isSolutionCorrect(orderPlacementString(getBoardPlacementString()));
    }

    public String getNextHint() {
        ArrayList<String> currentPieces = splitPlacementString(getBoardPlacementString());
        System.out.println(currentPieces);
        ArrayList<String> solutionPieces = splitPlacementString(currentChallenge.getSolution());
        System.out.println(solutionPieces);
        for (String p : currentPieces) {
            if (solutionPieces.contains(p)) {
                solutionPieces.remove(p);
            }
        }
        System.out.println(solutionPieces);
        return solutionPieces.iterator().next();
    }

    private static ArrayList<String> splitPlacementString(String placementString) {
        int l = placementString.length();
        char[] placementStringArray = placementString.toCharArray();
        ArrayList<String> placements = new ArrayList<>();
        int i = 0;
        while (i < l) {
            char[] p = new char[4];
            p[0] = placementStringArray[i];
            p[1] = placementStringArray[i+1];
            p[2] = placementStringArray[i+2];
            p[3] = placementStringArray[i+3];
            placements.add(new String(p));
            i += 4;
        }
        return placements;
    }

    /**
     * This is written by Yuhui Wang
     * public State[][] board = new State [5][9];
     */
    State[][] saved = new State[5][9];
    String current = new String();
    public State[][] board = {
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {NULL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, NULL},
    };

    /**
     * This is written by Yuhui Wang
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
     * This is written by Yuhui Wang
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
     * This is written by Yuhui Wang
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
     * This is written by Matthew Tein /Yuhui Wang
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

        //Find a list of Non-placed PieceTypes
        ArrayList<PieceType> AvaliablePiece = new ArrayList<PieceType>();
        for (int i = 'a'; i < 'a' + 10; i++) {
            if (!placement.contains(String.valueOf((char) i)))
                AvaliablePiece.add(PieceType.valueOf(String.valueOf((char) (i + 'A' - 'a'))));
        }



        //Matthew - Continueing on Yuhui's Work


        //Filtering if valid. Will need to compresss the following filters once sorted through
        ArrayList<String> possibleSinglePlacements = new ArrayList<>();
        for (String x : findPossibilities()){
            if(isPlacementStringValid(x) == true){
                possibleSinglePlacements.add(x);

            }
            else{
            }
        }
        Set<String> viablePlacements = new HashSet<>();
        //Filter if matches challenge Square
        for(String x : possibleSinglePlacements){
            boolean answer = new FocusGame().consistentWithChallenge(x, challenge);
            if(answer == true){
                viablePlacements.add(x);
            }
            else{
            }
        }
        for (String x : viablePlacements){

        }

        //Need to Add Last filter that checks corresponding c-square ofthe given int row and int col

        //for debugging
        System.out.println(AvaliablePiece);
        System.out.println(viablePlacements);

        return viablePlacements;
    }

    // Written by Matthew Tein - Generates all possible piece placements
    //      Used in tandem with getViablePiecePlacements
    public static Set<String> findPossibilities() {
        Set<String> AllPossibleMoves = new HashSet<>();
        char u;

        for (int h = 0; h < 10; h++) {
            switch (h){
                case 0: u = 'a';
                    break;
                case 1: u = 'b';
                    break;
                case 2: u = 'c';
                    break;
                case 3: u = 'd';
                    break;
                case 4: u = 'e';
                    break;
                case 5: u = 'f';
                    break;
                case 6: u = 'g';
                    break;
                case 7: u = 'h';
                    break;
                case 8: u = 'i';
                    break;
                case 9: u = 'j';
                    break;
                default:
                    u = 'a';
                    break;

            }
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 4; k++) {
                    for (int l = 0; l < 3; l++) {
                        String placeholder = ""+ u + "" + j + "" + k + "" + l;
                        AllPossibleMoves.add(placeholder);
                    }
                }
            }
        }
        return AllPossibleMoves;
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
     * should call on task 6 multiple times- like game tree to decide solutions
     */
    public static String getSolution(String challenge) {
        // FIXME Task 9: determine the solution to the game, given a particular challenge



        return null;
    }

    /**
     * This is written by Yuhui Wang
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
     * This is written by Yuhui Wang
     * Save the current board state of the game.(An necessary function for a game and also important
     * method for finding solution)
     */
    public void saveState() {
        saved = board.clone();
        for (int i = 0; i < board.length; i++)
            saved[i] = board[i].clone();
    }

    /**
     * This is written by Yuhui Wang
     * Load the current board state of the game.(An necessary function for a game and also important
     * method for finding solution)
     */
    public void loadState() {

        for (int i = 0; i < board.length; i++)
            board[i] = saved[i].clone();
    }

    /**
     * This is written by Yuhui Wang
     * Give a placement and return the boardstate before the placement is put
     * on the board.
     *
     * @param placement The string placement represents the current board state
     * @param piecePlacement    The piece placement need to be undone.
     */
    public void undoOperation(String placement, String piecePlacement) {
        boolean result = true;
        result = placement.contains(piecePlacement);
        if (result) {
            Piece p = new Piece(piecePlacement);
            int x = p.getLocation().getX();
            int y = p.getLocation().getY();
            current = placement.replaceAll(piecePlacement,"");


            for (int i = 0; i < 16; i++) {
                if (y + i % 4 >= 0 && x + i / 4 >= 0 && y + i % 4 < 5 && x + i / 4 < 9 &&
                        p.getPieceType().getStateOnPiece(i / 4, i % 4, p.getOrientation()) != EMPTY)
                    board[y + i % 4][x + i / 4] = EMPTY;
            }
        }
    }

    /**
     * This is written by Yuhui Wang
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

                if (checkPieceToBoard(placement.substring(i, i + 4)))
                    addPieceToBoard(placement.substring(i, i + 4));
                else
                    result = false;
            }
        }
        else
            loadState();
            return result;
    }

    /**
     * This is written by Yuhui Wang
     * @param piecePlacement The pieceplacement need to be checked before adding to board
     * @return True if the piece can be added to board(without overlap or out-of-bounds
     */
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

    /**
     * This is written by Yuhui Wang
     * @param piecePlacement The pieceplacement need to be added to board
     */
    public void addPieceToBoard(String piecePlacement) {
        current += piecePlacement;
        System.out.println(current);
        Piece p = new Piece(piecePlacement);
        int x = p.getLocation().getX();
        int y = p.getLocation().getY();

        for (int j = 0; j < 16; j++) {
            if (y + j % 4 >= 0 && x + j / 4 >= 0 && y + j % 4 < 5 && x + j / 4 < 9 &&
                    board[y + j % 4][x + j / 4] != NULL &&
                    p.getPieceType().getStateOnPiece(j / 4, j % 4, p.getOrientation()) != EMPTY)
                board[y + j % 4][x + j / 4] = p.getPieceType().getStateOnPiece(j / 4, j % 4, p.getOrientation());
        }
    }

    /**
     * This is written by Yuhui Wang
     * Gets the placement string for the current Board state
     * @return placement string of current board state
     */
    public String getBoardPlacementString() {
        return current;
    }

    /**
     * Orders placement string into alphabetically and valid order
     * @param placementString an unordered placement string
     * @return ordered placement string
     */

    private static String orderPlacementString(String placementString) {
        int l = placementString.length();
        String orderedPlacementString = "";
        ArrayList<String> placements = splitPlacementString(placementString);
        Collections.sort(placements);
        for (String p : placements) {
            orderedPlacementString = orderedPlacementString + p;
        }
        return orderedPlacementString;
    }

}
