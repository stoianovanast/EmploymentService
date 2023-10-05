package classes;

import database.DatabaseHandler;
import controllers.TableController;
import database.SQLQueries;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Registration {
    private Account account;
    private AddressMap addressMap;
    private AllAccount allAccount;
    private  EmployService employService;
    private Vacancy vacancy;
    private DatabaseHandler databaseHandler = new DatabaseHandler();

    public Registration(Account account, AddressMap addressMap, AllAccount allAccount, EmployService employService) {
        this.account = account;
        this.addressMap = addressMap;
        this.allAccount = allAccount;
        this.employService = employService;
    }
    public Vacancy getVacancy() {
        return vacancy;
    }
    public EmployService getEmployService() {
        return employService;
    }
    public Account getAccount() {
        return account;
    }
    public void newAccount(){
        this.account = new Account();
    }
    public void newVacancy(){
        this.vacancy = new Vacancy();
    }
    public boolean isExistence(String PIB, Passport passport){
        boolean resultOfChecking = account.isExistance(allAccount, passport, PIB);
       return resultOfChecking;
    }
    public boolean isAddress(String address){
        boolean resultOfChecking = account.isAddress(address, addressMap);
        return resultOfChecking;
    }
    public void setDate(String lastJob, String reasonForDismissal, String specialty){
        account.setDate(allAccount, employService, lastJob, reasonForDismissal, specialty);
    }
    public void setDate(String lastJob, String reasonForDismissal, String specialty, String phone){
        account.setDate(allAccount, employService, lastJob, reasonForDismissal, specialty, phone);
    }
    public void setVacancy( String position,  String specialty, float salary, String skills){
       vacancy.setVacancy(position,  specialty, salary, skills, databaseHandler);
    }
    public ObservableList<TableController> vacancySearch(TableController table, int numberCenter){
        ObservableList<TableController> vacancySearch = databaseHandler.getVacancySearch(table, numberCenter);
        return vacancySearch;
    }
    public void deleteAccount(int numberPassport, String seriaPassport) {
        databaseHandler.deleteAccount(numberPassport, seriaPassport);
    }
    public void deleteVacancy(String nameEmployer, String namePosition) {
        databaseHandler.deleteVacancy(nameEmployer, namePosition);
    }
    public void saveAccount(Account account) {
        try {
            int passportId = insertPassport(account.getPassport());
            int specialtyId = getSpecialtyId(account.getSpecialty());
            int employerId = getEmployerId(account.getLastJob());
            insertAccount(account, passportId, specialtyId, employerId);
        } catch (SQLException e) {
            throw new RuntimeException("Помилка збереження безробітного",e);
        }
    }
    private int insertPassport(Passport passport) throws SQLException {
        try (PreparedStatement psPassport = databaseHandler.getDBConnection()
                .prepareStatement(SQLQueries.INSERT_PASSPORT, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            psPassport.setInt(1, passport.getNumber());
            psPassport.setString(2, passport.getSeria());
            psPassport.executeUpdate();
            ResultSet generatedKeys = psPassport.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            throw new SQLException("ID паспорта не знайдено");
        }
    }
    private int getSpecialtyId(String specialty) throws SQLException {
        try (PreparedStatement psSelectSpecialty = databaseHandler.getDBConnection()
                .prepareStatement(SQLQueries.SELECT_SPECIALTY_ID)) {
            psSelectSpecialty.setString(1, specialty);
            ResultSet specialtyResult = psSelectSpecialty.executeQuery();
            if (specialtyResult.next()) {
                return specialtyResult.getInt(1);
            }
            throw new SQLException("ID спеціальності не знайдено");
        }
    }
    private int getEmployerId(String employer) throws SQLException {
        try (PreparedStatement psSelectEmployer = databaseHandler.getDBConnection()
                .prepareStatement(SQLQueries.SELECT_EMPLOYER_ID)) {
            psSelectEmployer.setString(1, employer);
            ResultSet employerResult = psSelectEmployer.executeQuery();
            if (employerResult.next()) {
                return employerResult.getInt(1);
            }
            throw new SQLException("ID роботодавця не знайдено");
        }
    }
    private void insertAccount(Account account, int passportId, int specialtyId, int employerId) throws SQLException {
        try (PreparedStatement psAccount = databaseHandler.getDBConnection()
                .prepareStatement(SQLQueries.INSERT_ACCOUNT)) {
            psAccount.setString(1, account.getPIB());
            psAccount.setInt(2, passportId);
            psAccount.setString(3, account.getPhone());
            psAccount.setInt(4, specialtyId);
            psAccount.setString(5, account.getReasonForDismissal());
            psAccount.setInt(6, employerId);
            psAccount.setInt(7, employService.getId_specialist());
            psAccount.executeUpdate();
        }
    }
    public void saveVacancy(Vacancy vacancy) {
       try (PreparedStatement ps = databaseHandler.getDBConnection().prepareStatement(SQLQueries.INSERT_VACANCY)) {
            ps.setInt(1, vacancy.getEmployer());
            ps.setString(2, vacancy.getPIB());
            ps.setString(3, vacancy.getPhoneEmployer());
            ps.setString(4, vacancy.getNamePosition());
            ps.setString(5, vacancy.getSkills());
            ps.setFloat(6, vacancy.getSalary());
            ps.setInt(7, vacancy.getIdSpecialty());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Помилка збереження вакансії", e);
        }
    }
}
