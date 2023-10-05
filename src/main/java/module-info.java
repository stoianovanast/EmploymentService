module com.example.bd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires itextpdf;


    opens classes to javafx.fxml;
    exports classes;
    exports controllers;
    opens controllers to javafx.fxml;
    exports database;
    opens database to javafx.fxml;


}