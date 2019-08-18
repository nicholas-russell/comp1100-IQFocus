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
    public String challangeEncoding(String challangeSquare) {
        char[] encodingArray = challangeSquare.toCharArray();


        //Example of what encoding would look like, also need to covert Char to Variable Colour, B -> Blue, R -> Red
        Square 0 = encodingArray[0];
        ......

    }

    /* Implementing Challanges from TestUtility.
    *How encoding the objective central 9 squares work. The Objective 3x3 square is split into the 3 rows. Starting from
    * 0, each square is encoded with a number for example 0, then the square to the right is encoded with the next
    * corresponding number e.g. 1. So the encoding for the 3x3 objective square will look like this,
    *
    *                         [0][1][2]
    *                         [3][4][5]
    *                         [6][7][8]
    *
    * Then we can implement challenges by assigning colours to the corresponding squares of the objective square.
    *
    *       This encoding design is provided in the Assets folder under the file challenge_encoding.png
    *


     */

    // FIXME Task 10: Implement hints
    public class user

    /* When the User holds down the "/" key, they arte suppose to "see" one or more pieces they can play to help them
    * towards the solution! Javafx
    * This can be done is a number of possible way, the easiest method would be to simply return in the interface the
    * letter of the corresponding piece/s such as A, G or D.
    *
    *Second Method would be to somehow highlight the piece, outlining or distinguishing it from the other pieces.
    *
    *
    */

    // FIXME Task 11: Generate interesting challenges (each challenge may have just one solution)

    @Override
    public void start(Stage primaryStage) {

    }
}
