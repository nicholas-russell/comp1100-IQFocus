package comp1110.ass2;

public enum Orientation {
    Zero("North",0),
    One("West",1),
    Two("South",2),
    Three("East",3);

    private String compassOrientation;
    private int numberOrientation;

    private Orientation(String compassOrientation, int num) {
        this.compassOrientation = compassOrientation;
        this.numberOrientation = num;
    }

    /** Translate the orientation to number */
    public int toInt(){
        return this.numberOrientation;
    };

    /**
     * Translate the orientation to the string, used for debugging.
     * For example, Zero is equal to "North", One is equal to "West" and so on.
     */
    @Override
    public String toString(){
        return compassOrientation;
    }
}
