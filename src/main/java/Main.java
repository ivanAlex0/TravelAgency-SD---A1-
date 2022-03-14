import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS [%4$s] [%2$s] --- %5$s%6$s%n");
    }

    //$2a$10$aoviFj9IdnN6.JJ/ErX6qOIWZnHbFpF6d6xqbEZGAzQju2cJseds2

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/login.fxml")));
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }
}
