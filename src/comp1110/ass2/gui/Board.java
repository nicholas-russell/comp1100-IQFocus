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
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * This program implements the controller and view for
 * the IQ-Focus puzzle game.
 *
 * The game is based directly on Smart Games' IQ-Focus game
 * (https://www.smartgames.eu/uk/one-player-games/iq-focus)
 *
 * @author Nicholas Russell, Matt Tein
 * @version 0.2-d2g
 * @since 1/10/2019
 */

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
    private static final int HELP_WINDOW_WIDTH = 680;
    private static final int HELP_WINDOW_HEIGHT = 400;


    private static final int BOARD_PADDING_TOP = 87; // grey part of board on top
    private static final int BOARD_PADDING_LEFT = 41; // grey part of board on left

    private static final int BOARD_MARGIN_TOP = 100; // margin of board to top of screen
    private static final int BOARD_MARGIN_BOTTOM = 20; // margin of board underneath

    /* Scale factor for Board, will also scale everything else at the same time. */
    private static final double BOARD_SCALE_FACTOR = 0.65;

    private static final double CONTROLS_HEIGHT = 30; // height of controls
    private boolean SHOW_CHALLENGE = true; // show challenge on board

    private static final double CHALLENGE_PIECE_OPACITY = 0.3;

    private static final String VERSION = "0.3-d2g";

    private static final Boolean HINTS_LIMITED = true;
    private static final int HINTS_LIMIT = 3;
    private int HINTS_COUNTER;
    private Text hintCounter = new Text();

    private PieceTile currentPiece; // current piece selected

    /* Class variables that are set upon initialisation functions */
    private double CHALLENGE_POS_X;
    private double CHALLENGE_POS_Y;
    private double CONTROLS_POS_Y;
    private double BOARD_X;
    private double BOARD_Y;
    private double BOARD_ABS_X;
    private double BOARD_ABS_Y;
    private double BOARD_HEIGHT;
    private double BOARD_WIDTH;
    private double SCALED_SQUARE_SIZE;

    private static final String URI_BASE = "assets/";
    private static final Image ICON_IMAGE = new Image(Board.class.getResourceAsStream(URI_BASE + "icon.png"));
    private static final FileChooser.ExtensionFilter EXTENSION_FILTER = new FileChooser.ExtensionFilter("IQ Focus Save (*.iqs)", "*.iqs");

    private final Group root = new Group();
    private final Group controls = new Group();
    private Pane boardPieces = new Pane();
    private Pane board = new Pane();
    private Pane pieceTiles = new Pane();
    private Pane challengeSquares = new Pane();
    private Pane challengeSquaresBoard = new Pane();
    private PieceTile[] pieceTilesList = new PieceTile[10];
    private Group debugShapes = new Group();

    private Stage helpStage = new Stage();
    private Group helpRoot = new Group();

    private FocusGame game = new FocusGame();

    /**
     * These methods implement hints and challenge generation for the FocusGame.
     */

    // FIXME Task 11: Generate interesting challenges (each challenge may have just one solution)
    public String challengeGenerator (int difficulty) {
        String[] pack = new String[9];
        String challenge = "";
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
        Random random = new Random();
        int g = random.nextInt(4);
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

    /**
     * This is the primary class that implements piece functionality
     * in the game.
     *
     * @author Nicholas Russell
     */

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
                    if (placed) {
                        game.undoOperation(game.getBoardPlacementString(),placement);
                    }

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
         */

        private void snapToBoard() {
            // offsets account for the orientation
            double[] offsets = Viewer.getOrientationOffsets(pieceType,orientation);
            double aX = SCALED_SQUARE_SIZE*offsets[0]*-1+getLayoutX();
            double aY = SCALED_SQUARE_SIZE*offsets[1]*-1+getLayoutY();

            if (!xyOnBoard(aX,aY)) {
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
                game.undoOperation(game.getBoardPlacementString(),placement);
                placement = null;
            }
            placed = false;
        }

        /**
         * Places piece onto the board
         * @param piece The Piece object built from a valid placement string
         */
        private void placePiece(Piece piece) {
            this.placed = true;
            double[] offsets = Viewer.getOrientationOffsets(piece.getPieceType(),piece.getOrientation());
            setLayoutX(BOARD_ABS_X + SCALED_SQUARE_SIZE*(piece.getLocation().getX()+offsets[0]));
            setLayoutY(BOARD_ABS_Y + SCALED_SQUARE_SIZE*(piece.getLocation().getY()+offsets[1]));
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
            setRotation(orientation);
        }

        private void setRotation(Orientation orientation) {
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
        double approxX = (mX-BOARD_ABS_X)/SCALED_SQUARE_SIZE;
        double approxY = (mY-BOARD_ABS_Y)/SCALED_SQUARE_SIZE;
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

    private void showHint() {
        if (HINTS_LIMITED && HINTS_COUNTER >= HINTS_LIMIT) {
            Alert hintAlert = new Alert(Alert.AlertType.NONE,"No more hints!",ButtonType.OK);
            hintAlert.setTitle("No more hints!!");
            hintAlert.showAndWait();
        } else {
            if (HINTS_LIMITED) {
                HINTS_COUNTER++;
                hintCounter.setText("Hints remaining: " + (HINTS_LIMIT-HINTS_COUNTER));
            }
            String hintPlacement = game.getNextHint();
            Piece hintPiece = new Piece(hintPlacement);
            for (PieceTile p : pieceTilesList) {
                if (p.pieceType == hintPiece.getPieceType()) {
                    if (p.placed) {
                        System.out.println("Piece " + p.pieceType.toString() + " is already placed at " + p.placement);
                        game.undoOperation(game.getBoardPlacementString(), p.placement);
                    }
                    p.placement = hintPlacement;
                    p.placePiece(hintPiece);
                    p.placed = true;
                    p.setRotation(hintPiece.getOrientation());
                    makePlacement(hintPlacement);
                }
            }
        }
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
        return x >= BOARD_ABS_X-errorMargin && x <= (BOARD_X+BOARD_WIDTH)
                && y >= BOARD_ABS_Y-errorMargin && y <= (BOARD_Y+BOARD_HEIGHT);
    }

    /**
     * Make a piece placement on the board logic
     * @param placement valid placement string
     */
    private void makePlacement(String placement) {
        game.addPieceToBoard(placement);
        if (allPiecesPlaced()) {
            checkCompletion();
        }
    }

    private boolean allPiecesPlaced() {
        boolean flag = true;
        for (PieceTile p : pieceTilesList) {
            flag = p.placed;
        }
        System.out.println("ALL PIECES PLACED:" + flag);
        return flag;
    }

    /**
     * Makes game controls
     */
    private void makeControls(Stage stage) throws IOException {
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
        //controlNodes.add(difficulty);
        //controlNodes.add(difficultyText);

        // Buttons
        HBox controlBox = new HBox();
        controlBox.setSpacing(20);
        controlBox.setMinWidth(WINDOW_WIDTH);
        controlBox.setLayoutY(CONTROLS_POS_Y);
        controlBox.setAlignment(Pos.CENTER);

        Button newGame = new Button("New Game");
        newGame.setOnAction(e -> {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to start a new game?",ButtonType.YES,ButtonType.NO);
            confirmation.setTitle("New Game");
            confirmation.setHeaderText("New Game?");
            confirmation.showAndWait();
            if (confirmation.getResult() == ButtonType.YES) {
                newGame();
            }
        });

        Button resetBoard = new Button();
        resetBoard.setMnemonicParsing(true);
        resetBoard.setText("_Reset Board");
        resetBoard.setOnAction(e -> {
            resetBoardAction();
        });

        /**
        Button newChallenge = new Button("Random Challenge");
        newChallenge.setOnAction(e -> {
            System.out.println("New Challenge of difficulty " + difficulty.getValue());
        });
        **/

        Button hint = new Button();
        hint.setMnemonicParsing(true);
        hint.setText("_Hint");
        hint.setOnAction(e -> {
            showHint();
        });

        Button help = new Button("Help");
        help.setOnAction(e -> showHelp());

        Button loadGame = new Button("Load Game");
        loadGame.setOnAction(e -> {
            try {
                loadGame(stage);
            } catch (IOException error) {
                System.out.println(error);
            }
        });

        Button saveGame = new Button("Save Game");
        saveGame.setOnAction(e -> {
            try {
                saveGame(stage);
            } catch (IOException error) {
                System.out.println(error);
            }
        });

        Button toggleChallenge = new Button();
        toggleChallenge.setMnemonicParsing(true);
        toggleChallenge.setText("Hide _Challenge");
        toggleChallenge.setMinWidth(110);
        toggleChallenge.setOnAction(e -> {
            if (SHOW_CHALLENGE) {
                SHOW_CHALLENGE = false;
                toggleChallenge.setText("Show _Challenge");
                challengeSquaresBoard.setOpacity(0);
            } else {
                SHOW_CHALLENGE = true;
                toggleChallenge.setText("Hide _Challenge");
                challengeSquaresBoard.setOpacity(CHALLENGE_PIECE_OPACITY);
            }
        });

        controlBox.getChildren().addAll(newGame,resetBoard,toggleChallenge,hint,saveGame,loadGame,help);

        for (Node n : controlBox.getChildren()) {
            if (n instanceof Button) {
                ((Button) n).setPrefHeight(CONTROLS_HEIGHT);
            }
        }

        controlNodes.add(controlBox);

        if (HINTS_LIMITED) {
            hintCounter.setText("Hints remaining: " + (HINTS_LIMIT-HINTS_COUNTER));
            hintCounter.setFont(new Font("Tahoma", 15));
            hintCounter.setX(BOARD_X+(BOARD_WIDTH-hintCounter.getLayoutBounds().getWidth())/2);
            hintCounter.setY(BOARD_Y+BOARD_HEIGHT+17);
        }
        controlNodes.add(hintCounter);

        Text info = new Text();
        info.setWrappingWidth(SCALED_SQUARE_SIZE*3);
        info.setText("Place all the pieces on the board that forms the 3x3 challenge shown!" +
                "\n\nPress Z to rotate pieces");
        info.setX(WINDOW_WIDTH-CHALLENGE_POS_X-SCALED_SQUARE_SIZE*3);
        info.setFont(new Font("Tahoma", 15));
        info.setY(CHALLENGE_POS_Y);
        info.setTextAlignment(TextAlignment.CENTER);
        controlNodes.add(info);

        // Version number
        Text version = new Text("Version " + VERSION);
        version.setFont(Font.font("Tahoma", 10));
        version.setX(5);
        version.setY(WINDOW_HEIGHT-10);
        version.setFill(Color.GRAY);
        controlNodes.add(version);

        controls.getChildren().addAll(controlNodes);
    }

    private void loadGame(Stage stage) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Game");
        fileChooser.getExtensionFilters().add(EXTENSION_FILTER);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String saveString = br.readLine();
            if (FocusGame.isSaveStringValid(saveString)) {
                String[] saveArray = saveString.split(",");
                resetBoard();
                game.nextChallenge(Integer.parseInt(saveArray[0]));
                makeChallenge(game.getChallenge());
                game.addPiecesToBoard(saveArray[1]);
                makePlacements(saveArray[1]);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
                alert.setHeaderText("Invalid Save File");
                alert.setTitle("Error - Invalid Save");
                alert.showAndWait();
            }
        }
    }

    private void saveGame(Stage stage) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Game");
        fileChooser.getExtensionFilters().add(EXTENSION_FILTER);
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
        Date date = new Date();
        fileChooser.setInitialFileName("IQ_FOCUS_SAVE_" + dateFormat.format(date));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try (PrintWriter out = new PrintWriter(file.getAbsolutePath())) {
                out.println(game.getSaveString());
            }
        }
    }

    private void resetBoardAction() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to reset the board?",ButtonType.YES,ButtonType.NO);
        confirmation.setTitle("Reset Board");
        confirmation.setHeaderText("Reset Board?");
        confirmation.showAndWait();
        if (confirmation.getResult() == ButtonType.YES) {
            resetBoard();
        }
    }

    private void makePlacements(String placementString) {
        for (String p : FocusGame.splitPlacementString(placementString)) {
            Piece piece = new Piece(p);
            for (PieceTile pT : pieceTilesList) {
                if (pT.pieceType == piece.getPieceType()) {
                    pT.placement = p;
                    pT.placePiece(piece);
                    pT.placed = true;
                    pT.orientation = piece.getOrientation();
                    pT.setRotation(piece.getOrientation());
                }
            }
        }
    }

    private void makeHelp(Stage stage) {
        Scene scene = new Scene(helpRoot,HELP_WINDOW_WIDTH,HELP_WINDOW_HEIGHT);
        ImageView banner = new ImageView(new Image(Board.class.getResourceAsStream(URI_BASE + "help-banner.jpg")));
        banner.setPreserveRatio(true);
        banner.setFitHeight(HELP_WINDOW_HEIGHT);

        double xOff = HELP_WINDOW_WIDTH/2+10;

        Text helpTitle = new Text("IQ FOCUS - Help");
        helpTitle.setFont(new Font("Tahoma", 20));
        helpTitle.setX(xOff);
        helpTitle.setY(20);

        Text helpBody = new Text();
        helpBody.setFont(new Font("Tahoma", 12));
        helpBody.setWrappingWidth(HELP_WINDOW_WIDTH/2-20);
        helpBody.setX(xOff);
        helpBody.setY(40);
        helpBody.setText("To complete the challenge, you must place all the pieces on the board and form the challenge given in the centre squares." +
                "\n\nControls" +
                "\nZ - Rotate piece" +
                "\nALT+H - Hint" +
                "\nALT+R - Reset Board" +
                "\nALT+C - Toggle challenge on board");

        Text aboutTitle = new Text();
        aboutTitle.setText("About");
        aboutTitle.setFont(new Font("Tahoma", 14));
        aboutTitle.setX(xOff);
        aboutTitle.setY(40+helpBody.getLayoutBounds().getHeight()+10);

        Text aboutBody = new Text();
        aboutBody.setText("The game is based directly on Smart Games' IQ-Focus game available at: \nhttps://www.smartgames.eu/uk/one-player-games/iq-focus" +
                "\n\nThis application was coded by Nicholas Russell, Yuhui Wang and Matthew Tein for a COMP1110 (ANU) assignment");
        aboutBody.setX(xOff);
        aboutBody.setY(40+helpBody.getLayoutBounds().getHeight()+10+20);
        aboutBody.setFont(new Font("Tahoma", 12));
        aboutBody.setWrappingWidth(HELP_WINDOW_WIDTH/2-20);

        helpRoot.getChildren().addAll(banner,helpTitle,helpBody, aboutTitle, aboutBody);

        stage.setTitle("IQ FOCUS - Help");
        stage.getIcons().add(ICON_IMAGE);
        stage.setScene(scene);
    }

    private void showHelp() {
        helpStage.show();
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
        challengeSquaresBoard.getChildren().clear();
        char[] challengeChar = challengeString.toCharArray();
        int row = 0;
        int col = 0;
        for (Character c : challengeChar) {
            ImageView chSq = getSquareImageFromFile(c);
            ImageView chSqBd = getSquareImageFromFile(c);
            if (col > 2) {
                col = 0;
                row++;
            }
            chSq.setX(CHALLENGE_POS_X+col*SCALED_SQUARE_SIZE);
            chSq.setY(CHALLENGE_POS_Y+row*SCALED_SQUARE_SIZE);
            challengeSquares.getChildren().add(chSq);

            chSqBd.setX(BOARD_ABS_X+SCALED_SQUARE_SIZE*3+col*SCALED_SQUARE_SIZE);
            chSqBd.setY(BOARD_ABS_Y+SCALED_SQUARE_SIZE*1+row*SCALED_SQUARE_SIZE);
            challengeSquaresBoard.getChildren().add(chSqBd);
            col++;
        }
        challengeSquaresBoard.setOpacity(CHALLENGE_PIECE_OPACITY);
        Text challengeTitle = new Text("Challenge #" + game.getChallengeNumber());
        challengeTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        challengeTitle.setFill(Color.BLACK);
        challengeTitle.setX(CHALLENGE_POS_X+(SCALED_SQUARE_SIZE*3-challengeTitle.getLayoutBounds().getWidth())/2);
        challengeTitle.setY(CHALLENGE_POS_Y-10);
        challengeSquares.getChildren().add(challengeTitle);
    }

    /**
     * Initialises class variables for positioning pieces.
     */
    public void initVariables() {
        SCALED_SQUARE_SIZE = BOARD_SCALE_FACTOR*SQUARE_SCALE_FACTOR*SQUARE_SIZE;
        CHALLENGE_POS_X = (WINDOW_WIDTH-BOARD_WIDTH)/4-1.5*SCALED_SQUARE_SIZE;
        CHALLENGE_POS_Y = BOARD_HEIGHT/2-1.5*SCALED_SQUARE_SIZE+BOARD_MARGIN_TOP;
        CONTROLS_POS_Y = BOARD_Y - CONTROLS_HEIGHT - 10;
        BOARD_ABS_X = BOARD_X + BOARD_PADDING_LEFT*BOARD_SCALE_FACTOR;
        BOARD_ABS_Y = BOARD_Y + BOARD_PADDING_TOP*BOARD_SCALE_FACTOR;
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
            } else if (key == KeyCode.SLASH) {
                showHint();
            } /*else if (key == KeyCode.R) {
                resetBoardAction();
            }*/
            e.consume();
        });
    }

    /**
     * TODO
     * Check if the challenge has been completed
     */
    private void checkCompletion() {
        if (game.checkCompletion()) {
            root.setOpacity(0.7);
            Alert completed = new Alert(Alert.AlertType.NONE,
                    "Congratulations! You completed this challenge! " +
                    "\nClick next to move onto the next puzzle.",
                    ButtonType.NEXT);
            completed.setTitle("Challenge complete");
            completed.showAndWait();
            if (completed.getResult() == ButtonType.NEXT) {
                game.nextChallenge(game.currentChallengeNumber+1);
                HINTS_COUNTER = HINTS_LIMIT;
                makeChallenge(game.getChallenge());
                resetBoard();
            }
            root.setOpacity(1.0);
        }
    }

    /**
     * Start a new game
     */
    private void newGame() {
        game.newGame();
        resetBoard();
        makeChallenge(game.getChallenge());
    }

    /**
     * Reset board state
     */
    private void resetBoard() {
        game.resetBoard();
        for (PieceTile p : pieceTilesList) {
            p.snapToHome();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("IQ Focus Puzzle");
        primaryStage.getIcons().add(ICON_IMAGE);
        primaryStage.setResizable(false);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        root.getChildren().addAll(
                challengeSquares,
                board,
                challengeSquaresBoard,
                boardPieces,
                pieceTiles,
                controls
        );

        setKeyEvents(scene);
        makeBoard();
        initVariables();

        makePieceTiles();
        makeControls(primaryStage);

        newGame();
        makeHelp(helpStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
