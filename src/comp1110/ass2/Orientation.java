package comp1110.ass2;

public enum Orientation {
    Zero, One, Two, Three;

    //Translate the orientation to number
    public int toInt(){ return 0; };

    /*
     * Translate the orientation to the string, used for debugging.
     * For example, Zero is equal to "North", One is equal to "West" and so on.
     */
    @Override
    public String toString(){ return null; }
}
