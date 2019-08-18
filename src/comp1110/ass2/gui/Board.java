package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Board extends Application {

    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;

    private String challenge; /* challangeSquare is a 9 character String, with each character corresponding to the
    state of one of the squares that makes up the central 3x3 challange square
    *
    *                         [0][1][2]
    *                         [3][4][5]
    *                         [6][7][8]
    */


    // FIXME Task 7: Implement a basic playable Focus Game in JavaFX that only allows pieces to be placed in valid places
    public boolean isPlacementValid (String placement){
        return false;

    }
    /* Firstly needs the usage of another Class for State, which Yuhui has created in his branch.

     * isPlacementValid should intake a placement string with data related to a piece being placed. This method will be
     * mainly made up of if cases which checks that all the slots the piece is being placed in are a State value of empty
     * ;returning false. Otherwise returns true for non-valid placements.
     *
     * Each individual piece, a,b,c,d... will need
     * its own if case as the pieces are all unique shapes.
     *
     *Note:This method is similar in nature to the one performed in Task 6 of Assignment 1

     */

    // FIXME Task 8: Implement challenges (you may use challenges and assets provided for you in comp1110.ass2.gui.assets: sq-b.png, sq-g.png, sq-r.png & sq-w.png)
    public String challengeEncoding (String challenge) {
        char[] encodingArray = challenge.toCharArray();


        //Example of what encoding would look like, also need to covert Char to Variable Colour, B -> Blue, R -> Red
        /**  ChallengeSquare 0 = encodingArray[0];
         *

         */

        //
        


        return challenge;
    }


    public String getchallenge() {
       return challenge;
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
    public class hints {


    }


    /* When the User holds down the "/" key, they are suppose to "see" one or more pieces they can play to help them
    * towards the solution!
     * Used in tandem with Javafx method found on line 131
     *
    * The easiest method would be to simply return in the interface the letter of the corresponding piece/s such as A,
    * G or D.
    *
    * Second Method would be to somehow highlight the piece, outlining or distinguishing it from the other pieces.
    *
    *
    */

    // FIXME Task 11: Generate interesting challenges (each challenge may have just one solution)
    public String challengeGenerator (int difficulty) {

        if (difficulty == 1) {
            //Generate challenge

            //Assign challenge
            challenge = "BBBBBBBBB";

            return challenge;
        }

        if (difficulty == 2){
            return challenge;
        }

        if (difficulty == 3){
            return challenge;
        }
        return challenge;

        /* The current system is crude, but the idea is that the user may input an Int ranging from 1 - 3, 1 = easy
        *2 = medium, 3 = hard, NOTE WE CAN PLAY AROUND WITH THIS AS THE DIFFICULTY AND NUMBERS ARE ARBITRARY
        * . The System then generates a 9 char String which can be read by the challengeEncoding
        * method under task 8.
        *
        *
        - How To Generate Different levels of difficulty?
        * Although generating a random 9 char string is not that hard, difficulty depends on the number of solutions
        * available for a given challenge square.
         */
    }
    @Override
    //To Be used in Tandem with Task 10
    public void start(Stage primaryStage) {


      /**  primaryStage.setTitle("Hints");

        scene.setOnKeyPressed(event ->
        {if (event.getCharacter() == "/") {
                // Hints Coded Here
            }
       }); */

    }
}
