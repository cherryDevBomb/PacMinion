package Model;

/**
 * Singleton class that stores PacMan coordinates on board
 */
public class PacMan {

    private static PacMan pacMan;

    private int x;
    private int y;


    /**
     * PacMan constructor that sets initial position to the hardcoded value of <column 4, row 13>
     */
    private PacMan() {
        x = 4;
        y = 13;
    }


    /**
     * static method for getting the singleton PacMan instance
     * @return PacMan
     */
    public static PacMan getPacMan() {
        if (pacMan == null)
            pacMan = new PacMan();
        return pacMan;
    }


    /**
     * getter for the x coordinate
     * @return x
     */
    public int getX() {
        return x;
    }


    /**
     * setter for the x coordinate
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }


    /**
     * getter for the y coordinate
     * @return y
     */
    public int getY() {
        return y;
    }


    /**
     * setter for the y coordinate
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }


    /**
     * method that changes PacMan coordinates
     * @param newX
     * @param neyY
     */
    public void changePosition(int newX, int neyY) {
        setX(newX);
        setY(neyY);
    }
}
