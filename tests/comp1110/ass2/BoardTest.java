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
    }

    @Test
    public void getPiecesFromPlacement() {
        Piece[] test1 = new Piece[]{
            new Piece("a000")
        };
        getPiecesTest("a000", test1);
    }

    @Test
    public void getLocationFromXY() {
        Location test1 = new Location(0,0);
        getLocationTest(BOARD_X+BOARD_PADDING_LEFT+20,BOARD_Y+BOARD_PADDING_TOP+20,test1);
    }

}
