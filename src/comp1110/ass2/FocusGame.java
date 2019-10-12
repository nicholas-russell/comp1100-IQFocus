package comp1110.ass2;

import comp1110.ass2.gui.Viewer;
import javafx.application.Application;

import java.util.*;

import static comp1110.ass2.State.*;

/**
 * This class provides the text interface for the IQ Focus Game
 *
 * The game is based directly on Smart Games' IQ-Focus game
 * (https://www.smartgames.eu/uk/one-player-games/iq-focus)
 *
 * @author Yuhui Wang, Matthew Tein, Nicholas Russell
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

    public boolean checkCompletionGenerated(String challenge) {
        if (getBoardPlacementString().length() != 40) {
            return false;
        }
        int x = 0;
        int y = 0;
        for (Character c : challenge.toCharArray()) {
            if (x > 2) {
                y++;
            }
            if (board[1+y][3+x] != State.getColorStateFromChar(c)) {
                return false;
            }
            x++;
        }

        return true;
    }

    private boolean challengePartiallyCorrect(String challenge) {
        int x = 0;
        int y = 0;
        for (Character c : challenge.toCharArray()) {
            if (x > 2) {
                x = 0;
                y++;
            }
            //System.out.println((1+y) + ", " + (3+x) + " = " + board[1+y][3+x].toString() + " should be " + c);
            if (board[1+y][3+x] != State.getColorStateFromChar(c) && board[1+y][3+x] != EMPTY) {
                System.out.println("END TREE");
                return false;
            }
            x++;
        }
        return true;
    }

    public void newGame() {
        resetBoard();
        currentChallengeNumber = 0;
        currentChallenge = Solution.getChallenge(currentChallengeNumber);
    }

    public void nextChallenge(int challengeNumber) {
        if (challengeNumber > Solution.SOLUTIONS.length) {
            // what to do here????
        } else {
            currentChallengeNumber = challengeNumber;
            currentChallenge = Solution.getChallenge(currentChallengeNumber);
        }
    }

    public String getChallenge() {
        return currentChallenge.getObjective();
    }

    public boolean checkCompletion() {
        return currentChallenge.isSolutionCorrect(orderPlacementString(getBoardPlacementString()));
    }

    public String getNextHint() {
        ArrayList<String> currentPieces = splitPlacementString(getBoardPlacementString());
        ArrayList<String> solutionPieces = splitPlacementString(currentChallenge.getSolution());
        for (String p : currentPieces) {
            if (solutionPieces.contains(p)) {
                solutionPieces.remove(p);
            }
        }
        Random r = new Random();
        return solutionPieces.get(r.nextInt(solutionPieces.size()));
    }

    public static ArrayList<String> splitPlacementString(String placementString) {
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

    public String getSaveString() {
        return currentChallengeNumber + "," + getBoardPlacementString();
    }

    public static boolean isSaveStringValid(String saveString) {
        String[] saveArray = saveString.split(",");
        return saveArray.length == 2
                && isPlacementStringValid(saveArray[1])
                && Solution.SOLUTIONS.length > Integer.parseInt(saveArray[0]);
    }

    /**
     * This is written by Yuhui Wang
     * public State[][] board = new State [5][9];
     */
    private State[][] saved = new State[5][9];
    private String current = "";
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
        return new FocusGame().addPiecesToBoard(placement);
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
    public static Set<String> getViablePiecePlacements(String placement, String challenge, int col, int row) {
        //Find a list of Non-placed PieceTypes
        ArrayList<PieceType> AvailablePiece = new ArrayList<PieceType>();
        for (int i = 'a'; i < 'a' + 10; i++) {
            if (!placement.contains(String.valueOf((char) i)))
                AvailablePiece.add(PieceType.valueOf(String.valueOf((char) (i + 'A' - 'a'))));
        }
        //Matthew - Continuing on Yuhui's Work

        Set<String> viablePlacements1 = new HashSet<>();
        Set<String> viablePlacements2 = new HashSet<>();
        Set<String> viablePlacements3 = new HashSet<>();
        Set<String> viablePlacements4 = new HashSet<>();

         for(String x : findPossibilities()){
             char piece1 = x.charAt(0);
             PieceType piece2 = getPieceTypeFromChar(piece1);
             if(AvailablePiece.contains(piece2)){
                 viablePlacements1.add(x);
             }
         }
        for (String x : viablePlacements1){
            if(isPlacementStringValid(placement+x)) {
                viablePlacements2.add(x);
            }
        }
        for(String x : viablePlacements2){
            if(testAddPieceToBoard(placement+x, row, col)) {
                viablePlacements3.add(x);
            }
        }
        for(String x : viablePlacements3){
            if(consistentWithChallengeMidGame(placement+x, challenge)){
                viablePlacements4.add(x);
                //System.out.println("Added 4");
            }
        }

        /*System.out.println("AP:" + AvailablePiece);
        System.out.println("VP:" + viablePlacements4);*/
        if (viablePlacements4.isEmpty()){
            return null;
        } else {
            return viablePlacements4;
        }
    }


    //Written By Matthew Tein
    // For Task6 : need to return just states of squares on board
    public static boolean testAddPieceToBoard(String testPlacement, int row, int col) {
        FocusGame Board1 = new FocusGame();
        if(Board1.addPiecesToBoard(testPlacement)){
            State targetCell = Board1.board[row][col];
            if(targetCell == WHITE || targetCell == GREEN || targetCell  == RED || targetCell  == BLUE){
                return true;
            }
            else {return false;}
        }
        else return false;

    }

    // Written by Matthew Tein - Generates all possible piece placements
    //      Used in tandem with getViablePiecePlacements
    private static Set<String> findPossibilities() {
        Set<String> AllPossibleMoves = new HashSet<>();
        for (int h = 0; h < 10; h++) {
            char u = (char)(h+97);
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < 5; k++) {
                    for (int l = 0; l < 4; l++) {
                        String placeholder = ""+ u + "" + j + "" + k + "" + l;
                        AllPossibleMoves.add(placeholder);
                    }
                }
            }
        }

        return AllPossibleMoves;
    }

    private static boolean consistentWithChallengeMidGame(String placement, String challenge) {
        FocusGame board = new FocusGame();
        if (board.addPiecesToBoard(placement)) {
            for (int i = 0; i < 9; i++) {
                State tmp1 = board.board[1 + i / 3][3 + i % 3];
                switch (challenge.charAt(i)) {
                    case 'R':
                        if (tmp1 != RED && tmp1 != EMPTY) return false;
                        break;
                    case 'W':
                        if (tmp1 != WHITE && tmp1 != EMPTY) return false;
                        break;
                    case 'B':
                        if (tmp1 != BLUE && tmp1!= EMPTY) return false;
                        break;
                    case 'G':
                        if (tmp1 != GREEN && tmp1 != EMPTY) return false;
                        break;
                }
            }
            return true;
        } else return false;
    }

    /**
     * Written By Matthew Tein
     * @param inputchar char from placement String
     * @return PieceType of corresponding char
     */
    public  static PieceType getPieceTypeFromChar(char inputchar){
        return PieceType.getPieceTypeFromChar(inputchar);
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
        FocusGame g = recursion(challenge,new FocusGame());
        System.out.println(g.current);
        return g.current;
    }

    private static FocusGame recursion(String challenge, FocusGame game) {
        ArrayList<String> pL = getAllPiecesWithCurrentState(challenge,game.current);
        for (String s : pL) {
            FocusGame g = new FocusGame();
            g.addPiecesToBoard(game.current);
            g.addPieceToBoard(s);
            //System.out.println("From prev level: " + game.current);
            System.out.println(g.current);
            if (g.getBoardPlacementString().length() == 40) {
                System.out.println("full string");
                if (g.checkCompletionGenerated(challenge)) {
                    System.out.println("----> Correct solution!");
                    return g;
                } else {
                    System.out.println("----> not a solution!");
                    break;
                }
            } else {
                ArrayList<String> pieces = getAllPiecesWithCurrentState(challenge, g.current);
                if (g.challengePartiallyCorrect(challenge) && !pieces.isEmpty() && !g.anyPiecesIsolated() && pieces.size() > (10 - g.current.length()/4)) {
                    System.out.println("----> partially correct ");
                    recursion(challenge,g);
                } else {
                    System.out.println("----> not partially correct!");
                    break;
                }
            }
        }
        return null;
    }

    private boolean anyPiecesIsolated() {
        int x = 0;
        int y = 0;
        boolean flag = false;
        while (x != 9) {
            if (x == 8) {
                x = 0;
                y++;
            }
            if (y == 5) {
                break;
            }
            if (board[y][x] == EMPTY) {
                //System.out.println("x:" + x + ", y:" + y + ", pos:" + getPositionsToCheck(x,y));
                switch (getPositionsToCheck(x,y)) {
                    case "LRTB":
                        if(board[y+1][x] != EMPTY && board[y-1][x] != EMPTY && board[y][x+1] != EMPTY && board[y][x-1] != EMPTY) {
                            flag = true;
                            break;
                        }
                        break;
                    case "LTB":
                        if(board[y+1][x] != EMPTY && board[y-1][x] != EMPTY && board[y][x-1] != EMPTY) {
                            flag = true;
                            break;
                        }
                        break;
                    case "RTB":
                        if(board[y+1][x] != EMPTY && board[y-1][x] != EMPTY && board[y][x+1] != EMPTY) {
                            flag = true;
                            break;
                        }
                        break;
                    case "LRT":
                        if(board[y-1][x] != EMPTY && board[y][x+1] != EMPTY && board[y][x-1] != EMPTY) {
                            flag = true;
                            break;
                        }
                        break;
                    case "LRB":
                        if(board[y+1][x] != EMPTY && board[y][x+1] != EMPTY && board[y][x-1] != EMPTY) {
                            flag = true;
                            break;
                        }
                        break;
                    case "RB":
                        if(board[y+1][x] != EMPTY && board[y][x+1] != EMPTY) {
                            flag = true;
                            break;
                        }
                        break;
                    case "LB":
                        if(board[y+1][x] != EMPTY && board[y][x-1] != EMPTY) {
                            flag = true;
                            break;
                        }
                        break;
                    case "LT":
                        if(board[y-1][x] != EMPTY && board[y][x-1] != EMPTY) {
                            flag = true;
                            break;
                        }
                        break;
                    case "RT":
                        if(board[y-1][x] != EMPTY && board[y][x+1] != EMPTY) {
                            flag = true;
                            break;
                        }
                        break;
                    default:
                        System.out.println("error : " + (getPositionsToCheck(x,y)) + " not in switch");
                }
            }
            x++;
        }
        return flag;
    }

    private String getPositionsToCheck(int x, int y) {
        char[] rtn = {'L','R','T','B'};
        if (y - 1 < 0) {
            rtn[2] = 'X';
        }
        if (x - 1 < 0) {
            rtn[0] = 'X';
        }
        if (x + 1 > 8) {
            rtn[1] = 'X';
        }
        if (y + 1 > 4) {
            rtn[3] = 'X';
        }
        return new String(rtn).replace("X","");
    }

    private static ArrayList<String> getAllPiecesWithCurrentState(String challenge, String boardPlacement) {
        ArrayList<String> pieces = new ArrayList<>();
        int x = 0;
        int y = 0;
        while (x <= 8) {
            while (y <= 4) {
                    Set<String> availablePlacements = getViablePiecePlacements(boardPlacement,challenge,x,y);
                    if (availablePlacements != null) {
                        for (String s : availablePlacements) {
                            if (!pieces.contains(s)) {
                                pieces.add(s);
                            }
                        }
                    }
                y++;
            }
            x++;
        }
        return pieces;
    }

    public static void main(String[] args) {
        /*FocusGame g = new FocusGame();
        g.addPiecesToBoard("e003a121g622f610d310c430i100");
        System.out.println(g.anyPiecesIsolated());
        g.resetBoard();
        g.addPieceToBoard("e003");
        System.out.println(g.anyPiecesIsolated());*/
        //getSolution("RRRBWBBRB");
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
    private void saveState() {
        saved = board.clone();
        for (int i = 0; i < board.length; i++)
            saved[i] = board[i].clone();
    }

    /**
     * This is written by Yuhui Wang
     * Load the current board state of the game.(An necessary function for a game and also important
     * method for finding solution)
     */
    private void loadState() {
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
        boolean result = isPlacementStringWellFormed(placement);
        saveState();
        if (result) {
            for (int i = 0; i < placement.length(); i += 4) {
                String  p = placement.substring(i,i+4);
                if (checkPieceToBoard(p))
                    addPieceToBoard(p);
                else
                    return false;
            }
        }
        else
            loadState();
            return result;
    }

    /**
     * This is written by Yuhui Wang
     * @param piecePlacement The piece placement need to be checked before adding to board
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




