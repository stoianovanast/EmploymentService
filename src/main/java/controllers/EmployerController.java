package controllers;

import classes.Registration;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EmployerController extends Controller{
    public  static String NAMEFILE ="employer.fxml";

    @FXML private Button addAnotherVacancyButton, backToCabinetButton, createVacancyButton, saveVacancyButton;
    @FXML private TextField fatherNameTextField, lastNameTextField, nameTextField, phoneTextField,
            positionTextField,  salaryTextField;
    @FXML private TextArea skillTextArea;
    @FXML private Label titleLabel;
    @FXML private ChoiceBox<String> companyChoice, specialtyChoice;

    public static final int OPEN_WINDOW_PERSONAL_INFO = 1;
    public static final int OPEN_WINDOW_VACANCY = 2;
    @FXML
    void initialize(Registration registration, int openWindow){
        initChoiceBoxes(registration);

        if(openWindow == OPEN_WINDOW_PERSONAL_INFO){
            setVisibleFields(true, lastNameTextField, nameTextField, fatherNameTextField, backToCabinetButton, phoneTextField, companyChoice, createVacancyButton);
            titleLabel.setText("Введіть дані роботодавця");
            setFieldsToFront(lastNameTextField, nameTextField, fatherNameTextField, backToCabinetButton, createVacancyButton, companyChoice);
        }
        if(openWindow == OPEN_WINDOW_VACANCY){
            setVisibleFields(true, positionTextField, specialtyChoice, salaryTextField, skillTextArea, addAnotherVacancyButton, saveVacancyButton);
            titleLabel.setText("Введіть дані вакансії");
            setFieldsToFront(positionTextField, specialtyChoice, salaryTextField, skillTextArea, addAnotherVacancyButton, saveVacancyButton);
        }
        setButtonAction(registration);
    }
    private void setButtonAction(Registration registration) {
        backToCabinetButton.setOnAction(event -> {
            closeWindow(backToCabinetButton);
            open(CabinetController.NAMEFILE, CabinetController.class, registration);
        });

        createVacancyButton.setOnAction(event -> handlePersonalInfoRegistration(registration));
        addAnotherVacancyButton.setOnAction(event -> handleVacancyRegistration(registration));
        saveVacancyButton.setOnAction(event -> handleVacancyRegistrationAndNavigate(registration));
    }
    private void initChoiceBoxes(Registration registration) {
        final String[] AVAILABLE_VALUES2 = databaseHandler.getSpecialty().toArray(new String[0]);
        final String[] AVAILABLE_VALUES = databaseHandler.getEm(registration.getEmployService().getId_center()).toArray(new String[0]);

        companyChoice.getItems().addAll(AVAILABLE_VALUES);
        companyChoice.setValue(AVAILABLE_VALUES[0]);

        specialtyChoice.getItems().addAll(AVAILABLE_VALUES2);
        specialtyChoice.setValue(AVAILABLE_VALUES2[0]);
    }
    private void handlePersonalInfoRegistration(Registration registration) {
        if (validatePersonalInfoFields()) {
            registration.getVacancy().setPIBEmployer(lastNameTextField.getText() + " " + nameTextField.getText() + " " + fatherNameTextField.getText());
            registration.getVacancy().setEmployer(databaseHandler.getIdEmployer(companyChoice.getValue()));
            registration.getVacancy().setPhoneEmployer(phoneTextField.getText());
            closeWindow(createVacancyButton);
            open(NAMEFILE, EmployerController.class, registration, OPEN_WINDOW_VACANCY);
        } else {
            showAlert("Помилка", "Ви ввели не всі дані", Alert.AlertType.ERROR);
        }
    }
    private void handleVacancyRegistration(Registration registration) {
        if (validateVacancyFields()) {
            registration.setVacancy(positionTextField.getText(), specialtyChoice.getValue(),
                    Float.parseFloat(salaryTextField.getText()), skillTextArea.getText());
            registration.saveVacancy(registration.getVacancy());
            closeWindow(addAnotherVacancyButton);
            registration.newVacancy();
            open(NAMEFILE, EmployerController.class, registration, OPEN_WINDOW_VACANCY);
        } else {
            showAlert("Помилка", "Ви ввели не всі дані", Alert.AlertType.ERROR);
        }
    }
    private void handleVacancyRegistrationAndNavigate(Registration registration) {
        if (validateVacancyFields()) {
            registration.setVacancy(positionTextField.getText(), specialtyChoice.getValue(),
                    Float.parseFloat(salaryTextField.getText()), skillTextArea.getText());
            registration.saveVacancy(registration.getVacancy());
            closeWindow(saveVacancyButton);
            open(CabinetController.NAMEFILE, CabinetController.class, registration);
        } else {
            showAlert("Помилка", "Ви ввели не всі дані", Alert.AlertType.ERROR);
        }
    }
    private boolean validatePersonalInfoFields() {
        return !lastNameTextField.getText().isEmpty() && !nameTextField.getText().isEmpty() && !fatherNameTextField.getText().isEmpty()
                && !phoneTextField.getText().isEmpty() && companyChoice.getValue() != null;
    }
    private boolean validateVacancyFields() {
        return !positionTextField.getText().isEmpty() && specialtyChoice.getValue() != null
                && !salaryTextField.getText().isEmpty() && !skillTextArea.getText().isEmpty();
    }
}


