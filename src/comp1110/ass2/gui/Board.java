package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.animation.FadeTransition;
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
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
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
    private static final double CHALLENGE_PIECE_OPACITY = 0.3;
    private static final String VERSION = "1.0";

    private static final String URI_BASE = "assets/";
    private static final Image ICON_IMAGE = new Image(Board.class.getResourceAsStream(URI_BASE + "icon.png"));
    private static final FileChooser.ExtensionFilter SAVE_EXTENSION_FILTER = new FileChooser.ExtensionFilter("IQ Focus Save (*.iqs)", "*.iqs");

    private boolean SHOW_CHALLENGE = true; // show challenge on board
    private Boolean HINTS_LIMITED = true;
    private int HINTS_LIMIT = 3;
    private int HINTS_COUNTER;

    private boolean AUTOSAVE = false;
    private File CURRENT_SAVEFILE = null;

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

    private PieceTile[] pieceTilesList = new PieceTile[10];

    private Group root = new Group();
    private Group controls = new Group();
    private Pane board = new Pane();
    private Pane pieceTiles = new Pane();
    private Pane challengeSquares = new Pane();
    private Pane challengeSquaresBoard = new Pane();
    private Pane boardMessages = new Pane();
    private Text userMessage = new Text();
    private Text hintCounter = new Text();

    private Stage helpStage = new Stage();
    private Group helpRoot = new Group();

    private FileChooser saveFileChooser = new FileChooser();
    private FileChooser loadFileChooser = new FileChooser();
    private Alert autosaveAlert = new Alert(Alert.AlertType.NONE, "Would you like to turn autosave on?",ButtonType.YES,ButtonType.NO);

    private FocusGame game = new FocusGame();

    /**
     * These methods implement hints and challenge generation for the FocusGame.
     */

    // FIXME Task 11: Generate interesting challenges (each challenge may have just one solution)
    public String challengeGenerator (int difficulty) {
        String[] pack = new String[9];
        String challenge = "";


        //Generate Completely Random String
        if (difficulty == 0) {
            for (int j = 0; j < 9; j++) {
                String g = generateRandomColor();
                pack[j] = g;
            }
            challenge = pack[0] + pack[1] + pack[2] + pack[3] + pack[4] + pack[5] + pack[6] + pack[7] + pack[8];
            return challenge;
        }
        //Generate Difficulty 1 (EASY)
        if (difficulty == 1) {
            //Generate challenge
            return challenge;
        }
        //Generate Difficulty 2 (MEDIUM)
        if (difficulty == 2) {
            return challenge;
        }
        //Generate Difficulty 3 (HARD)
        if (difficulty == 3) {
            return challenge;
        }
        return challenge;

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

        PieceTile(PieceType p) throws IOException {

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
                    if (placed) {
                        game.undoOperation(game.getBoardPlacementString(),placement);
                    }
                    mX = e.getSceneX();
                    mY = e.getSceneY();
                    setOpacity(0.6);
                    currentPiece = this;
                }
            });

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

            setOnMouseReleased(e -> {
                try { // wrapped in case of error with autosaving
                    snapToBoard();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                currentPiece = null;
                setOpacity(1.0);
            });
        }

        /**
         * Attempts to place the piece given the x and y locations of it.
         */

        private void snapToBoard() throws IOException {
            // offsets account for the orientation
            double[] offsets = Viewer.getOrientationOffsets(pieceType,orientation);
            double aX = SCALED_SQUARE_SIZE*offsets[0]*-1+getLayoutX();
            double aY = SCALED_SQUARE_SIZE*offsets[1]*-1+getLayoutY();

            if (!xyOnBoard(aX,aY)) {
                snapToHome();
            } else {
                location = getLocationFromSceneXY(aX,aY);
                // builds placement string
                placement = pieceType.toString().toLowerCase() + location.getX() + location.getY() + orientation.toInt();
                if (game.checkPieceToBoard(placement)) {
                    placePiece(new Piece(placement));
                    placed = true;
                    makePlacement(placement);
                } else {
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
    private void makePlacement(String placement) throws IOException {
        game.addPieceToBoard(placement);
        if (allPiecesPlaced()) {
            checkCompletion();
        }
        if (AUTOSAVE) {
            saveToFile(CURRENT_SAVEFILE,game.getSaveString());
        }
    }

    /**
     * Checks if all the pieces have been placed on the board
     * @return true if all pieces have been placed
     */
    private boolean allPiecesPlaced() {
        for (PieceTile p : pieceTilesList) {
            if (!p.placed) {
                return false;
            }
        }
        return true;
    }

    /**
     * Makes game controls and GUI
     */
    private void makeControls(Stage stage) {
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
        controlBox.setSpacing(20);
        controlBox.setMinWidth(WINDOW_WIDTH);
        controlBox.setLayoutY(CONTROLS_POS_Y);
        controlBox.setAlignment(Pos.CENTER);

        Button newGame = new Button("New Game");
        newGame.setOnAction(e -> {
            newGameAction();
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
            try {
                showHint();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        Button help = new Button("Help");
        help.setOnAction(e -> helpStage.show());

        Button loadGame = new Button("Load Game");
        loadGame.setOnAction(e -> {
            try {
                loadGameAction(stage);
            } catch (IOException error) {
                System.out.println(error);
            }
        });

        Button saveGame = new Button("Save Game");
        saveGame.setOnAction(e -> saveGameAction(stage));

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


    /* ACTIONS FOR CONTROL EVENT HANDLERS */
    /**
     * Attempts to place a piece as a hint on the board.
     * Piece is chosen at random from solutions string.
     * @throws IOException Autosave
     */
    private void showHint() throws IOException {
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
                        game.undoOperation(game.getBoardPlacementString(), p.placement);
                    }
                    p.placement = hintPlacement;
                    p.placePiece(hintPiece);
                    p.placed = true;
                    p.setRotation(hintPiece.getOrientation());
                    p.orientation = hintPiece.getOrientation();
                    p.location = hintPiece.getLocation();
                    makePlacement(hintPlacement);
                }
            }
        }
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
            } else if (key == KeyCode.H) {
                try {
                    showHint();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            e.consume();
        });
    }

    /**
     * Action for resetting the board
     */
    private void resetBoardAction() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to reset the board?",ButtonType.YES,ButtonType.NO);
        confirmation.setTitle("Reset Board");
        confirmation.setHeaderText("Reset Board?");
        confirmation.showAndWait();
        if (confirmation.getResult() == ButtonType.YES) {
            resetBoard();
        }
    }

    /**
     * Action for starting a new game
     */
    private void newGameAction() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to start a new game?",ButtonType.YES,ButtonType.NO);
        confirmation.setTitle("New Game");
        confirmation.setHeaderText("New Game?");
        confirmation.showAndWait();
        if (confirmation.getResult() == ButtonType.YES) {
            newGame();
        }
    }

    /**
     * Action for load game button
     * @param stage the stage to show the load game file chooser from
     * @throws IOException
     */
    private void loadGameAction(Stage stage) throws IOException {
        File tmp = showLoadGameFileChooser(stage);
        if (tmp != null) {
            BufferedReader br = new BufferedReader(new FileReader(tmp));
            String saveString = br.readLine();
            if (saveString != null && FocusGame.isSaveStringValid(saveString)) {
                loadGame(saveString);
                if (showAutoSaveAlert()) {
                    CURRENT_SAVEFILE = tmp;
                    AUTOSAVE = true;
                    userMessage.setText("Loaded file - autosave turned on");
                } else {
                    userMessage.setText("Loaded file");
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "", ButtonType.OK);
                alert.setHeaderText("Invalid Save File");
                alert.setTitle("Error - Invalid Save");
                alert.showAndWait();
            }
        }
    }

    /**
     * Save game action
     * @param stage The stage to show the save file chooser on
     * @throws IOException
     */
    private void saveGameAction(Stage stage) {
        File tmp = showSaveFileChooser(stage);
        if (tmp != null && saveToFile(tmp, game.getSaveString())) {
            if (showAutoSaveAlert()) {
                CURRENT_SAVEFILE = tmp;
                AUTOSAVE = true;
                userMessage.setText("Saved file - autosave turned on");
            } else {
                userMessage.setText("Saved file");
            }
        } else {
            System.out.println("ERROR WITH SAVING - FILE IS NULL");
        }
    }

    /**
     * Sets the board and game instance fields given a save string
     * @param saveString A valid save string
     */
    private void loadGame(String saveString) {
        resetBoard();
        String[] saveArray = saveString.split(",");
        game.nextChallenge(Integer.parseInt(saveArray[0]));
        makeChallenge(game.getChallenge());
        if (saveArray.length == 2) {
            game.addPiecesToBoard(saveArray[1]);
            makePiecePlacementsFromString(saveArray[1]);
        }
    }

    /**
     * Alert for user asking if autosave should be turned on for the file just saved/loaded
     * @return true if user clicks yes on alert
     */
    private boolean showAutoSaveAlert() {
        autosaveAlert.showAndWait();
        return autosaveAlert.getResult() == ButtonType.YES;
    }

    /**
     * Shows the load game file chooser window
     * @param stage The stage to show the FileChooser on
     * @return the File selected from the FileChooser
     */
    private File showLoadGameFileChooser(Stage stage) {
        return loadFileChooser.showOpenDialog(stage);
    }

    /**
     * Shows the file chooser for saving the game
     * @param stage The stage to show the file chooser on
     * @return File selected from FileChooser
     */
    private File showSaveFileChooser(Stage stage) {
        saveFileChooser.setInitialFileName("IQ_FOCUS_SAVE_" + new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date()));
        return saveFileChooser.showSaveDialog(stage);
    }

    /**
     * Saves a save string to file
     * @param file The File to save the game to
     * @param saveString A save string of the current board state
     * @return true for success
     */
    private boolean saveToFile(File file, String saveString) {
        userMessage.setText("Saving....");
        if (file != null) {
            try (PrintWriter out = new PrintWriter(file.getAbsolutePath())) {
                out.println(saveString);
            } catch (IOException e) {
                System.out.println(e);
                return false;
            }
        }
        userMessage.setText("Saved! - " + new SimpleDateFormat("H:mm:ss").format(new Date()));
        return true;
    }

    /**
     * Makes piece placements to board given placement string
     * @param placementString A valid placement string
     */
    private void makePiecePlacementsFromString(String placementString) {
        for (String p : FocusGame.splitPlacementString(placementString)) {
            Piece piece = new Piece(p);
            for (PieceTile pT : pieceTilesList) {
                if (pT.pieceType == piece.getPieceType()) {
                    pT.placement = p;
                    pT.placePiece(piece);
                    pT.placed = true;
                    pT.orientation = piece.getOrientation();
                    pT.setRotation(piece.getOrientation());
                    pT.location = piece.getLocation();
                }
            }
        }
    }

    /**
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
                HINTS_COUNTER = 0;
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

    /**
     * Sets up user messages
     */
    private void makeBoardMessages() {
        boardMessages.getChildren().add(userMessage);
        boardMessages.setLayoutY(15);
        boardMessages.setLayoutX(5);
        userMessage.setFont(new Font("Tahoma", 15));
    }

    /**
     * Sets up help window
     * @param stage Stage to show help on
     */
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
    private void makePieceTiles() throws IOException {
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
     * Makes file choosers for saving and loading game
     */
    private void makeFileChoosers() {
        saveFileChooser.setTitle("Save Game");
        saveFileChooser.getExtensionFilters().add(SAVE_EXTENSION_FILTER);
        loadFileChooser.setTitle("Load Game");
        loadFileChooser.getExtensionFilters().add(SAVE_EXTENSION_FILTER);
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

    /* JAVA FX SET UP */
    public static void main(String[] args) {
        launch(args);
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
                pieceTiles,
                controls,
                boardMessages
        );

        setKeyEvents(scene);
        makeBoard();
        initVariables();
        makeBoardMessages();
        makePieceTiles();
        makeControls(primaryStage);

        newGame();
        makeHelp(helpStage);
        makeFileChoosers();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
