package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static comp1110.ass2.State.*;
import static org.junit.Assert.*;


public class FocusGameTest {
    FocusGame game = new FocusGame();
    // Original duration < 0.05sec * 10 = 0.5sec
    @Rule
    public Timeout globalTimeout = Timeout.millis(500);

    private void checkBoard(State[][] initboard, State[][] changedboard) {
        for (int i = 0; i < 45; i++) {
            assertTrue("expected " + initboard[i / 9][i % 9] + " at " + i + " but got " + changedboard[i / 9][i % 9],
                    initboard[i / 9][i % 9] == changedboard[i / 9][i % 9]);
            break;
        }
    }

    private void checkResult(String in, boolean expected) {
        game.resetBoard();
        boolean out = game.addPieceToBoard(in);
        assertTrue("Input was '" + in + "', expected " +
                expected + " but got " + out, out == expected);
    }

    private String[] simplePieceNotValid = {
            "1000",
            "z123", // The first character is not valid
            "ab23",
            "eA12", // The second character is not valid
            "c460",
            "f5a1", // The third character is not valid
            "j004",
            "d11w", // The fourth character is not valid
    };

    private String[] simplePieceValid = {
            "a000",
            "b110",
            "c220",
            "d332",
            "e223",
            "f721",
            "g600",
            "h503",
    };

    private String[] multiplePieceNotValid = {
            "a403j502h320",
            "e523a403j501", // pieces overlap
            "b602d620f530",
            "e000h020i302" // piece offboard

    };

    private String[] multiplePieceValid = {
            "c430e611f511g110i200",
            "a630b402c202d432e220",
            "d320e101f240h601j130",
            "b601c430e022f300g100"
    };

    private State[][][] initboard = {
            {
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {NULL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, NULL}    // board not includes anything
            }, {
                    {EMPTY, BLUE, BLUE, BLUE, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, RED, RED, WHITE, BLUE, EMPTY, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, BLUE, WHITE, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {NULL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, NULL}   // e100g310
            }, {
                    {EMPTY, EMPTY, WHITE, WHITE, RED, WHITE, EMPTY, EMPTY, EMPTY},
                    {EMPTY, EMPTY, EMPTY, EMPTY, GREEN, WHITE, EMPTY, EMPTY, EMPTY},
                    {GREEN, GREEN, WHITE, RED, GREEN, WHITE, EMPTY, EMPTY, EMPTY},
                    {GREEN, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                    {NULL, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, EMPTY, NULL}  // f503h201j020
    }
    };

    @Test
    public void simplepiece() {
        for (int i = 0; i < simplePieceNotValid.length; i++) {
            checkResult(simplePieceNotValid[i], false);
        }

        for (int j = 0; j < simplePieceValid.length; j++) {
            checkResult(simplePieceValid[j], true);
        }
    }

    @Test
    public void multiplepieces() {
        for (int i = 0; i < multiplePieceNotValid.length; i++) {
            checkResult(multiplePieceNotValid[i], false);
        }

        for (int j = 0; j < multiplePieceValid.length; j++) {
            checkResult(multiplePieceValid[j], true);
        }
    }

    @Test
    public void combination() {
        for (int i = 0; i < simplePieceNotValid.length; i++) {
            for (int j = 0; j < multiplePieceValid.length; j++) {
                checkResult(simplePieceNotValid[i] + multiplePieceValid[j], false);
            }
        }
    }

    @Test
    public void addsuccess() {
        game.addPieceToBoard("f503h201j020");
        checkBoard(initboard[1], game.board);
        game.addPieceToBoard("e100g310");
        checkBoard(initboard[2], game.board);
    }

    @Test
    public void addfail() {
        for (int i = 0; i < simplePieceNotValid.length; i++) {
            game.resetBoard();
            game.addPieceToBoard(simplePieceNotValid[i]);
            checkBoard(initboard[0], game.board);
        }

        for (int j = 0; j < multiplePieceNotValid.length; j++) {
            game.resetBoard();
            game.addPieceToBoard(multiplePieceNotValid[j]);
            checkBoard(initboard[0], game.board);
        }
    }

    @Test
    public void undo() {
        FocusGame gametmp = new FocusGame();
        for (int i = 0; i < simplePieceValid.length; i++) {
            for (int j = simplePieceValid.length - 1; j > i; j--) {
                game.resetBoard();
                game.addPieceToBoard(simplePieceValid[i]);
                gametmp.resetBoard();
                gametmp.addPieceToBoard(simplePieceValid[i] + simplePieceValid[j]);
                gametmp.undoOperation(simplePieceValid[i] + simplePieceValid[j], simplePieceValid[j]);
                checkBoard(game.board, gametmp.board);
            }
        }
    }
}