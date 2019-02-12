package View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


/**
 * class that defines the GUI for the start page
 */
public class StartPage extends VBox {

    private static Button start;
    private static ComboBox<String> nrOfGhostsComboBox;
    private static ComboBox<String> ghostSpeedComboBox;

    /**
     * getter for the start button
     * @return start
     */
    public static Button getStart() {
        return start;
    }


    /**
     * getter for the comboBox that counts the number of ghosts
     * @return nrOfGhostsComboBox
     */
    public static ComboBox<String> getNrOfGhostsComboBox() {
        return nrOfGhostsComboBox;
    }


    /**
     * getter for the comboBox that sets the speed
     * @return ghostSpeedComboBox;
     */
    public static ComboBox<String> getGhostSpeedComboBox() {
        return ghostSpeedComboBox;
    }


    /**
     * constructor
     */
    public StartPage() {
        super(10);
        setAlignment(Pos.BASELINE_CENTER);
        setPadding(new Insets(30,150,0,0));

        //set background image
        String path = "/images/cover.jpg";
        Image image = new Image(path);
        BackgroundImage bgImg = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        setBackground(new Background(bgImg));

        //add elements
        Label info = new Label("Choose number of ghosts:");
        info.setFont(Font.font("Alegreya Sans SC", FontWeight.BOLD, 18));

        ObservableList<String> difficultyLevel = FXCollections.observableArrayList("1", "2", "3", "4");
        nrOfGhostsComboBox = new ComboBox<>(difficultyLevel);
        nrOfGhostsComboBox.setBackground(new Background(new BackgroundFill(Color.valueOf("#00A3E8"), new CornerRadii(5), Insets.EMPTY)));
        nrOfGhostsComboBox.setMinWidth(125);

        Label info2 = new Label("Choose ghost speed:");
        info2.setFont(Font.font("Alegreya Sans SC", FontWeight.BOLD, 18));

        ObservableList<String> speed = FXCollections.observableArrayList("Slow", "Medium", "Fast");
        ghostSpeedComboBox = new ComboBox<>(speed);
        ghostSpeedComboBox.setBackground(new Background(new BackgroundFill(Color.valueOf("#00A3E8"), new CornerRadii(5), Insets.EMPTY)));
        ghostSpeedComboBox.setMinWidth(125);

        String buttonImagePath = "images/banana-start.png";
        Image img = new Image(buttonImagePath);
        BackgroundImage buttonImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

        start = new Button("");
        start.setMinSize(125, 52);
        start.setBackground(new Background(buttonImg));

        getChildren().addAll(info, nrOfGhostsComboBox, info2, ghostSpeedComboBox, start);
    }
}
