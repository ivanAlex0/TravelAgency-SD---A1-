package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public interface SceneController {

    default void changeScene(ActionEvent event, String fxmlFilePath) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFilePath)));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    default void logInfo(Text text, String info) {
        text.setVisible(true);
        text.setText(info);
    }
}
