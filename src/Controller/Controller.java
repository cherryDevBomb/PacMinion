package Controller;

import Model.Ghost;
import Model.GridModel;
import Model.PacMan;
import View.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


/**
 * Controller class
 */
public class Controller implements GhostMovedListener {

    private Stage primaryStage;

    private List<List<Integer>> matrix;
    private int numberOfFoodCells;
    private GameView game;
    private PacMan pacMan;
    private List<Ghost> ghosts;


    /**
     * constructor
     * @param primaryStage
     */
    public Controller(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    /**
     * method that is first called on app launch
     */
    public void launch() {
        primaryStage.setTitle("PacMinion");

        String path = "/images/minion.png";
        try {
            FileInputStream inputFile = new FileInputStream(path);
        } catch (FileNotFoundException e) {}
        Image image = new Image(path);
        primaryStage.getIcons().add(image);
        primaryStage.setScene(new Scene(new StartPage(), 480, 480));
        primaryStage.show();
        //handler for the start button pressed event
        StartPage.getStart().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                int nrOfGhosts = Integer.parseInt(StartPage.getNrOfGhostsComboBox().getValue());
                String speedSelected = StartPage.getGhostSpeedComboBox().getValue();
                startGame(nrOfGhosts, speedSelected);
            }
        });
    }


    /**
     * method that represents the entering point of a game
     * @param nrOfGhosts number of ghost within this game
     * @param speed ghost speed
     */
    public void startGame(int nrOfGhosts, String speed) {
        matrix = GridModel.getMatrix(true);
        Grid grid = new Grid(30, matrix);
        numberOfFoodCells = grid.getNumberOfFoodCells();
        game = new GameView(grid, 480, 630);
        pacMan = PacMan.getPacMan();
        game.setPacManPosition(pacMan.getX(), pacMan.getY());

        ghosts = new ArrayList<>();
        IntStream.range(0, nrOfGhosts).forEach(i -> {
            Ghost g = new Ghost(i, speed);
            g.setListener(this);
            ghosts.add(g);
        });
        game.setGhosts(nrOfGhosts);
        IntStream.range(0, nrOfGhosts).forEach(i -> {
            Ghost g = ghosts.get(i);
            game.setGhostPosition(g);
            g.start();
        });

        setHandleKeyPressed();

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(primaryScreenBounds.getMinX() + (primaryScreenBounds.getWidth() - primaryStage.getWidth()) * 0.5);
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setScene(game);
        primaryStage.show();
    }


    /**
     * method that defines handling the interaction with keys
     */
    private void setHandleKeyPressed() {
        game.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int prevX = pacMan.getX();
                int prevY = pacMan.getY();
                switch (event.getCode()) {
                    case UP:
                        if (matrix.get(prevY-1).get(prevX) != 1 && matrix.get(prevY-1).get(prevX) != 4) {
                            game.resetLeftCell(prevX, prevY);
                            pacMan.changePosition(prevX, prevY - 1);
                            game.setPacManPosition(prevX, prevY - 1);
                        }
                        break;
                    case DOWN:
                        if (matrix.get(prevY+1).get(prevX) != 1 && matrix.get(prevY+1).get(prevX) != 4) {
                            game.resetLeftCell(prevX, prevY);
                            pacMan.changePosition(prevX, prevY + 1);
                            game.setPacManPosition(prevX, prevY + 1);
                        }
                        break;
                    case LEFT:
                        if (matrix.get(prevY).get(prevX - 1) != 1 && matrix.get(prevY).get(prevX-1) != 4) {
                            game.resetLeftCell(prevX, prevY);
                            pacMan.changePosition(prevX - 1, prevY);
                            game.setPacManPosition(prevX - 1, prevY);
                        }
                        break;
                    case RIGHT:
                        if (matrix.get(prevY).get(prevX + 1) != 1 && matrix.get(prevY).get(prevX+1) != 4) {
                            game.resetLeftCell(prevX, prevY);
                            pacMan.changePosition(prevX + 1, prevY);
                            game.setPacManPosition(prevX + 1, prevY);
                        }
                        break;
                }
                //decrement number of food cells if one was passed
                if (matrix.get(prevY).get(prevX) == 0) {
                    numberOfFoodCells--;
                    matrix.get(prevY).set(prevX, 2);
                }

                //check if there are any food cells left
                if (numberOfFoodCells == 1 && matrix.get(pacMan.getY()).get(pacMan.getX()) == 0 ||
                    numberOfFoodCells == 0) {
                    gameWon();
                }
            }
        });
    }


    /**
     * method that moves a ghost on the board
     * @param prevX previous x coordinate of ghost
     * @param prevY previous y coordinate of ghost
     * @param ghost ghost object to move contains the new coordinates
     */
    @Override
    public synchronized void ghostMoved(int prevX, int prevY, Ghost ghost) {
        int tileValue = matrix.get(prevY).get(prevX);
        game.resetLeftCellByGhost(prevX, prevY, tileValue);
        game.setGhostPosition(ghost);
    }


    /**
     * method that terminates game in case of losing
     * will be called from a Ghost Thread if PacMan and the ghost have the same coordinates
     */
    @Override
    public void collisionDetected() {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                showEndOfGame(false);
            }
        });
        stopGhosts();
    }


    /**
     * method that terminates game in case of winning
     */
    private void gameWon() {
        showEndOfGame(true);
        stopGhosts();
    }


    /**
     * method that stops all running ghost threads
     */
    private void stopGhosts() {
        ghosts.forEach(ghost -> {
            ghost.stop();
        });
    }


    /**
     * method that displays a window at the end of each game
     * @param won boolean that will define how the window looks depending on game outcome
     */
    private void showEndOfGame(boolean won) {
        VBox box = new VBox(20);
        box.setAlignment(Pos.BOTTOM_CENTER);
        String path = "";
        if (won)
            path = "/images/happy.png";
        else
            path = "/images/sad.jpg";
        Image image = new Image(path);
        ImagePattern pattern = new ImagePattern(image);
        BackgroundImage bgImg = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        box.setBackground(new Background(bgImg));

        Button restart = new Button("Play again");
        restart.setBackground(new Background(new BackgroundFill(Color.valueOf("#00A3E8"), new CornerRadii(5), Insets.EMPTY)));

        box.setPadding(new Insets(0,0,20,0));
        box.getChildren().add(restart);
        Scene newGame = new Scene(box, 300, 300);
        Stage newStage = new Stage();
        if (won)
            newStage.setTitle("You won");
        else
            newStage.setTitle("Game over");

        String iconPath = "/images/minion.png";
        Image icon = new Image(iconPath);
        newStage.getIcons().add(icon);

        //event handler for restart button pressed
        restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                launch();
                newStage.close();
            }
        });

        newStage.setScene(newGame);
        newStage.show();
    }
}
