package comp1110.ass2;

import comp1110.ass2.gui.Board;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static comp1110.ass2.TestUtility.SOLUTIONS;
import static org.junit.Assert.*;

public class BoardTest {

    // taken from output of running Board class
    private double BOARD_X = 218.0;
    private double BOARD_Y = 0;
    private double BOARD_WIDTH = 496.80;
    private double BOARD_HEIGHT = 319.47;
    private static final int BOARD_PADDING_TOP = 87;
    private static final int BOARD_PADDING_LEFT = 41;
    private final double SCALED_SQUARE_SIZE = 48.30;

    // Original duration < 0.05sec * 10 = 0.5sec
    @Rule
    public Timeout globalTimeout = Timeout.millis(500);

    private void xyOnBoardTest(double x, double y, boolean expected) {
        boolean out = Board.xyOnBoard(x, y);
        assertTrue("Input was x:" + x + ", y:" + y +" and " + expected + " was expected but got " + out +" instead.", out == expected);
    }

    private void getPiecesTest(String placement, Piece[] expected) {
        Piece[] out = Board.getPiecesFromPlacement(placement);
        assertArrayEquals(expected, out);
    }

    private void getLocationTest(double x, double y, Location expected) {
        Location out = Board.getLocationFromPointer(x,y);
        assertEquals(expected,out);
    }

    @Test
    public void xyOnBoard() {
        xyOnBoardTest(BOARD_X+BOARD_WIDTH/2,BOARD_Y+BOARD_HEIGHT/2, true);
        xyOnBoardTest(BOARD_X+100,BOARD_Y+100, true);
        xyOnBoardTest(BOARD_X+SCALED_SQUARE_SIZE*5,BOARD_Y+SCALED_SQUARE_SIZE*3, true);

        xyOnBoardTest(BOARD_X+SCALED_SQUARE_SIZE*11+BOARD_PADDING_LEFT,BOARD_Y+BOARD_PADDING_TOP+SCALED_SQUARE_SIZE*6, false);
        xyOnBoardTest(BOARD_X-1,BOARD_Y-1, false);
    }

    @Test
    public void getPiecesFromPlacement() {
        Piece[] test1 = new Piece[]{
            new Piece("a000")
        };
        Piece[] test2 = new Piece[]{
            new Piece("a000"),
            new Piece("b013"),
            new Piece("c113"),
            new Piece("d302")
        };
        Piece[] test3 = new Piece[]{
                new Piece("e323"),
                new Piece("f400"),
                new Piece("g420"),
                new Piece("h522"),
                new Piece("i613"),
                new Piece("j701")
        };
        getPiecesTest("a000", test1);
        getPiecesTest("a000b013c113d302", test2);
        getPiecesTest("e323f400g420h522i613j701", test3);
    }

    @Test
    public void getLocationFromXY() {
        Location test1 = new Location(0,0);
        Location test2 = new Location(3,2);

        getLocationTest(BOARD_X+BOARD_PADDING_LEFT+20,BOARD_Y+BOARD_PADDING_TOP+20,test1);
        getLocationTest(BOARD_X+BOARD_PADDING_LEFT+SCALED_SQUARE_SIZE*3+5,BOARD_Y+BOARD_PADDING_TOP+SCALED_SQUARE_SIZE*3+5,test2);
        getLocationTest(BOARD_X+BOARD_PADDING_LEFT-200,BOARD_Y+BOARD_PADDING_TOP+20,null);
        getLocationTest(BOARD_X+BOARD_PADDING_LEFT+1000,BOARD_Y+BOARD_PADDING_TOP+1000,null);

    }

}
