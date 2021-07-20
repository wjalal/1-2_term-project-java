module edu.buet {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;

    opens edu.buet to javafx.fxml;
    exports edu.buet;
}