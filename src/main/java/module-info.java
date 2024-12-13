module com.example.supervisorssystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.supervisorssystem.Controller to javafx.fxml;
    exports com.example.supervisorssystem;
}