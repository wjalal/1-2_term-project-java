package edu.buet;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PasswordModal {


    public static String response;

    public static String display () {

        Stage window = new Stage();
        window.setResizable(false);
        PasswordField password = new PasswordField();
        Button okButton = new Button("Ok");

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Password confirmation");

        Label message = new Label();
        message.setText("Please re-enter your password: ");

        okButton.setOnAction(e -> {
            response = password.getText();
            window.close();
        });

        password.setPrefWidth(250);

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        // buttons.setPadding(new Insets(20, 20, 20, 20));
        buttons.getChildren().addAll(password, okButton);

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
