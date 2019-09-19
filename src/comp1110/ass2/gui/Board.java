package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Board extends Application {

    private static final double SQUARE_SIZE = 70;
    private static final int VIEWER_WIDTH = 720;
    private static final int VIEWER_HEIGHT = 520;
    private static final int BOARD_PADDING_TOP = 87;
    private static final int BOARD_PADDING_LEFT = 41;
    private static final int BOARD_MARGIN_RIGHT = 43;
    private static final int BOARD_MARGIN_BOTTOM = 20;
    private static final double SQUARE_SCALE_FACTOR = 0.70;
    private static final int WINDOW_WIDTH = 933;
    private static final int WINDOW_HEIGHT = 650;
    private static final double BOARD_SCALE_FACTOR = 0.69;
    private double CHALLENGE_POS_X;
    private double CHALLENGE_POS_Y;

    private int BOARD_X;
    private int BOARD_Y;
    private double BOARD_HEIGHT;
    private double BOARD_WIDTH;

    private ImageView[] controlImages = new ImageView[10];

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private Pane boardPieces = new Pane();
    private Pane board = new Pane();
    private Pane errors = new Pane();
    private Pane controlPieces = new Pane();
    private Pane challengeSquares = new Pane();

    /* challangeSquare is a 9 character String, with each character corresponding to the
    state of one of the squares that makes up the central 3x3 challange square
    *
    *                         [0][1][2]
    *                         [3][4][5]
    *                         [6][7][8]
    */
    private String challenge;

    /**
     * @param location The location on the board that you want the state for
     * @return The state of the square
     */
    public State getStateFromLocation (Location location) {
        return null;
    }

    // FIXME Task 8: Implement challenges (you may use challenges and assets provided for you in comp1110.ass2.gui.assets: sq-b.png, sq-g.png, sq-r.png & sq-w.png)
    public String challengeEncoding (String challenge, String boardState) {

        //Example of what encoding would look like, also need to covert Char to Variable Colour, B -> Blue, R -> Red
        char[] encodingArray = challenge.toCharArray();
        /**  ChallengeSquare0 = encodingArray[0];
         *

         */

        //When Board Updates (e.g piece is placed) the squares of the challenge square are checked against the
        //stored states encoded above. If all states match should end the game and print victory Screen/message.

        return challenge;
        }

    public String getChallenge() {
       return challenge;
    }

    /* Implementing Challeges from TestUtility.

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


    /* When the User holds down the "/" key, they are suppose to "see" one or more boardPieces they can play to help them
    * towards the solution!
     * Used in tandem with Javafx method found on line 131
     *
    * The easiest method would be to simply return in the interface the letter of the corresponding piece/s such as A,
    * G or D.
    *
    * Second Method would be to somehow highlight the piece, outlining or distinguishing it from the other boardPieces.
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


    /**
     *  Returns JavaFX Text object for error, styled to be red and bold.
     * @param text String that should be displayed for error.
     * @return JavaFX Text object styled as error.
     */
    private Text getErrorText(String text) {
        Text error = new Text("ERROR: " + text);
        error.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        error.setFill(Color.RED);
        return error;
    }

    /**
     * Draws error box onto viewer
     * @param err Error string
     */
    private void drawErrorBox(String err) {
        HBox errorBox = new HBox();
        errorBox.getChildren().add(getErrorText(err));
        errorBox.setAlignment(Pos.CENTER);
        errorBox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        errorBox.setLayoutX(0);
        errorBox.setLayoutY(0);
        errors.getChildren().add(errorBox);
    }

    /**
     * Gives an array of Piece objects from a placement string
     * @param placement Placement string
     * @return Array of Piece's from placement string
     */
    private Piece[] getPiecesFromPlacement(String placement) {
        if (!FocusGame.isPlacementStringWellFormed(placement)) {
            return null;
        }
        int numberOfPieces = placement.length()/4;
        int i = 0;
        int pIndex = 0;
        Piece[] pieces = new Piece[numberOfPieces];
        while (i < numberOfPieces*4) {
            pieces[pIndex] = new Piece(placement.substring(i,i+4));
            i += 4;
            pIndex++;
        }
        return pieces;
    }

    /**
     * Returns x & y offset values depending on PieceType and Orientation.
     * These are multiplied by the SQUARE_SIZE to give a real pixel value.
     * @param pieceType The PieceType of the piece being drawn
     * @param orientation The Orientation of the piece being drawn
     * @return Array of two doubles, the first for the x offset and the second for the y offset.
     */
    private double[] getOrientationOffsets(PieceType pieceType, Orientation orientation) {
        double[] offsets = new double[]{0, 0};
        switch (orientation) {
            case One:
                switch (pieceType) {
                    case A:
                    case D:
                    case E:
                    case G:
                        offsets[0] = -0.5;
                        offsets[1] = 0.5;
                        break;
                    case B:
                    case C:
                    case F:
                    case J:
                        offsets[0] = -1;
                        offsets[1] = 1;
                        break;
                    case I:
                    case H:
                        break;
                }
                break;
            case Two:
                switch (pieceType) {
                    case A:
                    case B:
                    case C:
                    case D:
                    case E:
                    case F:
                    case G:
                    case H:
                    case I:
                    case J:
                        offsets[0] = 0;
                        offsets[1] = 0;
                        break;
                }
                break;
            case Three:
                switch (pieceType) {
                    case A:
                    case D:
                    case E:
                    case G:
                        offsets[0] = -0.5;
                        offsets[1] = 0.5;
                        break;
                    case F:
                    case J:
                        offsets[0] = -1;
                        offsets[0] = 1;
                        break;
                    case H:
                    case I:
                        break;
                    case B:
                    case C:
                        offsets[0] = -1;
                        offsets[1] = 1;
                        break;
                }
                break;
            case Zero:
            default:
                break;
        }
        return offsets;
    }


    /**
     * Gets JavaFX ImageView object for a piece and scales it.
     * @param p The PieceType
     * @return Scaled ImageView of the piece object.
     */
    private ImageView getPieceImageFromFile(PieceType p) {
        InputStream pieceFile = getClass().getResourceAsStream(URI_BASE + p.toString().toLowerCase() + ".png");
        Image pieceImage = new Image(pieceFile);
        double imageHeight = pieceImage.getHeight();
        ImageView pieceImageView = new ImageView(pieceImage);
        pieceImageView.setFitHeight(BOARD_SCALE_FACTOR*SQUARE_SCALE_FACTOR*imageHeight);
        pieceImageView.setPreserveRatio(true);
        return pieceImageView;
    }

    private ImageView getSquareImageFromFile(Character c) {
        InputStream squareFile = getClass().getResourceAsStream(URI_BASE + "sq-" + c.toString().toLowerCase() + ".png");
        Image squareImage = new Image(squareFile);
        double imageHeight = squareImage.getHeight();
        ImageView squareImageView = new ImageView(squareImage);
        squareImageView.setFitHeight(BOARD_SCALE_FACTOR*SQUARE_SCALE_FACTOR*imageHeight);
        squareImageView.setPreserveRatio(true);
        return squareImageView;
    }

    /**
     * Generates ImageViews for all Piece's in an array
     * @param pieceList The array of Piece's to be generated
     * @return An array of ImageView's to display
     */
    private ImageView[] getImageFromPiece(Piece[] pieceList) {
        ImageView[] images = new ImageView[pieceList.length];
        int i = 0;
        for (Piece p : pieceList) {
            images[i] = getPieceImageFromFile(p.getPieceType());
            double[] offsets = getOrientationOffsets(p.getPieceType(), p.getOrientation());
            double xPos = BOARD_PADDING_LEFT +p.getLocation().getX()*SQUARE_SIZE;
            double yPos = BOARD_PADDING_TOP +p.getLocation().getY()*SQUARE_SIZE;
            int angle = p.getOrientation().toInt()*90;
            images[i].setRotate(angle);
            images[i].setX(xPos+(SQUARE_SIZE*offsets[0]));
            images[i].setY(yPos+(SQUARE_SIZE*offsets[1]));
            i++;
        }
        return images;
    }

    private void makePlacement(String placement) {
        errors.getChildren().clear();
        boardPieces.getChildren().clear();
        if (!FocusGame.isPlacementStringWellFormed(placement)) {
            drawErrorBox("Placement string not valid");
            return;
        }
        Piece[] pieceList = getPiecesFromPlacement(placement);
        ImageView[] imageList = getImageFromPiece(pieceList);
        boardPieces.getChildren().addAll(imageList);
    }

    private void makeControls() {

    }

    private void makeBoard() throws IOException {
        Image boardImage = new Image(new FileInputStream(URI_BASE + "board.png"));
        ImageView boardIv = new ImageView(boardImage);
        boardIv.setPreserveRatio(true);
        boardIv.setFitHeight(boardImage.getHeight()*BOARD_SCALE_FACTOR);
        boardIv.setX(Math.round((WINDOW_WIDTH - boardImage.getWidth()*BOARD_SCALE_FACTOR)/2));
        this.BOARD_X = (int)boardIv.getX();
        this.BOARD_Y = (int)boardIv.getY();
        this.BOARD_HEIGHT = boardImage.getHeight()*BOARD_SCALE_FACTOR;
        this.BOARD_WIDTH = boardImage.getWidth()*BOARD_SCALE_FACTOR;
        board.getChildren().addAll(boardIv);
    }

    private void makeControlPieces() {
        int i = 0;
        for (PieceType p : PieceType.values()) {
            controlImages[i] = getPieceImageFromFile(p);
            i++;
        }

        double yOff = BOARD_Y + BOARD_HEIGHT + BOARD_MARGIN_BOTTOM;
        double squareOff = BOARD_SCALE_FACTOR*SQUARE_SCALE_FACTOR*100;
        double yPadding = 20.0;
        controlImages[0].setY(yOff);
        controlImages[0].setX(10);
        controlImages[1].setY(yOff);
        controlImages[1].setX(10+squareOff*3);
        controlImages[2].setY(yOff);
        controlImages[2].setX(10+squareOff*7);
        controlImages[3].setY(yOff);
        controlImages[3].setX(10+squareOff*11);
        controlImages[4].setY(yOff);
        controlImages[4].setX(10+squareOff*15);
        controlImages[5].setY(yOff+squareOff*2+yPadding);
        controlImages[5].setX(10);
        controlImages[6].setY(yOff+squareOff*2+yPadding);
        controlImages[6].setX(10+squareOff*4);
        controlImages[7].setY(yOff+squareOff*2+yPadding);
        controlImages[7].setX(10+squareOff*8);
        controlImages[8].setY(yOff+squareOff*2+yPadding);
        controlImages[8].setX(10+squareOff*12);
        controlImages[9].setY(yOff+squareOff*2+yPadding);
        controlImages[9].setX(10+squareOff*15);


        controlPieces.getChildren().addAll(controlImages);
    }

    private ImageView getChallengeSquare(Character c) {
        ImageView challengeSq = getSquareImageFromFile(c);
        return challengeSq;
    }

    private void makeChallenge(String challengeString) {
        char[] challengeChar = challengeString.toCharArray();
        int row = 0;
        int col = 0;
        double sqOff = BOARD_SCALE_FACTOR*SQUARE_SCALE_FACTOR*100;
        CHALLENGE_POS_X = (WINDOW_WIDTH-BOARD_WIDTH)/4-1.5*sqOff;
        CHALLENGE_POS_Y = BOARD_HEIGHT/2-1.5*sqOff;
        for (Character c : challengeChar) {
            ImageView chSq = getChallengeSquare(c);
            if (col > 2) {
                col = 0;
                row++;
            }
            chSq.setX(CHALLENGE_POS_X+col*sqOff);
            chSq.setY(CHALLENGE_POS_Y+row*sqOff);
            challengeSquares.getChildren().add(chSq);
            col++;
        }
        Text challengeTitle = new Text("Challenge");
        challengeTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        challengeTitle.setFill(Color.BLACK);
        challengeTitle.setX(CHALLENGE_POS_X);
        challengeTitle.setY(CHALLENGE_POS_Y-10);
        challengeSquares.getChildren().add(challengeTitle);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FocusGame");
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        root.getChildren().addAll(
                controls,
                board,
                boardPieces,
                controlPieces,
                errors,
                challengeSquares
        );

        makeControls();
        makeBoard();
        makeControlPieces();
        makeChallenge("RRRBWBBRB");

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
