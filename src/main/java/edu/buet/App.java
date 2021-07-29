package edu.buet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.stage.Stage;
// import java.util.*;
import java.io.IOException;
import javafx.scene.image.*;

public class App extends Application {

    private static Stage mainStage;
    private static Scene scene;
    private static PlayerList playerList = new PlayerList();
    private static NetworkUtil networkUtil = new NetworkUtil();
    private static ReadThreadClient rtc;
    // private static Club clientClub = new Club();
    private static UserMode userMode = UserMode.NONE;
    private static boolean uiUpdate = false;
    private static int tabIdx1 = 0, tabIdx2 = 0;
    private static String theme = "Dark";
    private static PlayerDisplayController pDispP, pDispC;
    private static Label balanceLabel;
    private static String serverAddress = "127.0.0.1";
    private static int serverPort = 6969;

    static String getServerAddress() {
        return serverAddress;
    }

    static int getServerPort() {
        return serverPort;
    }

    static void setBalanceLabel(Label balanceLabel) {
        App.balanceLabel = balanceLabel;
    }

    static Label getBalanceLabel() {
        return balanceLabel;
    }

    public static void setpDispC(PlayerDisplayController pDispC) {
        App.pDispC = pDispC;
    }

    public static void setpDispP(PlayerDisplayController pDispP) {
        App.pDispP = pDispP;
    }

    public static PlayerDisplayController getpDispC() {
        return pDispC;
    }

    public static PlayerDisplayController getpDispP() {
        return pDispP;
    }

    @Override
    public void start(Stage stage) throws IOException {
        //Application.setUserAgentStylesheet(getClass().getResource("styles/dark.css").toExternalForm());
        // StyleManager.getInstance().addUserAgentStylesheet("example.css");
        stage.setTitle("Football Club Database System");
        stage.setResizable(false);
        // scene.getStylesheets().add(App.class.getResource("styles/dark.css").toExternalForm());
        scene = new Scene(loadFXML("login"), 1280, 720);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("icons/app.png")));
        stage.setOnCloseRequest(e -> {
            e.consume();
            try {
                // networkUtil.closeConnection();
                if (ConfirmationModal.display("Exit Confirmation", "Are you sure you want to exit the program?")) {
                    stage.close();
                    System.exit(0);
                }
            } catch (Exception x) {
                System.out.println("sorry");
                x.printStackTrace();
            }
        });
        stage.setScene(scene);
        mainStage = stage;
        stage.show();
        if (uiUpdate == true) {
            WarningModal.display("Error", "Failed to connect to server.");
            System.exit(0);
        }
        
    }

    static void setRoot(String fxml) throws IOException {
        if ( !(App.getTheme().equals("Light")) ) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(App.class.getResource("styles/" + App.getTheme() +  ".css").toExternalForm());
        } else scene.getStylesheets().clear();
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

    static void setNetworkUtil(NetworkUtil networkUtil) {
        App.networkUtil = networkUtil;
    }

    static UserMode getUserMode() {
        return userMode;
    }

    static void setUserMode(UserMode userMode) {
        App.userMode = userMode;
    }

    static void setRtc(ReadThreadClient rtc) {
        App.rtc = rtc;
    }

    static ReadThreadClient getRtc() {
        return rtc;
    }

    static void setUiUpdate (boolean uiUpdate) {
        App.uiUpdate = uiUpdate;
    }

    static boolean getUiUpdate() {
        return uiUpdate;
    }
    
    static Stage getMainStage() {
        return mainStage;
    }

    static int getTabIdx1() {
        return tabIdx1;
    }

    static int getTabIdx2() {
        return tabIdx2;
    }

    static void setTabIdx1(int tabIdx1) {
        App.tabIdx1 = tabIdx1;
    }

    static void setTabIdx2(int tabIdx2) {
        App.tabIdx2 = tabIdx2;
    }

    static String getTheme() {
        return theme;
    }

    static void setTheme(String theme) {
        App.theme = theme;
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setLocation(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws Exception {

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("--help") || args[0].equalsIgnoreCase("-h")) {
                System.out.println("\nIf the client is launched without arguments, it will try to connect");
                System.out.println("to localhost (127.0.0.1) assuming that the server is running on the same");
                System.out.println("device. The default port is 6969. If only one argument is put, it will be");
                System.out.println("assumed as the server address and the default port 6969 will be used. Eg. :");
                System.out.println("\n\tjava -jar client.jar 192.168.0.101");
                System.out.println("\n(Client will try to connect to 192.168.0.101 at port 6969)");
                System.out.println("\n\nA second argument can be used to spcify port after server address:");
                System.out.println("\n\tjava -jar client.jar 192.168.0.101 3000");
                System.out.println("\n(Client will try to connect to 192.168.0.101 at port 3000)\n");
                System.exit(0);
            } else {
                App.serverAddress = args[0];
            }
        } else if (args.length == 2) {
            App.serverAddress = args[0];
            App.serverPort = Integer.parseInt(args[1]);
        } else if (args.length != 0) {
            System.out.println("\nInvalid arguments, run with argument --help or -h for help:");
            System.out.println("\n\tjava -jar client.jar --help \n");
            System.exit(0);
        }

        try {
            networkUtil = new NetworkUtil(App.serverAddress, App.serverPort);

        } catch (Exception e) {
            System.out.println(e);
            uiUpdate = true;
        }
        launch(args);
    }

}