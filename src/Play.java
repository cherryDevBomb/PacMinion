import Controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

public class Play extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Controller ctrl = new Controller(primaryStage);
        ctrl.launch();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
