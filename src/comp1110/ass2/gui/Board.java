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
import java.util.Random;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Board extends Application {
    //Class constants
    private static final double SQUARE_SIZE = 100; // square size from image
    private static final double SQUARE_SCALE_FACTOR = 0.70; // factor to scale to full size board

    private static final int WINDOW_WIDTH = 933;
    private static final int WINDOW_HEIGHT = 650;

    private static final int BOARD_PADDING_TOP = 87; // grey part of board on top
    private static final int BOARD_PADDING_LEFT = 41; // grey part of board on left
    private static final int BOARD_PADDING_RIGHT = 43;

    private static final double BOARD_MARGIN_TOP = 0; // margin of board to top of screen
    private static final int BOARD_MARGIN_BOTTOM = 20; // margin of board underneath
    private static final double BOARD_SCALE_FACTOR = 0.69; // scale factor NOTE: will scale everything else.

    // Class variables that are set upon initialisation functions
    private double CHALLENGE_POS_X;
    private double CHALLENGE_POS_Y;
    private int BOARD_X;
    private int BOARD_Y;
    private double BOARD_HEIGHT;
    private double BOARD_WIDTH;
    private double SCALED_SQUARE_SIZE;


    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private Pane boardPieces = new Pane();
    private Pane board = new Pane();
    private Pane errors = new Pane();
    private Pane controlPieces = new Pane();
    private Pane challengeSquares = new Pane();
    private ImageView[] controlImages = new ImageView[10];

    /* challangeSquare is a 9 character String, with each character corresponding to the
    state of one of the squares that makes up the central 3x3 challange square
    *
    *                         [0][1][2]
    *                         [3][4][5]
    *                         [6][7][8]
    */
    private String challenge;

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
    private final Random randomThing = new Random();
    private int g;
    private String[] pack = new String[9];

    public String challengeGenerator (int difficulty) {


        //Generate Completely Random String
        if (difficulty == 0){
            for(int j = 0; j < 9; j++){
                String g = generateRandomColor();
                pack[j] = g;
            }
            challenge = pack[0] + pack[1] + pack[2] + pack[3] + pack[4] + pack[5] + pack[6] + pack[7] + pack[8];
            return challenge;


        }


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
        *
         */
    }


    //Generates a random color, random numbers 0-3 corresponding to one of the 4 colors states
    private String generateRandomColor() {

        int g = randomThing.nextInt(4);

        switch (g) {
            case 0:
                return "B";
            case 1:
                return "R";
            case 2:
                return "G";
            case 3:
                return "W";
            default:
                return "L";
        }
    }

    //==========================================================================//

    /**
     * TODO: Move to game logic
     * @param location The location on the board that you want the state for
     * @return The state of the square
     */
    public State getStateFromLocation (Location location) {
        return null;
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
    public static Piece[] getPiecesFromPlacement(String placement) {
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

    /**
     * Gets ImageViews of individual colour squares to show the Challenge
     * @param c Char representing colour of square (R W B G)
     * @return ImageView of individual square
     */
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
        System.out.println("DEBUG");
        System.out.println("===========================================");
        for (Piece p : pieceList) {
            System.out.println("Placing " + p.toString());
            images[i] = getPieceImageFromFile(p.getPieceType());
            double[] offsets = getOrientationOffsets(p.getPieceType(), p.getOrientation());
            double xPos = BOARD_X + BOARD_PADDING_LEFT*BOARD_SCALE_FACTOR + p.getLocation().getX()* SQUARE_SIZE*SQUARE_SCALE_FACTOR*BOARD_SCALE_FACTOR;
            double yPos = BOARD_Y + BOARD_PADDING_TOP*BOARD_SCALE_FACTOR + p.getLocation().getY()* SQUARE_SIZE*SQUARE_SCALE_FACTOR*BOARD_SCALE_FACTOR;
            System.out.println("xPos " + xPos + ", yPos " + yPos);
            int angle = p.getOrientation().toInt()*90;
            images[i].setRotate(angle);
            images[i].setX(xPos+(SQUARE_SCALE_FACTOR*BOARD_SCALE_FACTOR*SQUARE_SIZE*offsets[0]));
            images[i].setY(yPos+(SQUARE_SCALE_FACTOR*BOARD_SCALE_FACTOR*SQUARE_SIZE*offsets[1]));
            i++;
        }
        return images;
    }

    /**
     * TODO
     * Gets board location given location of top left of image view
     * @param iX screen x value of top left of a piece's ImageView
     * @param iY screen y value of top left of a piece's ImageView
     * @return Instance of Location with valid x,y co-ords, or FALSE/NULL if not valid
     */
    public static Location getLocationFromPointer(double iX, double iY) {
        return null;
    }

    /**
     * TODO
     * Returns true if x/y location is on the board
     * @param x x location in window
     * @param y y location in window
     * @return
     */
    public static boolean xyOnBoard(double x, double y) { return false; }

    /**
     * TODO: remove piece from controls
     * Make a piece placement on the board (graphically)
     * @param placement
     */
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

    /**
     * TODO
     * Makes game controls
     */
    private void makeControls() {

    }

    /**
     * Loads board image and displays it
     * @throws IOException
     */
    private void makeBoard() throws IOException {
        Image boardImage = new Image(new FileInputStream(URI_BASE + "board.png"));
        ImageView boardIv = new ImageView(boardImage);
        boardIv.setPreserveRatio(true);
        boardIv.setFitHeight(boardImage.getHeight()*BOARD_SCALE_FACTOR);
        boardIv.setX(Math.round((WINDOW_WIDTH - boardImage.getWidth()*BOARD_SCALE_FACTOR)/2));
        boardIv.setY(BOARD_MARGIN_TOP);
        this.BOARD_X = (int)boardIv.getX();
        this.BOARD_Y = (int)boardIv.getY();
        this.BOARD_HEIGHT = boardImage.getHeight()*BOARD_SCALE_FACTOR;
        this.BOARD_WIDTH = boardImage.getWidth()*BOARD_SCALE_FACTOR;
        board.getChildren().addAll(boardIv);
    }

    /**
     * TODO: make draggable
     * Displays control pieces on screen
     */
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

    /**
     * Displays 9 square challenge on screen
     * @param challengeString 9 character challenge string
     */
    private void makeChallenge(String challengeString) {
        char[] challengeChar = challengeString.toCharArray();
        int row = 0;
        int col = 0;
        for (Character c : challengeChar) {
            ImageView chSq = getSquareImageFromFile(c);
            if (col > 2) {
                col = 0;
                row++;
            }
            chSq.setX(CHALLENGE_POS_X+col*SCALED_SQUARE_SIZE);
            chSq.setY(CHALLENGE_POS_Y+row*SCALED_SQUARE_SIZE);
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

    /**
     * Prints debug statements
     */
    private void debug() {
        System.out.println("DEBUG");
        System.out.println("===========================================");
        System.out.println("BOARD_X " + BOARD_X + ", BOARD_Y " + BOARD_Y);
        System.out.println("BOARD_WIDTH " + BOARD_WIDTH + ", BOARD_HEIGHT " + BOARD_HEIGHT);
        System.out.println("Est BOARD_WIDTH =" + (BOARD_PADDING_LEFT*BOARD_SCALE_FACTOR*2+BOARD_SCALE_FACTOR*SQUARE_SIZE*SQUARE_SCALE_FACTOR*9));
        System.out.println("Full size BOARD_WIDTH=" + (BOARD_PADDING_LEFT*2+SQUARE_SIZE*SQUARE_SCALE_FACTOR*9));
        System.out.println("Scaled square size=" + SCALED_SQUARE_SIZE);

    }

    /**
     * Initialises class variables for positioning pieces.
     */
    private void initVariables() {
        SCALED_SQUARE_SIZE = BOARD_SCALE_FACTOR*SQUARE_SCALE_FACTOR*SQUARE_SIZE;
        CHALLENGE_POS_X = (WINDOW_WIDTH-BOARD_WIDTH)/4-1.5*SCALED_SQUARE_SIZE;
        CHALLENGE_POS_Y = BOARD_HEIGHT/2-1.5*SCALED_SQUARE_SIZE;
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

        makeBoard();
        initVariables();
        makeControls();
        debug();
        makeControlPieces();
        makeChallenge("RRRBWBBRB");
        makePlacement("a000b013c113d302e323f400g420h522i613j701");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
