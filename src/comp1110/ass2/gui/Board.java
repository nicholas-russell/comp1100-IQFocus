package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Board extends Application {
    //TODO-D2A - Matt

    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;

    // FIXME Task 7: Implement a basic playable Focus Game in JavaFX that only allows pieces to be placed in valid places
    public boolean isPlacementValid (String placement){


    }
    /* Firstly needs the usage of another Class for State, which Yuhui has created in his branch.

     * isPLacementValid should intake a placement string with data related to a piece being placed. This method will be
     * mainly made up of if cases which checks that all the slots the piece is being placed in are a State value of empty
     * ;returning false. Otherwise returns true for non-valid placements.
     *
     * Each individual piece, a,b,c,d... will need
     * its own if case as the pieces are all unique shapes.
     *
     *Note:This method is similar in nature to the one performed in Task 6 of Assignment 1

     */
    // FIXME Task 8: Implement challenges (you may use challenges and assets provided for you in comp1110.ass2.gui.assets: sq-b.png, sq-g.png, sq-r.png & sq-w.png)
    public



    // FIXME Task 10: Implement hints
    public enum KeyInput
    /*


    */

    // FIXME Task 11: Generate interesting challenges (each challenge may have just one solution)

    @Override
    public void start(Stage primaryStage) {

    }
}
