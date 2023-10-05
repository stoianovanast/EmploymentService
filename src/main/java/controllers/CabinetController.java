package controllers;
import classes.Registration;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class CabinetController extends Controller{
    public  static String NAMEFILE ="cabinet.fxml";

    @FXML private Button accountButton, backButton, searchButton, createVacancyButton;
    @FXML
    void initialize(Registration registration) {
        setButtonActions(registration);
    }
    private void setButtonActions(Registration registration) {
        accountButton.setOnAction(event -> {
            registration.newAccount();
            closeWindow(accountButton);
            open(AccountController.NAMEFILE, AccountController.class, registration, AccountController.OPEN_WINDOW_PERSONAL_DATA);
        });
        backButton.setOnAction(event -> {
            closeWindow(backButton);
            open(AuthController.NAMEFILE, AuthController.class);
        });
        createVacancyButton.setOnAction(event -> {
            registration.newVacancy();
            closeWindow(createVacancyButton);
            open(EmployerController.NAMEFILE, EmployerController.class, registration, EmployerController.OPEN_WINDOW_PERSONAL_INFO);
        });
        searchButton.setOnAction(event -> {
            closeWindow(searchButton);
            open(SearchController.NAMEFILE, SearchController.class, registration, SearchController.OPEN_TABLE_ACCOUNT, null);
        });
    }
}
