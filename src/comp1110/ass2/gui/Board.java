package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    /**
     * ~~~~Class Constants~~~~
     * The only constant that should be changed is the BOARD_SCALE_FACTOR
     * This will adjust the size of everything else.
     */
    private static final int SQUARE_SIZE = 100; // square size from image
    private static final double SQUARE_SCALE_FACTOR = 0.70; // factor to scale to full size board

    private static final int WINDOW_WIDTH = 933;
    private static final int WINDOW_HEIGHT = 700;

    private static final int BOARD_PADDING_TOP = 87; // grey part of board on top
    private static final int BOARD_PADDING_LEFT = 41; // grey part of board on left
    private static final int BOARD_PADDING_RIGHT = 43; // grey part of board on right -- not equal to left

    private static final int BOARD_MARGIN_TOP = 100; // margin of board to top of screen
    private static final int BOARD_MARGIN_BOTTOM = 20; // margin of board underneath

    /* Scale factor for Board, will also scale everything else at the same time. */
    private static final double BOARD_SCALE_FACTOR = 0.65;

    private static final double CONTROLS_HEIGHT = 30; // height of controls

    private static final String VERSION = "0.2";

    private PieceTile currentPiece; // current piece selected

    /* Class variables that are set upon initialisation functions */
    private double CHALLENGE_POS_X;
    private double CHALLENGE_POS_Y;
    private double CONTROLS_POS_Y;
    private double BOARD_X;
    private double BOARD_Y;
    private double BOARD_HEIGHT;
    private double BOARD_WIDTH;
    private double SCALED_SQUARE_SIZE;
    private double BOARD_PADDING_LEFT_SCALED;
    private double BOARD_PADDING_TOP_SCALED;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private Pane boardPieces = new Pane();
    private Pane board = new Pane();
    private Pane errors = new Pane();
    private Pane pieceTiles = new Pane();
    private Pane challengeSquares = new Pane();
    private PieceTile[] pieceTilesList = new PieceTile[10];
    private Group debugShapes = new Group();

    private FocusGame game = new FocusGame();

    /* challengeSquare is a 9 character String, with each character corresponding to the
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
        // ChallengeSquare0 = encodingArray[0];
        //When Board Updates (e.g piece is placed) the squares of the challenge square are checked against the
        //stored states encoded above. If all states match should end the game and print victory Screen/message.
        return challenge;
        }
    public String getChallenge() {
       return challenge;
    }

    /* Implementing Challegnes from TestUtility.

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
    // Nicks code begins here

    class PieceTile extends ImageView {

        // home position of pieces
        double xHome;
        double yHome;

        // is the piece currently on the board
        boolean placed;

        // Mouse positions
        double mX;
        double mY;

        // PieceTile attributes
        PieceType pieceType;
        Orientation orientation;
        Location location;
        String placement;

        PieceTile(PieceType p) {

            this.pieceType = p;
            this.orientation = Orientation.Zero;

            InputStream pieceFile = getClass().getResourceAsStream(URI_BASE + p.toString().toLowerCase() + ".png");
            Image pieceImage = new Image(pieceFile);
            double imageHeight = pieceImage.getHeight();
            setImage(pieceImage);
            setPreserveRatio(true);
            setFitHeight(BOARD_SCALE_FACTOR*SQUARE_SCALE_FACTOR*imageHeight);

            // get relevant homeLocation and set to false.
            double[] homeLocation = getHomeLocation(p);
            this.xHome = homeLocation[0];
            this.yHome = homeLocation[1];
            setLayoutX(xHome);
            setLayoutY(yHome);
            this.placed = false;

            setOnMousePressed(e -> {
                if (e.getClickCount() == 2) { // checks if it is a double click (to return it home)
                    snapToHome();
                } else {
                    // debug information
                    System.out.println("===============================");
                    System.out.println("NEW PIECE MOVEMENT");
                    System.out.println("You've pressed on " + pieceType.toString());

                    // set mouse pointer location
                    mX = e.getSceneX();
                    mY = e.getSceneY();

                    // make translucent for dragging
                    setOpacity(0.6);

                    // sets currentPiece to this (allows for rotation to work)
                    currentPiece = this;
                }
            });

            setOnMouseDragged(e -> {
                toFront();
                // change in x and y position of mouse
                double deltaX = e.getSceneX() - mX;
                double deltaY = e.getSceneY() - mY;
                // changes layout
                setLayoutX(getLayoutX() + deltaX);
                setLayoutY(getLayoutY() + deltaY);
                // gets new mouse location
                mX = e.getSceneX();
                mY = e.getSceneY();
                e.consume();
            });
            // Dragging ended
            setOnMouseReleased(e -> {
                // tries to place it to the board
                snapToBoard();
                // changes the currentPiece back to null
                currentPiece = null;
                // make solid again
                setOpacity(1.0);
            });
        }

        /**
         * Attempts to place the piece given the x and y locations of it.
         *
         */

        private void snapToBoard() {
            //String placement;

            // offsets account for the orientation
            double[] offsets = Viewer.getOrientationOffsets(pieceType,orientation);
            double aX = SCALED_SQUARE_SIZE*offsets[0]*-1+getLayoutX();
            double aY = SCALED_SQUARE_SIZE*offsets[1]*-1+getLayoutY();

            //debugAddCircle(aX,aY); -- uncomment if you'd like to see where the x/y location is
            System.out.println("Dropped coordinates: " + aX + ", " + aY);

            if (!xyOnBoard(aX,aY)) {
                System.out.println("Not on board");
                snapToHome();
            } else {
                location = getLocationFromSceneXY(aX,aY); // approximate location
                // builds placement string
                placement = pieceType.toString().toLowerCase() + location.getX() + location.getY() + orientation.toInt();
                if (game.checkPieceToBoard(placement)) { // if valid, piece can be placed
                    System.out.println("Placement " + placement + " is valid.");
                    placePiece(new Piece(placement));
                    placed = true;
                    makePlacement(placement);
                } else { // otherwise return home
                    System.out.println("Placement " + placement + " is NOT valid.");
                    snapToHome();
                }
            }
        }

        /**
         * Places the piece back to its home state, and resets orientation
         */
        private void snapToHome() {
            setLayoutX(xHome);
            setLayoutY(yHome);
            orientation = Orientation.Zero;
            setRotate(0);
            if (placed) {
                System.out.println("flag");
                game.undoOperation(game.getBoardPlacementString(),placement);
            }
            System.out.println(game.getBoardPlacementString());;
            placed = false;
        }

        /**
         * Places piece onto the board
         * @param piece The Piece object built from a valid placement string
         */
        private void placePiece(Piece piece) {
            this.placed = true;
            System.out.println(this.orientation);
            double[] offsets = Viewer.getOrientationOffsets(piece.getPieceType(),piece.getOrientation());
            setLayoutX(BOARD_X + BOARD_PADDING_LEFT_SCALED + SCALED_SQUARE_SIZE*(piece.getLocation().getX()+offsets[0]));
            setLayoutY(BOARD_Y + BOARD_PADDING_TOP_SCALED+ SCALED_SQUARE_SIZE*(piece.getLocation().getY()+offsets[1]));
        }

        /**
         * Rotate piece and switch the orientation
         */
        private void rotate() {
            if (orientation == Orientation.Zero) {
                orientation = Orientation.One;
            } else if (orientation == Orientation.One) {
                orientation = Orientation.Two;
            } else if (orientation == Orientation.Two) {
                orientation = Orientation.Three;
            } else {
                orientation = Orientation.Zero;
            }
            setRotate(orientation.toInt()*90);
        }

    }

    /**
     * Gets ImageViews of individual colour squares to show the Challenge
     * @param c Char representing colour of square (R W B G)
     * @return ImageView of individual square
     */
    private ImageView getSquareImageFromFile(Character c) {
        InputStream squareFile = getClass().getResourceAsStream(URI_BASE + "sq-" + c.toString().toLowerCase() + ".png");
        Image squareImage = new Image(squareFile);
        ImageView squareImageView = new ImageView(squareImage);
        squareImageView.setFitHeight(SCALED_SQUARE_SIZE);
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
        double approxX = (mX-BOARD_X-BOARD_PADDING_LEFT_SCALED)/SCALED_SQUARE_SIZE;
        double approxY = (mY-BOARD_Y-BOARD_PADDING_TOP_SCALED)/SCALED_SQUARE_SIZE;
        System.out.println("Approx location: " + Math.round(approxX) + ", " + Math.round(approxY));
        return new Location((int)Math.round(approxX),(int)Math.round(approxY));
    }

    /**
     * DEBUG ONLY
     * Will place a circle of radius 10 at given x and y locations
     * @param x -- X location of circle
     * @param y -- Y location of circle
     */
    private void debugAddCircle(double x, double y) {
        Circle circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(10);
        circle.setFill(Color.BLACK);
        debugShapes.getChildren().add(circle);
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
     * Returns true if x/y location is on the board on screen
     * @param x x location in window
     * @param y y location in window
     * @return true if it is on the board, false if not
     */
    public boolean xyOnBoard(double x, double y) {
        double errorMargin = 20;
        return x >= BOARD_X+BOARD_PADDING_LEFT_SCALED-errorMargin && x <= (BOARD_X+BOARD_WIDTH)
                && y >= BOARD_Y+BOARD_PADDING_TOP_SCALED-errorMargin && y <= (BOARD_Y+BOARD_HEIGHT);
    }

    /**
     * Make a piece placement on the board logic
     * @param placement valid placement string
     */
    private void makePlacement(String placement) {
        System.out.println("Making placement " + placement);
        game.addPieceToBoard(placement);
        //checkCompletion(); // -- always returning true until method implemented in FocusGame
    }

    /**
     * Makes game controls
     */
    private void makeControls() {
        ArrayList<Node> controlNodes = new ArrayList<Node>();

        // Board Title
        Text title = new Text("IQ Focus Puzzle");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, FontPosture.ITALIC, 40));
        title.setY(45);
        title.setX(WINDOW_WIDTH/2-title.getLayoutBounds().getWidth()/2);
        title.setFill(Color.BLACK);
        controlNodes.add(title);

        //Difficulty slider
        Text difficultyText = new Text("Difficulty");
        difficultyText.setLayoutY(CHALLENGE_POS_Y+SCALED_SQUARE_SIZE*3+20);
        difficultyText.setFont(new Font("Tahoma", 15));
        difficultyText.setLayoutX(CHALLENGE_POS_X+(SCALED_SQUARE_SIZE*3-difficultyText.getLayoutBounds().getWidth())/2);
        Slider difficulty = new Slider();
        difficulty.setMin(1);
        difficulty.setMax(3);
        difficulty.setValue(1);
        difficulty.setLayoutX(CHALLENGE_POS_X);
        difficulty.setLayoutY(CHALLENGE_POS_Y+SCALED_SQUARE_SIZE*3+30);
        difficulty.setShowTickMarks(true);
        difficulty.setShowTickLabels(true);
        difficulty.setMajorTickUnit(1);
        difficulty.setMinorTickCount(0);
        difficulty.setSnapToTicks(true);
        controlNodes.add(difficulty);
        controlNodes.add(difficultyText);

        // Buttons
        HBox controlBox = new HBox();
        controlBox.setSpacing(40);
        controlBox.setMinWidth(WINDOW_WIDTH);
        controlBox.setLayoutY(CONTROLS_POS_Y);
        controlBox.setAlignment(Pos.CENTER);

        Button newGame = new Button("New Game");
        newGame.setOnAction(e -> newGame());

        Button resetBoard = new Button("Reset Board");
        resetBoard.setOnAction(e -> resetBoard());

        Button newChallenge = new Button("Random Challenge");
        newChallenge.setOnAction(e -> {
            System.out.println("New Challenge of difficulty " + difficulty.getValue());
        });

        controlBox.getChildren().addAll(newGame,resetBoard,newChallenge);

        for (Node n : controlBox.getChildren()) {
            if (n instanceof Button) {
                ((Button) n).setPrefHeight(CONTROLS_HEIGHT);
            }
        }

        controlNodes.add(controlBox);


        // Version number
        Text version = new Text("Version " + VERSION);
        version.setFont(Font.font("Tahoma", 10));
        version.setX(5);
        version.setY(WINDOW_HEIGHT-10);
        version.setFill(Color.GRAY);
        controlNodes.add(version);

        controls.getChildren().addAll(controlNodes);
    }

    /**
     * Loads board image and displays it
     * Also sets instance fields for Board position and dimensions
     * @throws IOException -- for Image
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
     * Loads PieceTiles onto screen
     */
    private void makePieceTiles() {
        int i = 0;
        for (PieceType p : PieceType.values()) {
            pieceTilesList[i] = new PieceTile(p);
            i++;
        }
        pieceTiles.getChildren().addAll(pieceTilesList);
    }

    /**
     * Displays 9 square challenge on screen
     * @param challengeString 9 character challenge string
     */
    private void makeChallenge(String challengeString) {
        challengeSquares.getChildren().clear();
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
        challengeTitle.setX(CHALLENGE_POS_X+(SCALED_SQUARE_SIZE*3-challengeTitle.getLayoutBounds().getWidth())/2);
        challengeTitle.setY(CHALLENGE_POS_Y-10);
        challengeSquares.getChildren().add(challengeTitle);
    }

    /**
     * Prints debug statements on start up
     */
    private void debug() {
        System.out.println("DEBUG");
        System.out.println("===========================================");
        System.out.println("Calculated Values");
        System.out.println("BOARD_X=" + BOARD_X);
        System.out.println("BOARD_Y=" + BOARD_Y);
        System.out.println("BOARD_HEIGHT=" + BOARD_HEIGHT);
        System.out.println("BOARD_WIDTH=" + BOARD_WIDTH);
        System.out.println("SCALED_SQUARE_SIZE=" + SCALED_SQUARE_SIZE);
        System.out.println("BOARD_PADDING_LEFT_SCALED=" + BOARD_PADDING_LEFT_SCALED);
        System.out.println("BOARD_PADDING_TOP_SCALED=" + BOARD_PADDING_TOP_SCALED);

    }

    /**
     * Initialises class variables for positioning pieces.
     */
    public void initVariables() {
        SCALED_SQUARE_SIZE = BOARD_SCALE_FACTOR*SQUARE_SCALE_FACTOR*SQUARE_SIZE;
        CHALLENGE_POS_X = (WINDOW_WIDTH-BOARD_WIDTH)/4-1.5*SCALED_SQUARE_SIZE;
        CHALLENGE_POS_Y = BOARD_HEIGHT/2-1.5*SCALED_SQUARE_SIZE+BOARD_MARGIN_TOP;
        CONTROLS_POS_Y = BOARD_Y - CONTROLS_HEIGHT - 10;
        BOARD_PADDING_LEFT_SCALED = BOARD_PADDING_LEFT*BOARD_SCALE_FACTOR;
        BOARD_PADDING_TOP_SCALED = BOARD_PADDING_TOP*BOARD_SCALE_FACTOR;
    }

    /**
     * Sets key events for scene
     * Z - used to rotate pieces
     * @param scene Scene to set events on
     */
    private void setKeyEvents(Scene scene) {
        scene.setOnKeyPressed(e -> {
            KeyCode key = e.getCode();
            if (key == KeyCode.Z) {
                if (currentPiece != null) {
                    currentPiece.rotate();
                }
                e.consume();
            }
        });
    }

    /**
     * TODO
     * Check if the challenge has been completed
     */
    private void checkCompletion() {
        //if game.checkCompletion() -> show congratulating message
        board.setOpacity(0.5);
        pieceTiles.setOpacity(0.5);
        Alert completed = new Alert(Alert.AlertType.NONE,"Congratulations! You completed this challenge! Would you like to start a new game?", ButtonType.NEXT,ButtonType.NO);
        completed.setTitle("Challenge complete");
        completed.showAndWait();
    }

    /**
     * TODO
     * Start a new game
     */
    private void newGame() {
        //game.newGame
        //makeChallenge(game.getChallenge);
        resetBoard();
        makeChallenge("RRRBWBBRB");
    }

    /**
     * Reset board state
     */
    private void resetBoard() {
        game.resetBoard();
        for (PieceTile p : pieceTilesList) {
            p.snapToHome();
        }
        debugShapes.getChildren().clear();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("IQ Focus Puzzle");
        primaryStage.setResizable(false);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        root.getChildren().addAll(
                challengeSquares,
                board,
                boardPieces,
                pieceTiles,
                errors,
                controls,
                debugShapes
        );

        setKeyEvents(scene);
        makeBoard();
        initVariables();

        makePieceTiles();
        makeControls();
        debug(); // -- comment out in production

        newGame();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
