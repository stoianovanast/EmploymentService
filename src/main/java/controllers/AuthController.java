package controllers;

import classes.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthController extends Controller{
    public  static String NAMEFILE ="auth.fxml";

    @FXML private Button authLogInButton;
    @FXML private TextField authLoginTextField;
    @FXML private PasswordField authPasswordField;

    @FXML
    void initialize() {
        setButtonActions();
    }
    private void setButtonActions() {
        authLogInButton.setOnAction(event -> setAuthLogInButton());
    }
    private void setAuthLogInButton(){
        if (databaseHandler.enter(authLoginTextField.getText(), authPasswordField.getText()) != null) {
            closeWindow(authLogInButton);
            EmployService employService = databaseHandler.enter(authLoginTextField.getText(), authPasswordField.getText());
            Registration registration = new Registration(new Account(), new AddressMap(databaseHandler.createAddressList(employService.getId_center())),
                    new AllAccount(databaseHandler.createListAllAccounting()), employService);
            open(CabinetController.NAMEFILE, CabinetController.class, registration);
        } else {
            showAlert("Помилка", "Неправильний логін чи пароль", Alert.AlertType.ERROR);
        }
    }
}
