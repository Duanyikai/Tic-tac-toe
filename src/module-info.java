module application.controller {
    requires javafx.controls;
    requires javafx.fxml;


    opens application.controller to javafx.fxml;
    exports application.controller;
}