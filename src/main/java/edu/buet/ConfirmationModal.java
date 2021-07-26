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
        window.setResizable(false);
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
        yesButton.setPrefWidth(150);

        noButton.setOnAction(e -> {
            window.close();
            response = false;
        });
        noButton.setPrefWidth(150);

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        // buttons.setPadding(new Insets(20, 20, 20, 20));
        buttons.getChildren().addAll(yesButton, noButton);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(message, buttons);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return response;
    }
    
}
