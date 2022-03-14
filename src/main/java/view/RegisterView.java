package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.enums.PERSIST_RESPONSE;

public class RegisterView extends DIController implements SceneController {

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    private Text errorText;


    @FXML
    public void register(ActionEvent event) {
        String name = email.getText();
        String passwordS = password.getText();
        PERSIST_RESPONSE response = userController.register(name, passwordS);
        if (response == PERSIST_RESPONSE.DUPLICATE_ENTITY) {
            logInfo(errorText, "DUPLICATE MACNACTIAS");
        } else if (response == PERSIST_RESPONSE.INVALID_INPUT) {
            logInfo(errorText, "Some invalid input man");
        } else {
            changeScene(event, "/login.fxml");
        }
    }

    @FXML
    public void goBack(ActionEvent actionEvent) {
        changeScene(actionEvent, "/login.fxml");
    }
}
