package comp1110.ass2.gui;

import comp1110.ass2.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A very simple viewer for piece placements in the IQ-Focus game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various piece
 * placements.
 */
public class Viewer extends Application {

    /* board layout */
    private static final double SQUARE_SIZE = 70;
    private static final int VIEWER_WIDTH = 720;
    private static final int VIEWER_HEIGHT = 520;
    private static final int BOARD_MARGIN_TOP = 87;
    private static final int BOARD_MARGIN_LEFT = 41;
    private static final int BOARD_MARGIN_RIGHT = 43;
    private static final int BOARD_MARGIN_BOTTOM = 25;
    private static final double SQUARE_SCALE_FACTOR = 0.70;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private Pane pieces = new Pane();
    private Pane board = new Pane();
    private Pane errors = new Pane();
    private TextField textField;

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
    private ImageView getImageFromFile(PieceType p) {
        InputStream pieceFile = getClass().getResourceAsStream(URI_BASE + p.toString().toLowerCase() + ".png");
        Image pieceImage = new Image(pieceFile);
        double imageHeight = pieceImage.getHeight();

        ImageView pieceImageView = new ImageView(pieceImage);
        pieceImageView.setFitHeight(SQUARE_SCALE_FACTOR*imageHeight);
        pieceImageView.setPreserveRatio(true);
        return pieceImageView;
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
            images[i] = getImageFromFile(p.getPieceType());
            double[] offsets = getOrientationOffsets(p.getPieceType(), p.getOrientation());
            double xPos = BOARD_MARGIN_LEFT+p.getLocation().getX()*SQUARE_SIZE;
            double yPos = BOARD_MARGIN_TOP+p.getLocation().getY()*SQUARE_SIZE;
            int angle = p.getOrientation().toInt()*90;
            images[i].setRotate(angle);
            images[i].setX(xPos+(SQUARE_SIZE*offsets[0]));
            images[i].setY(yPos+(SQUARE_SIZE*offsets[1]));
            i++;
        }
        return images;
    }

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement) {
        errors.getChildren().clear();
        pieces.getChildren().clear();
        if (!FocusGame.isPlacementStringWellFormed(placement)) {
            drawErrorBox("Placement string not valid");
            return;
        }
        Piece[] pieceList = getPiecesFromPlacement(placement);
        ImageView[] imageList = getImageFromPiece(pieceList);
        pieces.getChildren().addAll(imageList);
    }

    /**
     * Draw the game board
     * @throws IOException
     */
    private void makeBoard() throws IOException {
        Image boardImage = new Image(new FileInputStream(URI_BASE + "board.png"));
        ImageView boardIv = new ImageView(boardImage);
        boardIv.setPreserveRatio(true);
        board.getChildren().addAll(boardIv);
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                makePlacement(textField.getText());
                textField.clear();
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("FocusGame Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().addAll(controls, board, pieces, errors);

        makeControls();
        makeBoard();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
