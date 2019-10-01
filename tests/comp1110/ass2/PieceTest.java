package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;


import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class PieceTest {

    // original duration < 0.15 sec -> 1 sec
    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);


    /* Fringe Cases, valid or invalid inputs; one is valid one coord-axis and one is invalid
     *
     */
    private void placementToLocationTest(String placement, Location expected) {
        Location out = Piece.placementToLocation(placement);
        assertEquals(expected.toString(), out.toString());
    }


    private void placementToPieceTypeTest(String placement, PieceType expected){
        PieceType out = Piece.placementToPieceType(placement);
        assertTrue("Input placement string '" + placement + "', expected " + expected + " but instead got " + out, out == expected);
    }



    @Test
    public void placementToLocationGood() {

        String goodTestPlacements[] = new String[]{
                "a513b",
                "a000b"
        };
        placementToLocationTest(goodTestPlacements[0],  new Location(5, 1));
        placementToLocationTest(goodTestPlacements[1],  new Location(0, 0));
    }

    @Test
    public void placementToLocationBad() {

        String badTestPlacements[] = new String[]{
                "a990",
                "b993"
        };
        placementToLocationTest(badTestPlacements[0], new Location(9, 9) );
        placementToLocationTest(badTestPlacements[0], new Location(9, 9) );
    }

    @Test
    public void placementToPieceTypeGood(){
        String goodTestPlacements[] = new String[]{
                "a313",
                "a000"

        };
        placementToPieceTypeTest(goodTestPlacements[0], PieceType.A);
        placementToPieceTypeTest(goodTestPlacements[1], PieceType.A);
    }


    @Test
    public void placementToPieceTypeBad(){
        String badTestPlacements[] = new String[]{
                "b993",
                "c586"
        };
        placementToPieceTypeTest(badTestPlacements[0], PieceType.B);
    }


}


