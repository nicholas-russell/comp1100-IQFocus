package comp1110.ass2;

/**
 * Simple class that defines locations on the game board.
 *
 * @author Nicholas Russell
 * @version 0.1
 * @since 21/08/2019
 */

public class Location {
    private int X;
    private int Y;

    public Location(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    public int getX() {return X;}
    public int getY() {return Y;}

    @Override
    public String toString() {
        return "x:" + getX() + ", y:" + getY();
    }
}
