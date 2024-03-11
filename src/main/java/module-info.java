module com.example.dietetica {
    requires javafx.controls;
    requires javafx.fxml;
    requires jasperreports;
    requires java.sql;
    requires java.desktop;


    opens com.example.dietetica to javafx.fxml;
    exports com.example.dietetica;
    exports com.example.dietetica.controller;
    opens com.example.dietetica.controller to javafx.fxml;
}