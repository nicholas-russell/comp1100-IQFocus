package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;


import static comp1110.ass2.TestUtility.*;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class Piece_Test {

    // original duration < 0.15 sec -> 1 sec
    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);


    /* Fringe Cases, valid or invalid inputs; one is valid one coord-axis and one is invalid
     *
     */
    private void placementToLocationTest(String placement, Location expected) {
        Location out = Piece.placementToLocation(placement);
        assertEquals(expected, out);
    }


    private void placementToPieceTypeTest(String placement, boolean expected){
        boolean out = Piece.placementToPieceTypeCheck(placement);
        assertTrue("Input placement string '" + placement + "', expected " + expected + " but instead got " + out, out == expected);
    }



    @Test
    public void placementToLocationGood() {

        String goodTestPlacements[] = new String[]{
                "a513b",
                "a000b"
        };

        for(int i = 0; i < goodTestPlacements.length; i++){
            placementToLocationTest(goodTestPlacements[i],  new Location(5, 1));
        }
    }

    @Test
    public void placementToLocationBad() {

        String badTestPlacements[] = new String[]{
                "a990",
                "b993"
        };
        for(int i = 0; i < badTestPlacements.length; i++){
            placementToLocationTest(badTestPlacements[i], new Location(0, 0) );
        }
    }

    @Test
    public void placementToPieceTypeGood(){
        String goodTestPlacements[] = new String[]{
                "a313",
                "a000"

        };
        placementToPieceTypeTest(goodTestPlacements[0], true);
        placementToPieceTypeTest(goodTestPlacements[1], true);
    }


    @Test
    public void placementToPieceTypeBad(){
        String badTestPlacements[] = new String[]{
                "b993",
                "c586"
        };
        placementToPieceTypeTest(badTestPlacements[0], false);
    }


}


