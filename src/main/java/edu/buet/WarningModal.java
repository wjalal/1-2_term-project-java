package edu.buet;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WarningModal {

    public static void display (String title, String warning) {

        Stage window = new Stage();
        window.setResizable(false);
        Button closeButton = new Button("Close");

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        Label message = new Label();
        message.setText(warning);
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(message, closeButton);

        Scene scene = new Scene(layout);
        if ( !(App.getTheme().equals("Light")) )
            scene.getStylesheets().add(App.class.getResource("styles/" + App.getTheme() +  ".css").toExternalForm());
        else scene.getStylesheets().clear();
        window.setScene(scene);
        window.showAndWait();
    }
    
}
