package View;

import Model.Ghost;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


/**
 * class that holds all GUI elements of a game
 */
public class GameView extends Scene {

    private static int cellSize = 30;
    private Grid grid;
    private Rectangle pacManView;
    private List<Rectangle> ghostViews;


    /**
     * method that returns paths of ghost images
     * @return list of paths
     */
    private List<String> getImages() {
        List<String> images = new ArrayList<>();
        images.add("/images/evil_minion_1.png");
        images.add("/images/evil_minion_2.jpg");
        images.add("/images/evil_minion_3.jpg");
        images.add("/images/evil_minion_4.jpg");
        return images;
    }


    /**
     * getter for the grid
     * @return grid
     */
    public Grid getGrid() {
        return grid;
    }


    /**
     * constructor
     * @param root
     * @param width
     * @param height
     */
    public GameView(Grid root, double width, double height) {
        super(root, width, height);
        grid = root;
        pacManView = new Rectangle(cellSize, cellSize);
        String path = "/images/minion.png";
        Image image = new Image(path);
        ImagePattern pattern = new ImagePattern(image);
        pacManView.setFill(pattern);
    }


    /**
     * method that updates a cell
     * @param cellContent content to fill the cell with
     * @param x x coordinate of cell to update
     * @param y y coordinate of cell to update
     */
    private void updateCell(Rectangle cellContent, int x, int y) {
        Rectangle cell = (Rectangle)(grid.getChildren().get(y*16+x));
        cell.setFill(cellContent.getFill());
    }


    /**
     * method that moves the PacMan over the grid
     * @param x
     * @param y
     */
    public void setPacManPosition(int x, int y) {
        updateCell(pacManView, x, y);
    }


    /**
     * method that moves the ghost over the grid
     * @param ghost ghost to move
     */
    public void setGhostPosition(Ghost ghost) {
        updateCell(ghostViews.get(ghost.getNumber()), ghost.getX(), ghost.getY());
    }


    /**
     * method that resets a cell after PacMan moves over it
     * @param x
     * @param y
     */
    public void resetLeftCell(int x, int y) {
        Rectangle tile = new Rectangle(cellSize, cellSize);
        tile.setFill(grid.getTerrainColor());
        updateCell(tile, x, y);
    }


    /**
     * method that resets a cell after a ghost moves over it
     * @param x
     * @param y
     * @param tileValue 0 if cell contains food, 2 if empty
     */
    public void resetLeftCellByGhost(int x, int y, int tileValue) {
        Rectangle tile = new Rectangle(cellSize, cellSize);
        if (tileValue == 0)
            tile.setFill(grid.getFoodPattern());
        else
            tile.setFill(grid.getTerrainColor());
        updateCell(tile, x, y);
    }


    /**
     * method that initializes the list of ghosts with corresponding images
     * @param nrOfGhosts number of ghost to add
     */
    public void setGhosts(int nrOfGhosts) {
        ghostViews = new ArrayList<>();
        IntStream.range(0, nrOfGhosts).forEach(i -> {
            Rectangle singleGhost = new Rectangle(cellSize, cellSize);

            String path = getImages().get(i);
            Image image = new Image(path);
            ImagePattern pattern = new ImagePattern(image);
            singleGhost.setFill(pattern);

            ghostViews.add(singleGhost);
        });
    }
}
