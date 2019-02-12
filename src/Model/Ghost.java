package Model;
import Controller.GhostMovedListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 * class that represents a Ghost running on a separate Thread
 */
public class Ghost implements Runnable {

    private int x;
    private int y;
    private int number;
    private int speed;
    private boolean running = true;
    private GhostMovedListener listener;
    private Thread thread;


    /**
     * list of available speeds
     */
    private enum SpeedOptions {
        SLOW(1000),
        MEDIUM(500),
        FAST(200);

        private int speedValue;

        SpeedOptions(int value) { this.speedValue = value; }

        public int getSpeed() { return this.speedValue; }
    }


    /**
     * setter for the running field
     * @param running false when Thread should stop, true otherwise
     */
    public void setRunning(boolean running) {
        this.running = running;
    }


    /**
     * setter for the listener field
     * @param listener object responsible for handling the GhostMoved event
     */
    public void setListener(GhostMovedListener listener) {
        this.listener = listener;
    }


    /**
     * hardcoded values for initial ghost coordinates on board
     * @return list of positions
     */
    private List<List<Integer>> getInitialGhostPositions() {
        List<List<Integer>> positions = new ArrayList<>();
        positions.add(new ArrayList<>(Arrays.asList(1, 1)));
        positions.add(new ArrayList<>(Arrays.asList(14, 1)));
        positions.add(new ArrayList<>(Arrays.asList(1, 19)));
        positions.add(new ArrayList<>(Arrays.asList(14, 19)));
        return positions;
}

    /**
     * constructor for Ghost
     * @param nr ghost "ID"
     * @param s speed
     */
    public Ghost(int nr, String s) {
        List<Integer> position = getInitialGhostPositions().get(nr);
        x = position.get(0);
        y = position.get(1);
        number = nr;
        int sInt = SpeedOptions.valueOf(s.toUpperCase()).getSpeed();
        speed = sInt;
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
     * getter for the number
     * @return number
     */
    public int getNumber() {
        return number;
    }


    /**
     * method that changes ghost coordinates
     * @param newX
     * @param neyY
     */
    public void changePosition(int newX, int neyY) {
        setX(newX);
        setY(neyY);
    }


    /**
     * method that checks whether or not the ghost is on the same cell as PacMan
     */
    private void checkForCollision() {
        if (getX() == PacMan.getPacMan().getX() && getY() == PacMan.getPacMan().getY()) {
            listener.collisionDetected();
        }
    }


    /**
     * method that moves the ghost randomly with the selected speed
     * restrictions:
     * a ghost cannot move through walls
     * a ghost cannot move in the opposite direction immediately after a move, unless it is the only move possible
     */
    @Override
    public void run() {
        List<List<Integer>> matrix = GridModel.getMatrix(false);
        int prevMovement = 5;

        while (running) {

            checkForCollision();

            int prevX = getX();
            int prevY = getY();
            int leftNeighborCell = matrix.get(prevY).get(prevX-1);
            int rightNeighborCell = matrix.get(prevY).get(prevX+1);
            int upNeighborCell = matrix.get(prevY-1).get(prevX);
            int downNeighborCell = matrix.get(prevY+1).get(prevX);

            int randomMovement = ThreadLocalRandom.current().nextInt(0, 4);

            switch (randomMovement) {
                case 0: //up
                    if (matrix.get(prevY - 1).get(prevX) != 1 && matrix.get(prevY - 1).get(prevX) != 4)
                        if (prevMovement != 1 || (leftNeighborCell == 1 && rightNeighborCell == 1 && downNeighborCell == 1))
                            {
                                changePosition(prevX, prevY - 1);
                                listener.ghostMoved(prevX, prevY, this);
                                prevMovement = 0;
                                checkForCollision();
                                sleep(speed);
                            }
                    break;
                case 1: //down
                    if (matrix.get(prevY + 1).get(prevX) != 1 && matrix.get(prevY + 1).get(prevX) != 4)
                        if (prevMovement != 0 || (leftNeighborCell == 1 && rightNeighborCell == 1 && upNeighborCell == 1))
                            {
                                changePosition(prevX, prevY + 1);
                                listener.ghostMoved(prevX, prevY, this);
                                prevMovement = 1;
                                checkForCollision();
                                sleep(speed);
                            }
                    break;
                case 2: //left
                    if (matrix.get(prevY).get(prevX - 1) != 1 && matrix.get(prevY).get(prevX - 1) != 4)
                        if (prevMovement != 3 || (upNeighborCell == 1 && rightNeighborCell == 1 && downNeighborCell == 1))
                            {

                                changePosition(prevX - 1, prevY);
                                listener.ghostMoved(prevX, prevY, this);
                                prevMovement = 2;
                                checkForCollision();
                                sleep(speed);
                            }
                    break;
                case 3: //right
                    if (matrix.get(prevY).get(prevX + 1) != 1 && matrix.get(prevY).get(prevX + 1) != 4)
                        if (prevMovement != 2 || (upNeighborCell == 1 && leftNeighborCell == 1 && downNeighborCell == 1))
                            {
                                changePosition(prevX +1, prevY);
                                listener.ghostMoved(prevX, prevY, this);
                                prevMovement = 3;
                                checkForCollision();
                                sleep(speed);
                            }
                    break;
            }
        }
    }


    /**
     * method that starts the Thread
     */
    public void start() {
        if (thread == null) {
            thread = new Thread (this);
            thread.start ();
        }
    }


    /**
     * method that stops the Thread
     */
    public void stop(){
        if (thread != null) {
            running = false;
            //thread.stop();
            thread.interrupt();
        }
    }


    /**
     * method that sleeps the Thread
     * @param time
     */
    public void sleep(int time) {
        try {
            if (thread != null) {
                Thread.sleep(time);
            }
        }
        catch (InterruptedException e) {
            running = false;
            stop();
        }
    }
}