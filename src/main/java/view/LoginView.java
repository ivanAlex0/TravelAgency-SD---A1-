package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Admin;


public class LoginView extends DIController implements SceneController {

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private Text errorText;

    @FXML
    public void initialize() {
    }

    @FXML
    public void authenticate(ActionEvent actionEvent) {
        errorText.setText("");
        errorText.setVisible(false);
        System.out.println(email.getText() + " " + password.getText());
        user = userController.authenticate(email.getText(), password.getText());

        if (user == null) {
            errorText.setVisible(true);
            errorText.setText("Invalid credentials! Maybe want to");
        } else {
            if (user instanceof Admin) {
                changeScene(actionEvent, "/admin.fxml");
            } else {
                changeScene(actionEvent, "/userPage.fxml");
            }
        }
    }

    @FXML
    public void loadRegister(ActionEvent actionEvent) {
        changeScene(actionEvent, "/register.fxml");
    }
}
