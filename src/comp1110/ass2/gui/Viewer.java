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
    private static final int SQUARE_SIZE = 70;
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

    private Text getErrorText(String text) {
        Text error = new Text("ERROR: " + text);
        error.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        error.setFill(Color.RED);
        return error;
    }

    /**
     * Gives an array of pieces from a placement string
     * @param placement Placement string
     * @return Array of Piece's from placement string
     */

    private Piece[] getPiecesFromPlacement(String placement) {
        /*if (!FocusGame.isPlacementStringWellFormed(placement)) {
            return null;
        }*/
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

    private void drawErrorBox(String err) {
        HBox errorBox = new HBox();
        errorBox.getChildren().add(getErrorText(err));
        errorBox.setAlignment(Pos.CENTER);
        errorBox.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        errorBox.setLayoutX(0);
        errorBox.setLayoutY(0);
        errors.getChildren().add(errorBox);
    }

    private int[] getOrientationOffsets(PieceType pieceType, Orientation orientation) {
        int[] offsets = new int[]{0, 0};
        switch (orientation) {
            case Zero:
                break;
            case One:
                switch (pieceType) {
                    case A:
                    case B:
                    case C:
                    case D:
                    case E:
                    case G:
                    case I:
                    case J:
                        offsets[0] = 2;
                        break;
                    case F:
                        offsets[0] = -1;
                        break;
                    case H:
                        offsets[0] = 3;
                        break;
                }
                break;
            case Two:
                switch (pieceType) {
                    case A:
                    case D:
                    case E:
                    case G:
                        offsets[0] = 3;
                        offsets[1] = 2;
                        break;
                    case B:
                    case C:
                    case J:
                        offsets[0] = 4;
                        offsets[1] = 2;
                        break;
                    case F:
                        offsets[0] = 3;
                        offsets[1] = 1;
                        break;
                    case H:
                        offsets[0] = 3;
                        offsets[1] = 3;
                    case I:
                        offsets[0] = 2;
                        offsets[1] = 2;
                        break;
                }
                break;
            case Three:
                switch (pieceType) {
                    case A:
                    case D:
                    case E:
                    case F:
                    case G:
                    case H:
                        offsets[1] = 3;
                        break;
                    case B:
                    case C:
                    case J:
                        offsets[1] = 4;
                        break;
                    case I:
                        offsets[1] = 2;
                        break;
                }
                break;
            default: break;
        }
        return offsets;
    }

    private ImageView[] getImageFromPiece(Piece[] pieceList) {

        ImageView[] images = new ImageView[pieceList.length];
        int i = 0;
        for (Piece p : pieceList) {
            images[i] = getImageFromFile(p.getPieceType());
            int[] offsets = getOrientationOffsets(p.getPieceType(), p.getOrientation());
            System.out.println(offsets[0] + " " + offsets[1]);
            int xPos = BOARD_MARGIN_LEFT+p.getLocation().getX()*SQUARE_SIZE;
            int yPos = BOARD_MARGIN_TOP+p.getLocation().getY()*SQUARE_SIZE;
            int angle = p.getOrientation().toInt()*90;
            Rotate rotation = new Rotate(angle,xPos,yPos);
            //images[i].getTransforms().add(rotation);
            images[i].setRotate(angle);
            images[i].setX(xPos-SQUARE_SIZE/2);
            images[i].setY(yPos+SQUARE_SIZE/2);
            i++;
        }
        return images;
    }

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
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement) {
        errors.getChildren().clear();
        pieces.getChildren().clear();
        System.out.println("Placement: " + placement);
        /*if (!FocusGame.isPlacementStringWellFormed(placement)) { // insert validation rules here
            drawErrorBox("Placement string not valid");
            return;
        }*/
        Piece[] pieceList = getPiecesFromPlacement(placement);
        for (Piece p : pieceList) {
            System.out.println(p);
        }
        ImageView[] imageList = getImageFromPiece(pieceList);
        pieces.getChildren().addAll(imageList);
    }

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
