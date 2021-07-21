package edu.buet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
// import java.util.*;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static PlayerList playerList = new PlayerList();
    private static NetworkUtil networkUtil = new NetworkUtil();
    // private static Club clientClub = new Club();
    private static UserMode userMode = UserMode.NONE;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Football Player Database System");
        scene = new Scene(loadFXML("login"), 1280, 720);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    static PlayerList getPlayerList() {
        return playerList;
    }

    static void setPlayerList (PlayerList playerList) {
        App.playerList = playerList;
    }

    static NetworkUtil getNetworkUtil() {
        return networkUtil;
    }

    static UserMode getUserMode() {
        return userMode;
    }

    static void setUserMode(UserMode userMode) {
        App.userMode = userMode;
    }
    // public static Club getClientClub() {
    //     return clientClub;
    // }

    // public static void setClientClub (Club clientClub) {
    //     App.clientClub = clientClub;
    // }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws Exception {

        try {
            networkUtil = new NetworkUtil("127.0.0.1", 33333);

        } catch (Exception e) {
            System.out.println(e);
        }
        launch(args);
    }

}