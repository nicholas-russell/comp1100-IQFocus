package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Board extends Application {
    //Class constants
    static final double SQUARE_SIZE = 100; // square size from image
    static final double SQUARE_SCALE_FACTOR = 0.70; // factor to scale to full size board

    static final int WINDOW_WIDTH = 933;
    static final int WINDOW_HEIGHT = 650;

    static final int BOARD_PADDING_TOP = 87; // grey part of board on top
    static final int BOARD_PADDING_LEFT = 41; // grey part of board on left
    static final int BOARD_PADDING_RIGHT = 43;

    static final double BOARD_MARGIN_TOP = 50; // margin of board to top of screen
    static final int BOARD_MARGIN_BOTTOM = 20; // margin of board underneath
    static final double BOARD_SCALE_FACTOR = 0.65; // scale factor NOTE: will scale everything else.

    // Class variables that are set upon initialisation functions
    double CHALLENGE_POS_X;
    double CHALLENGE_POS_Y;
    double BOARD_X;
    double BOARD_Y;
    double BOARD_HEIGHT;
    double BOARD_WIDTH;
    double SCALED_SQUARE_SIZE;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private Pane boardPieces = new Pane();
    private Pane board = new Pane();
    private Pane errors = new Pane();
    private Pane controlPieces = new Pane();
    private Pane challengeSquares = new Pane();
    private ImageView[] controlImages = new ImageView[10];

    FocusGame game = new FocusGame();

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


    class PieceTile extends ImageView {

        double xHome;
        double yHome;
        boolean placed;
        double mX;
        double mY;

        PieceType pieceType;
        Orientation orientation;
        Location location;

        PieceTile(PieceType p) {

            this.pieceType = p;
            this.orientation = Orientation.Zero;

            InputStream pieceFile = getClass().getResourceAsStream(URI_BASE + p.toString().toLowerCase() + ".png");
            Image pieceImage = new Image(pieceFile);
            double imageHeight = pieceImage.getHeight();
            setImage(pieceImage);
            setFitHeight(BOARD_SCALE_FACTOR*SQUARE_SCALE_FACTOR*imageHeight);
            setPreserveRatio(true);

            double[] homeLocation = getHomeLocation(p);
            this.xHome = homeLocation[0];
            this.yHome = homeLocation[1];

            this.placed = false;

            setLayoutX(xHome);
            setLayoutY(yHome);

            // Dragging beginning
            setOnScroll(e -> {
                System.out.println("You're rotating piece " + pieceType.toString());
                rotate();
                e.consume();
            });
            setOnMousePressed(e -> {
                if (e.getClickCount() == 2) {
                    snapToHome();
                } else {
                    System.out.println("===============================");
                    System.out.println("NEW PIECE MOVEMENT");
                    System.out.println("You've pressed on " + pieceType.toString());
                    mX = e.getSceneX();
                    mY = e.getSceneY();
                }
            });
            // Dragging
            setOnMouseDragged(e -> {
                toFront();
                double deltaX = e.getSceneX() - mX;
                double deltaY = e.getSceneY() - mY;
                setLayoutX(getLayoutX() + deltaX);
                setLayoutY(getLayoutY() + deltaY);
                mX = e.getSceneX();
                mY = e.getSceneY();
                e.consume();
            });
            // Dragging ended
            setOnMouseReleased(e -> {
                System.out.println("You've let go of " + pieceType.toString());
                snapToBoard();
            });
        }

        private void snapToBoard() {
            String placement;
            System.out.println("Dropped coordinates: " + getLayoutX() + ", " + getLayoutY());
            if (!xyOnBoard(getLayoutX(),getLayoutY())) {
                snapToHome();
            } else {
                location = getLocationFromSceneXY(getLayoutX(),getLayoutY());
                placement = pieceType.toString().toLowerCase() + location.getX() + location.getY() + orientation.toInt();
                if (FocusGame.isPlacementStringValid(placement)) {
                    System.out.println("Placement " + placement + " is valid.");
                    setLayoutX(BOARD_X + BOARD_PADDING_LEFT*BOARD_SCALE_FACTOR + location.getX()*SCALED_SQUARE_SIZE);
                    setLayoutY(BOARD_Y + BOARD_PADDING_TOP*BOARD_SCALE_FACTOR + location.getY()*SCALED_SQUARE_SIZE);
                    this.placed = true;
                    makePlacement(placement);
                } else {
                    System.out.println("Placement " + placement + " is NOT valid.");
                    snapToHome();
                }
            }
        }

        private void snapToHome() {
            setLayoutX(xHome);
            setLayoutY(yHome);
            orientation = Orientation.Zero;
            setRotate(0);
        }

        private void rotate() {
            setRotate(orientation.toInt()*90);
            if (orientation == Orientation.Zero) {
                orientation = Orientation.One;
            } else if (orientation == Orientation.One) {
                orientation = Orientation.Two;
            } else if (orientation == Orientation.Two) {
                orientation = Orientation.Three;
            } else {
                orientation = Orientation.Zero;
            }

        }

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
     * Gets board location given location of top left of image view
     * @param mX screen x value of top left of a piece's ImageView
     * @param mY screen y value of top left of a piece's ImageView
     * @return Instance of Location with valid x,y co-ords, or FALSE/NULL if not valid
     */
    public Location getLocationFromSceneXY(double mX, double mY) {
        double approxX = (mX-BOARD_X-BOARD_PADDING_LEFT*BOARD_SCALE_FACTOR)/SCALED_SQUARE_SIZE;
        double approxY = (mY-BOARD_Y-BOARD_PADDING_TOP*BOARD_SCALE_FACTOR)/SCALED_SQUARE_SIZE;
        System.out.println("Approx location: " + Math.round(approxX) + ", " + Math.round(approxY));
        return new Location((int)Math.round(approxX),(int)Math.round(approxY));
    }

    /**
     * Returns true if x/y location is on the board
     * @param x x location in window
     * @param y y location in window
     * @return
     */
    public boolean xyOnBoard(double x, double y) {
        double errorMargin = 20;
        return x >= BOARD_X+BOARD_PADDING_LEFT*BOARD_SCALE_FACTOR-errorMargin && x <= (BOARD_X+BOARD_WIDTH)
                && y >= BOARD_Y+BOARD_PADDING_TOP*BOARD_SCALE_FACTOR-errorMargin && y <= (BOARD_Y+BOARD_HEIGHT);
    }

    /**
     * TODO: work in
     * Make a piece placement on the board logic
     * @param placement
     */
    private void makePlacement(String placement) {
        System.out.println("Making placement " + placement);
        game.addPieceToBoard(placement);
    }

    /**
     * TODO
     * Makes game controls
     */
    private void makeControls() {
        ArrayList<Node> controlNodes = new ArrayList<Node>();

        // Board Title
        Text title = new Text("Focus Game");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, FontPosture.ITALIC, 40));
        title.setY(BOARD_Y-10);
        title.setX(WINDOW_WIDTH/2-title.getLayoutBounds().getWidth()/2);
        title.setFill(Color.BLACK);
        controlNodes.add(title);

        // New game button

        // Reset board

        //Difficulty slider

        controls.getChildren().addAll(controlNodes);
    }

    /**
     * Loads board image and displays it
     * @throws IOException
     */
    public void makeBoard() throws IOException {
        Image boardImage = new Image(new FileInputStream(URI_BASE + "board.png"));
        ImageView boardIv = new ImageView(boardImage);
        boardIv.setPreserveRatio(true);
        boardIv.setFitHeight(boardImage.getHeight()*BOARD_SCALE_FACTOR);
        boardIv.setX(Math.round((WINDOW_WIDTH - boardImage.getWidth()*BOARD_SCALE_FACTOR)/2));
        boardIv.setY(BOARD_MARGIN_TOP);
        this.BOARD_X = boardIv.getX();
        this.BOARD_Y = boardIv.getY();
        this.BOARD_HEIGHT = boardImage.getHeight()*BOARD_SCALE_FACTOR;
        this.BOARD_WIDTH = boardImage.getWidth()*BOARD_SCALE_FACTOR;
        board.getChildren().addAll(boardIv);
    }

    /**
     * Displays control pieces on screen
     */
    private void makeControlPieces() {
        int i = 0;
        for (PieceType p : PieceType.values()) {
            controlImages[i] = new PieceTile(p);
            i++;
        }

        controlPieces.getChildren().addAll(controlImages);
    }

    /**
     * Gets 'home' co-ordinates for Piece
     * @param p the PieceType
     * @return two element array with x and y co-ordinate
     */
    private double[] getHomeLocation(PieceType p) {
        double yOff = BOARD_Y + BOARD_HEIGHT + BOARD_MARGIN_BOTTOM;
        double xPadding = 10.0;
        double yPadding = 20.0;
        double[] c = new double[2];
        switch (p) {
            case A:
                c[0] = xPadding;
                c[1] = yOff;
                break;
            case B:
                c[0] = xPadding + SCALED_SQUARE_SIZE*3;
                c[1] = yOff;
                break;
            case C:
                c[0] = xPadding + SCALED_SQUARE_SIZE*7;
                c[1] = yOff;
                break;
            case D:
                c[0] = xPadding + SCALED_SQUARE_SIZE*11;
                c[1] = yOff;
                break;
            case E:
                c[0] = xPadding + SCALED_SQUARE_SIZE*15;
                c[1] = yOff;
                break;
            case F:
                c[0] = xPadding;
                c[1] = yOff+SCALED_SQUARE_SIZE*2+yPadding;
                break;
            case G:
                c[0] = xPadding + SCALED_SQUARE_SIZE*4;
                c[1] = yOff+SCALED_SQUARE_SIZE*2+yPadding;
                break;
            case H:
                c[0] = xPadding + SCALED_SQUARE_SIZE*8;
                c[1] = yOff+SCALED_SQUARE_SIZE*2+yPadding;
                break;
            case I:
                c[0] = xPadding + SCALED_SQUARE_SIZE*12;
                c[1] = yOff+SCALED_SQUARE_SIZE*2+yPadding;
                break;
            case J:
                c[0] = xPadding + SCALED_SQUARE_SIZE*15;
                c[1] = yOff+SCALED_SQUARE_SIZE*2+yPadding;
                break;
            default:
                break;
        }
        return c;
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
    public void initVariables() {
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
                challengeSquares,
                board,
                boardPieces,
                controlPieces,
                errors
        );

        makeBoard();
        initVariables();
        makeControls();
        debug();
        makeControlPieces();
        makeChallenge("RRRBWBBRB");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
