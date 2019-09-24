package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;


import static comp1110.ass2.TestUtility.*;
import static org.junit.Assert.assertTrue;

public class Piece_Test {

    // original duration < 0.15 sec -> 1 sec
    @Rule
    public Timeout globalTimeout = Timeout.millis(1000);

    private void placementToLocationTest(String placement, boolean expected) {
        boolean out = Piece.placementToLocationCheck(placement);
        assertTrue("Input placement string '" + placement + "', expected " + expected + " but instead got " + out, out == expected);
    }



    @Test
    public void placementToLocationGood() {
        String goodTestPlacements[] = new String[]{ "a000b013c113d302e323f400g420h522i613j701",
                "a513b130c502d002e020f401g721h101i713j332"
        };

        for(int i = 0; i < goodTestPlacements.length; i++){
            placementToLocationTest(goodTestPlacements[i], true);
        }
    }

    @Test
    public void placementToLocationBad() {

        String badTestPlacements[] = new String[]{ "a000b013c113d302e323f400g420h522i613j701",
                "a513b130c502d002e020f401g721h101i713j332"
        };
        for(int i = 0; i < badTestPlacements.length; i++){
            placementToLocationTest(badTestPlacements[i], false);
        }
    }
}


