package comp1110.ass2;

public enum State {
    WHITE, RED, BLUE, GREEN,// The four colours a square may have
    EMPTY,// Locations on board haven't been used by placement
    NULL;// Bottom left and right(not belongs to the board) or the square in piece has no colour

    /**
     * Since Null is a hypothesis, we should not consider it.
     * Empty can only be updated by color state and for the Null state,we should ignore it.
     * i,e.Null cannot update other value and when we put a placement on board, if squares of
     * Null are outside the board, we should delete the Null square.
     */
    public void ignoreNull() {}

}
