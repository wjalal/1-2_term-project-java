package edu.buet;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NullPlayerListWarning {
    
    public static void display() {

        Stage window = new Stage();
        Button closeButton = new Button("Close");

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("No results");

        Label message = new Label();
        message.setText("Sorry, no players with such attributes found.");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(message, closeButton);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
