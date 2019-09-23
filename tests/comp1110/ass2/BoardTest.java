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
        assertEquals(out.toString(), expected.toString());
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
        Location test2 = new Location(3,2);

        getLocationTest(263.33, 70.14,new Location(0,0));
        getLocationTest(263.33, 70.14,new Location(0,0));
        getLocationTest(263.33, 70.14,new Location(0,0));
        getLocationTest(263.33, 70.14,new Location(0,0));
        getLocationTest(263.33, 70.14,new Location(0,0));

    }

}
