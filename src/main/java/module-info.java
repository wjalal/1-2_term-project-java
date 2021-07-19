module edu.buet {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.buet to javafx.fxml;
    exports edu.buet;
}