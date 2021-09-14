module pass {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens pass to javafx.fxml;
    exports pass;
}