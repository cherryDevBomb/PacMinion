package View;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.stream.IntStream;


/**
 * class that defines the GUI for the game grid
 */
public class Grid extends GridPane {

    private Color terrainColor = Color.valueOf("#00A3E8");
    private Color glassesColor = Color.valueOf("#4e5054");
    private ImagePattern foodPattern;
    private Color wallColor = Color.valueOf("#084a8b");

    private int numberOfFoodCells = 0;


    /**
     * getter for terrainColor
     * @return terrainColor
     */
    Color getTerrainColor() {
        return terrainColor;
    }


    /**
     * getter for foodPattern
     * @return foodPattern
     */
    ImagePattern getFoodPattern() {
        return foodPattern;
    }


    /**
     * getter for the number of cells with food
     * @return numberOfFoodCells
     */
    public int getNumberOfFoodCells() {
        return numberOfFoodCells;
    }


    /**
     * constructor
     * @param cellSize size of a single cell
     * @param matrix grid model
     */
    public Grid(int cellSize, List<List<Integer>> matrix) {
        String foodPath = "/images/banana.jpg";
        Image image = new Image(foodPath);
        foodPattern = new ImagePattern(image);

        IntStream.range(0, matrix.size()).forEach(i->
                IntStream.range(0,matrix.get(0).size()).forEach(j->{
                    Rectangle tile = new Rectangle(cellSize, cellSize);
                    if (matrix.get(i).get(j) == 0) {     //food
                        tile.setFill(foodPattern);
                        numberOfFoodCells++;
                    }
                    else if (matrix.get(i).get(j) == 1){ //wall
                        tile.setFill(wallColor);
                    }
                    else if (matrix.get(i).get(j) == 2){ //free cell
                        tile.setFill(terrainColor);
                    }
                    else if (matrix.get(i).get(j) == 3){ //for design purposes
                        tile.setFill(Color.WHITE);
                    }
                    else if (matrix.get(i).get(j) == 4){ //for design purposes, treated as obstacle
                        tile.setFill(glassesColor);
                    }
                    else if (matrix.get(i).get(j) == 5){ //for design purposes
                        tile.setFill(Color.BLACK);
                    }
                    add(tile, j, i);
                })
        );
    }
}





