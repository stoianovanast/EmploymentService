package controllers;

import classes.HelloApplication;
import classes.Registration;
import database.DatabaseHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.lang.reflect.Method;

public class Controller {
    protected static Stage stage = new Stage();
    protected static Scene scene;
    DatabaseHandler databaseHandler = new DatabaseHandler();
    public static FXMLLoader initFXMLLoader(String fxmlFileName) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFileName));
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fxmlLoader;
    }
    public void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void open(String NAMEFILE, Class<? extends Controller> controllerClass, Registration registration) {
        FXMLLoader fxmlLoader = initFXMLLoader(NAMEFILE);
        Controller controller = fxmlLoader.<Controller>getController();

        try {
            Method initializeMethod = controllerClass.getDeclaredMethod("initialize", Registration.class);
            initializeMethod.setAccessible(true);
            initializeMethod.invoke(controller, registration);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        stage.setScene(scene);
        stage.show();
    }
    public static void open(String NAMEFILE, Class<? extends Controller> controllerClass) {
        FXMLLoader fxmlLoader = initFXMLLoader(NAMEFILE);
        Controller controller = fxmlLoader.<Controller>getController();
        try {
            Method initializeMethod = controllerClass.getDeclaredMethod("initialize");
            initializeMethod.setAccessible(true);
            initializeMethod.invoke(controller);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
        stage.show();
    }
    public static void open(String NAMEFILE, Class<? extends Controller> controllerClass, Registration registration, int someNumber) {
        FXMLLoader fxmlLoader = initFXMLLoader(NAMEFILE);
        Controller controller = fxmlLoader.<Controller>getController();
        try {
            Method initializeMethod = controllerClass.getDeclaredMethod("initialize", Registration.class, int.class);
            initializeMethod.setAccessible(true);
            initializeMethod.invoke(controller, registration, someNumber);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
        stage.show();
    }

    public static void open(String NAMEFILE, Class<? extends Controller> controllerClass, Registration registration,  int win, TableController t) {
        FXMLLoader fxmlLoader = initFXMLLoader(NAMEFILE);
        Controller controller = fxmlLoader.<Controller>getController();
        try {
            Method initializeMethod = controllerClass.getDeclaredMethod("initialize", Registration.class, int.class, TableController.class);
            initializeMethod.setAccessible(true);
            initializeMethod.invoke(controller, registration, win, t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        stage.setScene(scene);
        stage.show();
    }
    public void setVisibleFields(boolean setVisible, Node... fields) {
        for (Node field : fields) {
            field.setVisible(setVisible);
        }
    }
    public void setFieldsToFront(Node... fields) {
        for (Node field : fields) {
            field.toFront();
        }
    }
    public void closeWindow(Button button) {
        button.getScene().getWindow().hide();
    }
}

