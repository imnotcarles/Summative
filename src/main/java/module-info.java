module summative {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires javafx.graphics;

    opens summative to javafx.fxml;

    exports summative;
}