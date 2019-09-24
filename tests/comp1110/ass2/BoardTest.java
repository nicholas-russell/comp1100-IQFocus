package comp1110.ass2;

import comp1110.ass2.gui.Board;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.io.IOException;

import static org.junit.Assert.*;

public class BoardTest {

    Board board = new Board();

    // taken from output of running Board class
    private double BOARD_X = 218.0;
    private double BOARD_Y = 0;
    private double BOARD_WIDTH = 496.80;
    private double BOARD_HEIGHT = 319.47;
    private static final int BOARD_PADDING_TOP = 87;
    private static final int BOARD_PADDING_LEFT = 41;
    private final double SCALED_SQUARE_SIZE = 48.30;

    @Before
    public void main() throws IOException {
        board.makeBoard();
        board.initVariables();
    }

    // Original duration < 0.05sec * 10 = 0.5sec
    @Rule
    public Timeout globalTimeout = Timeout.millis(500);

    private void xyOnBoardTest(double x, double y, boolean expected) throws IOException {
        boolean out = board.xyOnBoard(x, y);
        assertTrue("Input was x:" + x + ", y:" + y +" and " + expected + " was expected but got " + out +" instead.", out == expected);
    }

    private void getPiecesTest(String placement, Piece[] expected) {
        Piece[] out = board.getPiecesFromPlacement(placement);
        assertArrayEquals(expected, out);
    }

    private void getLocationTest(double x, double y, Location expected) {
        Location out = board.getLocationFromSceneXY(x,y);
        assertEquals(expected.toString(), out.toString());
    }

    @Test
    public void xyOnBoard() throws IOException {
        xyOnBoardTest(393.43, 189.47, true);
        xyOnBoardTest(360.10, 252.80, true);
        xyOnBoardTest(172.23, 64.80, false);
        xyOnBoardTest(247.07, 37.40, false);
        xyOnBoardTest(628.77, 204.14, true);

    }

    @Test
    public void getLocationFromXY() {
        Location test2 = new Location(3,2);

        getLocationTest(269, 91,new Location(0,0));
        getLocationTest(259, 139,new Location(0,1));
        getLocationTest(314, 241,new Location(1,3));
        getLocationTest(510, 181,new Location(6,2));
        getLocationTest(409, 170,new Location(3,1));
        getLocationTest(451, 236,new Location(4,3));
        getLocationTest(478, 113,new Location(5,0));
        getLocationTest(307, 173,new Location(1,1));

    }

}
