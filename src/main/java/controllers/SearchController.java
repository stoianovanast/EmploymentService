package controllers;

import classes.Registration;
import database.SQLQueries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchController extends Controller {
    public static final String NAMEFILE = "search.fxml";
    private ObservableList<TableController> accountList, vacancyList;

    @FXML private TableColumn<TableController, String> pibColumnAccount, dateColumnAccount, phoneColumnAccount, seriaPassportColumnAccount, specialtyColumnAccount,
            pibColumnVacancy, nameCompanyColumnVacancy, phoneColumnVacancy, positionColumnVacancy, skillsColumnVacancy, specialtyColumnVacancy;
    @FXML private Button goToVacancyButton, backToCabinetButton, deleteAccountButton, deleteVacancyButton,
            goToAccountButton, searchVacancyButton;
    @FXML private Label titleLabel;
    @FXML private TableColumn<TableController, Integer> numberPassportColumnAccount;
    @FXML private TableView<TableController> tableAccount, tableVacancy;
    @FXML private TableColumn<TableController, Float> salaryColumnVacancy;

    public static final int OPEN_TABLE_ACCOUNT = 1;
    public static final int OPEN_TABLE_VACANCY = 2;
    public static final int OPEN_TABLE_SEARCH_RESULTS = 3;

    @FXML
    void initialize(Registration registration, int windowType, TableController tableController) {
        setVisibleFields(false, goToVacancyButton, tableAccount, deleteAccountButton, deleteVacancyButton, goToAccountButton, tableVacancy, searchVacancyButton);

        if (windowType == OPEN_TABLE_ACCOUNT) {
            initializeAccountTable(registration);
        } else if (windowType == OPEN_TABLE_VACANCY) {
            initializeVacancyTable(registration);
        } else if (windowType == OPEN_TABLE_SEARCH_RESULTS) {
            initializeSearchResults(registration, tableController);
        }
        setupCommonEventHandlers(registration);
    }

    private void initializeAccountTable(Registration registration) {
        accountList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = databaseHandler.getDBConnection().prepareStatement(SQLQueries.ACCOUNT_DETAILS);
            ps.setInt(1, registration.getEmployService().getId_center());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                accountList.add(new TableController(
                        rs.getString(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setVisibleFields(true, goToVacancyButton, tableAccount, deleteAccountButton, searchVacancyButton);
        searchVacancyButton.setDisable(false);

        tableAccount.setItems(accountList);
        pibColumnAccount.setCellValueFactory(new PropertyValueFactory<>("PIBAccount"));
        numberPassportColumnAccount.setCellValueFactory(new PropertyValueFactory<>("numberPassport"));
        seriaPassportColumnAccount.setCellValueFactory(new PropertyValueFactory<>("seriaPassport"));
        phoneColumnAccount.setCellValueFactory(new PropertyValueFactory<>("phone"));
        specialtyColumnAccount.setCellValueFactory(new PropertyValueFactory<>("nameSpecialty"));
        dateColumnAccount.setCellValueFactory(new PropertyValueFactory<>("date"));

        titleLabel.setText("Дані про безробітних");
    }

    private void initializeVacancyTable(Registration registration) {
        vacancyList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = databaseHandler.getDBConnection().prepareStatement(SQLQueries.VACANCY_DETAILS);
            ps.setInt(1, registration.getEmployService().getId_center());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                vacancyList.add(new TableController(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getFloat(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setVisibleFields(true, deleteVacancyButton, goToAccountButton, tableVacancy, searchVacancyButton);
        searchVacancyButton.setDisable(true);

        tableVacancy.setItems(vacancyList);
        positionColumnVacancy.setCellValueFactory(new PropertyValueFactory<>("namePosition"));
        specialtyColumnVacancy.setCellValueFactory(new PropertyValueFactory<>("nameSpecialty"));
        salaryColumnVacancy.setCellValueFactory(new PropertyValueFactory<>("salary"));
        skillsColumnVacancy.setCellValueFactory(new PropertyValueFactory<>("skills"));
        nameCompanyColumnVacancy.setCellValueFactory(new PropertyValueFactory<>("nameEmployer"));
        pibColumnVacancy.setCellValueFactory(new PropertyValueFactory<>("PIBAccount"));
        phoneColumnVacancy.setCellValueFactory(new PropertyValueFactory<>("phone"));

        titleLabel.setText("Дані про вакансії");
    }

    private void initializeSearchResults(Registration registration, TableController tableController) {

        searchVacancyButton.setDisable(true);
        setVisibleFields(true, deleteVacancyButton, goToAccountButton, tableVacancy, searchVacancyButton);

        ObservableList<TableController> searchResults = registration.vacancySearch(tableController, registration.getEmployService().getId_center());

        tableVacancy.setItems(searchResults);
        positionColumnVacancy.setCellValueFactory(new PropertyValueFactory<>("namePosition"));
        specialtyColumnVacancy.setCellValueFactory(new PropertyValueFactory<>("nameSpecialty"));
        salaryColumnVacancy.setCellValueFactory(new PropertyValueFactory<>("zp"));
        skillsColumnVacancy.setCellValueFactory(new PropertyValueFactory<>("skills"));
        nameCompanyColumnVacancy.setCellValueFactory(new PropertyValueFactory<>("nameEmployer"));
        pibColumnVacancy.setCellValueFactory(new PropertyValueFactory<>("PIB"));
        phoneColumnVacancy.setCellValueFactory(new PropertyValueFactory<>("phone"));
        titleLabel.setText("Знайдені вакансії");
    }

    private void setupCommonEventHandlers(Registration registration) {
        backToCabinetButton.setOnAction(event -> {
            closeWindow(backToCabinetButton);
            open(CabinetController.NAMEFILE, CabinetController.class, registration);
        });

        goToAccountButton.setOnAction(event -> {
            closeWindow(goToAccountButton);
            open(NAMEFILE, SearchController.class, registration, OPEN_TABLE_ACCOUNT, null);
        });

        goToVacancyButton.setOnAction(event -> {
            closeWindow(goToVacancyButton);
            open(NAMEFILE, SearchController.class, registration, OPEN_TABLE_VACANCY, null);
        });

        searchVacancyButton.setOnAction(event -> {
            TableController selected = tableAccount.getSelectionModel().getSelectedItem();
            if (selected != null) {
                closeWindow(searchVacancyButton);
                open(NAMEFILE, SearchController.class, registration, OPEN_TABLE_SEARCH_RESULTS, selected);
            } else {
                showAlert("Помилка", "Оберіть безробітного для того, щоб здійснити пошук роботи", Alert.AlertType.ERROR);
            }
        });

        deleteAccountButton.setOnAction(event -> {
            handleDeleteAccount(registration);
        });

        deleteVacancyButton.setOnAction(event -> {
            handleDeleteVacancy(registration);
        });
    }

    private void handleDeleteAccount(Registration registration) {
        TableController selected = tableAccount.getSelectionModel().getSelectedItem();
        if (selected != null) {
            registration.deleteAccount(selected.getNumberPassport(), selected.getSeriaPassport());
            showAlert("Успіх", "Безробітного було успішно видалено", Alert.AlertType.INFORMATION);
            tableAccount.getItems().remove(selected);
        } else {
            showAlert("Помилка", "Оберіть безробітного для того, щоб зняти його з обліку", Alert.AlertType.ERROR);
        }
    }

    private void handleDeleteVacancy(Registration registration) {
        TableController selected = tableVacancy.getSelectionModel().getSelectedItem();
        if (selected != null) {
            registration.deleteVacancy(selected.getNameEmployer(), selected.getNamePosition());
            showAlert("Успіх", "Вакансію було успішно видалено", Alert.AlertType.INFORMATION);
            tableVacancy.getItems().remove(selected);
        } else {
            showAlert("Помилка", "Оберіть вакансію для того, щоб зняти її з обліку", Alert.AlertType.ERROR);
        }
    }
}
