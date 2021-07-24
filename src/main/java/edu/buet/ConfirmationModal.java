package edu.buet;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmationModal {

    public static boolean response;

    public static boolean display (String title, String warning) {

        Stage window = new Stage();
        Button yesButton = new Button("Yes");
        Button noButton = new Button("Cancel");

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Label message = new Label();
        message.setText(warning);
        yesButton.setOnAction(e -> {
            window.close();
            response = true;
        });
        noButton.setOnAction(e -> {
            window.close();
            response = false;
        });

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(message, yesButton, noButton);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return response;
    }
    
}
