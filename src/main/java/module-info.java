module edu.buet {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.media;
    opens edu.buet to javafx.fxml;
    exports edu.buet;
}