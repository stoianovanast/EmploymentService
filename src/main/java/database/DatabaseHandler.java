package database;

import classes.EmployService;
import classes.Passport;
import controllers.TableController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler {
    private final String connectionString="jdbc:mysql://localhost:3306/service";
    private final String className = "com.mysql.cj.jdbc.Driver";
    private final String user = "root";
    private final String password = "root";
    Connection connection;
    ResultSet resultSet;
    PreparedStatement preparedStatement;
    public Connection getDBConnection() {
        try {
            Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException("Помилка завантаження драйвера",e);
        }
        try {
            connection= DriverManager.getConnection(connectionString, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Помилка з'єднання", e);
        }
        return connection;
    }
    public EmployService enter(String loginSpecialit, String passwordSpecialit){
        EmployService employService = null;
        try {
            preparedStatement = getDBConnection().prepareStatement(SQLQueries.SELECT_SPECIALIST_ID);
            preparedStatement.setString(1, loginSpecialit);
            preparedStatement.setString(2, passwordSpecialit);
            resultSet = preparedStatement.executeQuery();
            int id_specialist = 0;
            if(resultSet.next()){
                id_specialist = resultSet.getInt(1);
            }
            preparedStatement = getDBConnection().prepareStatement(SQLQueries.SELECT_INFO_SPECIALIST);
            preparedStatement.setInt(1, id_specialist);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                employService = new EmployService(resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getString(6) + ", вул. "+ resultSet.getString(7)+
                                ", буд."+ resultSet.getString(8));
                employService.setCity_center(resultSet.getString(5));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employService;
    }
    public ArrayList<Passport> createListAllAccounting(){
        ArrayList<Passport> listAllAccounting = new ArrayList<>();
        try {
            preparedStatement = getDBConnection().prepareStatement(SQLQueries.SELECT_NUMBER_AND_SERIA);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                listAllAccounting.add(new Passport(resultSet.getInt(1), resultSet.getString(2)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  listAllAccounting;
    }
    public ArrayList<String> createAddressList(int id_center){
        ArrayList<String> addressList = new ArrayList<>();
        try {
            preparedStatement = getDBConnection().prepareStatement(SQLQueries.SELECT_CITY);
            preparedStatement.setInt(1, id_center);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                addressList.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  addressList;
    }
    public ArrayList<String> getSpecialty(){
        ArrayList<String> AVAILABLE_VALUES = new ArrayList<>();
        try {
            preparedStatement = getDBConnection().prepareStatement(SQLQueries.SELECT_SPECIALTY_NAME);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) AVAILABLE_VALUES.add(resultSet.getString(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return AVAILABLE_VALUES;
    }
    public ArrayList<String> getEmployer(){
        ArrayList<String> AVAILABLE_VALUES = new ArrayList<>();
        try {
            preparedStatement = getDBConnection().prepareStatement(SQLQueries.SELEC_EMPLOYER_NAME);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) AVAILABLE_VALUES.add(resultSet.getString(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return AVAILABLE_VALUES;
    }
    public ArrayList<String> getEm(int id_center) {
        ArrayList<String> name = new ArrayList<>();
        try {
            preparedStatement = getDBConnection().prepareStatement(SQLQueries.SELECT_EMPLOYER_NAME);
            preparedStatement.setInt(1, id_center);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                name.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return name;
    }
    public int getIdEmployer(String nameCompany) {
        try {
            preparedStatement = getDBConnection().prepareStatement(SQLQueries.SELECT_EMPLOYER_ID);
            preparedStatement.setString(1, nameCompany);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
    public int getIdSpecialty(String nameSpecialty) {
        try {
            preparedStatement = getDBConnection().prepareStatement(SQLQueries.SELECT_SPECIALTY_ID_NUMBER);
            preparedStatement.setString(1, nameSpecialty);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
    public void deleteAccount(int numberPassport, String seriaPassport) {
        try {
            preparedStatement = getDBConnection().prepareStatement(SQLQueries.SELECT_PASSPORT_ID);
            preparedStatement.setInt(1, numberPassport);
            preparedStatement.setString(2, seriaPassport);
            resultSet = preparedStatement.executeQuery();
            int id_passport = 0;
            if(resultSet.next()){
                id_passport = resultSet.getInt(1);
            }
            preparedStatement = getDBConnection().prepareStatement(SQLQueries.SELECT_ACCOUNT_ALL);
            preparedStatement.setInt(1, id_passport);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                PreparedStatement preparedStatement = getDBConnection().prepareStatement(SQLQueries.INSERT_ACCOUNT_AR);
                preparedStatement.setInt(1, resultSet.getInt(1));
                preparedStatement.setString(2, resultSet.getString(2));
                preparedStatement.setInt(3, resultSet.getInt(3));
                preparedStatement.setDate(4, resultSet.getDate(5));
                preparedStatement.setInt(5, resultSet.getInt(9));
                preparedStatement.setInt(6, resultSet.getInt(7));
                preparedStatement.executeUpdate();
            }
            preparedStatement = getDBConnection().prepareStatement(SQLQueries.DELETE_ACCOUNT);
            preparedStatement.setInt(1, id_passport);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void deleteVacancy(String nameEmployer, String namePosition) {
        try {
            preparedStatement = getDBConnection().prepareStatement(SQLQueries. SELECT_EMPLOYER_ID);
            preparedStatement.setString(1, nameEmployer);
            resultSet = preparedStatement.executeQuery();
            int id_employer = 0;
            if(resultSet.next()){
                id_employer = resultSet.getInt(1);
            }

            preparedStatement = getDBConnection().prepareStatement(SQLQueries.DELETE_VACANCY);
            preparedStatement.setString(1, namePosition);
            preparedStatement.setInt(2, id_employer);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ObservableList<TableController> getVacancySearch(TableController tableController, int numberCenter) {
        ObservableList<TableController> vacancySearch = FXCollections.observableArrayList();
        try {
            preparedStatement = getDBConnection().prepareStatement(SQLQueries.SELECT_VACANCY_ALL);
            preparedStatement.setString(1, tableController.getNameSpecialty());
            preparedStatement.setInt(2, numberCenter);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                vacancySearch.add(new TableController(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getFloat(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return vacancySearch;
    }
}





