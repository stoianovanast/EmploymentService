package controllers;

import classes.Passport;
import classes.Registration;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AccountController extends Controller{
    public  static String NAMEFILE = "accounting.fxml";

    @FXML private Button backToPersonalDataButton, backToCabinetButton, backToAddressButton,
            continueToAddressButton, continueToAdditionalInfoButton, continueToSaveNewAccountButton;
    @FXML private TextField cityTextField, fatherNameTextField, flatTextField, homeTextField, surnameTextField,
            nameTextField, numberPassportTextField, phoneTextField, seriaPasportTextField, streetTextField;
    @FXML private Label titleLabel;
    @FXML private ChoiceBox<String> lastJobChoice, reasonForDismissalChoice, specialtyChoice;

    public static int OPEN_WINDOW_PERSONAL_DATA = 1;
    public static int OPEN_WINDOW_ADDRESS = 2;
    public static int OPEN_WINDOW_ADDITIONAL_INFO = 3;
    @FXML
    void initialize(Registration registration, int openWindow){

        setVisibleFields(false, backToPersonalDataButton, backToCabinetButton, backToAddressButton, cityTextField, continueToAddressButton, continueToAdditionalInfoButton, continueToSaveNewAccountButton,
                fatherNameTextField, flatTextField, homeTextField, lastJobChoice, surnameTextField, nameTextField, numberPassportTextField,
                reasonForDismissalChoice, seriaPasportTextField, phoneTextField, specialtyChoice, streetTextField);

        setItemsChoiceBox();

        openinigWindows(openWindow);

        setButtonActions(registration);
    }
    private void openinigWindows(int openWindow) {
        titleLabel.setText("Введіть дані безробітного");
        if (openWindow == OPEN_WINDOW_PERSONAL_DATA) {
            setVisibleFields(true, surnameTextField, nameTextField, fatherNameTextField, numberPassportTextField, seriaPasportTextField, backToCabinetButton, continueToAddressButton);
            setFieldsToFront(surnameTextField, nameTextField, fatherNameTextField, numberPassportTextField, seriaPasportTextField);
        } else if (openWindow == OPEN_WINDOW_ADDRESS) {
            setVisibleFields(true, cityTextField, flatTextField, homeTextField, streetTextField, backToPersonalDataButton, continueToAdditionalInfoButton);
            titleLabel.setText("Введіть адресу, якщо вона є");
            setFieldsToFront(cityTextField, flatTextField, homeTextField, streetTextField);
        } else if (openWindow == OPEN_WINDOW_ADDITIONAL_INFO) {
            setVisibleFields(true, lastJobChoice, reasonForDismissalChoice, phoneTextField, specialtyChoice, backToAddressButton, continueToSaveNewAccountButton);
            setFieldsToFront(lastJobChoice, reasonForDismissalChoice, phoneTextField, specialtyChoice, backToAddressButton);
        }
    }
    private void setItemsChoiceBox() {
        reasonForDismissalChoice.setItems(FXCollections.observableArrayList("За власним бажанням", "За згодою сторін"));
        reasonForDismissalChoice.setValue("За власним бажанням");

        final String[] AVAILABLE_VALUES = databaseHandler.getEmployer().toArray(new String[0]);
        final String[] AVAILABLE_VALUES2 = databaseHandler.getSpecialty().toArray(new String[0]);

        specialtyChoice.setItems(FXCollections.observableArrayList(AVAILABLE_VALUES2));
        specialtyChoice.setValue(AVAILABLE_VALUES2[0]);

        lastJobChoice.setItems(FXCollections.observableArrayList(AVAILABLE_VALUES));
        lastJobChoice.setValue(AVAILABLE_VALUES[0]);
    }
    private void setButtonActions(Registration registration) {
        backToCabinetButton.setOnAction(event -> {
            closeWindow(backToCabinetButton);
            open(CabinetController.NAMEFILE, CabinetController.class, registration);
        });

        backToPersonalDataButton.setOnAction(event -> {
            closeWindow(backToPersonalDataButton);
            open(NAMEFILE, AccountController.class, registration, OPEN_WINDOW_PERSONAL_DATA);
        });

        backToAddressButton.setOnAction(event -> {
            closeWindow(backToAddressButton);
            open(NAMEFILE, AccountController.class, registration, OPEN_WINDOW_ADDRESS);
        });

        continueToAddressButton.setOnAction(event -> setContinueToAddressAction(registration));
        continueToAdditionalInfoButton.setOnAction(event -> setContinueToAdditionalInfoAction(registration));
        continueToSaveNewAccountButton.setOnAction(event -> setContinueToSaveNewAccountAction(registration));
    }
    private void setContinueToAddressAction(Registration registration) {
        if (isBasicInfoValid()) {
            String fullName = surnameTextField.getText() + " " + nameTextField.getText() + " " + fatherNameTextField.getText();
            Passport passport = new Passport(Integer.parseInt(numberPassportTextField.getText()), seriaPasportTextField.getText());
            if (!registration.isExistence(fullName, passport)) {
               open(AccountController.NAMEFILE, AccountController.class, registration, OPEN_WINDOW_ADDRESS);
            } else {
                showAlert("Помилка", "Ця людина вже зареєстрована", Alert.AlertType.ERROR);
                closeWindow(continueToAddressButton);
                open(CabinetController.NAMEFILE, CabinetController.class, registration);
            }
        } else {
            showAlert("Помилка", "Ви ввели не всі дані", Alert.AlertType.ERROR);
        }
    }
    private void setContinueToAdditionalInfoAction(Registration registration) {
        if (isAddressValid()) {
            String address = cityTextField.getText() + " " + streetTextField.getText() + " " + homeTextField.getText() + " " + flatTextField.getText();
            closeWindow(continueToAdditionalInfoButton);
            if (!registration.isAddress(address)) {
                showAlert("Помилка", "Безробітний має звернутися до іншого відділу", Alert.AlertType.ERROR);
               open(CabinetController.NAMEFILE, CabinetController.class, registration);
            } else {
                open(AccountController.NAMEFILE, AccountController.class, registration, OPEN_WINDOW_ADDITIONAL_INFO);
            }
        } else {
            showAlert("Помилка", "Адреса не є повною", Alert.AlertType.ERROR);
        }
    }
    private void setContinueToSaveNewAccountAction(Registration registration) {
        if (isSaveNewAccountValid()) {
            String phoneText = phoneTextField.getText();
            registration.setDate(lastJobChoice.getValue(), reasonForDismissalChoice.getValue(), specialtyChoice.getValue(), phoneText);
            registration.saveAccount(registration.getAccount());
            showAlert("Успіх", "Безробітного зареєстровано", Alert.AlertType.CONFIRMATION);
            closeWindow(continueToSaveNewAccountButton);
            open(CabinetController.NAMEFILE, CabinetController.class, registration);
        } else {
            showAlert("Помилка", "Ви ввели не всі дані", Alert.AlertType.ERROR);
        }
    }
    private boolean isAddressValid() {
        return !cityTextField.getText().isEmpty() && !streetTextField.getText().isEmpty() && !homeTextField.getText().isEmpty();
    }
    private boolean isSaveNewAccountValid() {
        return !lastJobChoice.getValue().isEmpty() && !reasonForDismissalChoice.getValue().isEmpty();
    }
    private boolean isBasicInfoValid() {
        return !surnameTextField.getText().isEmpty() && !nameTextField.getText().isEmpty() &&
                !fatherNameTextField.getText().isEmpty() && !numberPassportTextField.getText().isEmpty();
    }
}
