package comp1110.ass2;

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
