package edu.buet;

import java.io.IOException;
import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;

import javafx.scene.control.TextField;
// import javafx.scene.control.Label;
// import javafx.scene.control.ListView;
// import javafx.scene.image.*;



public class LoginController {

    PlayerList playerList = App.getPlayerList();
    NetworkUtil networkUtil = App.getNetworkUtil();

    @FXML private TextField username, password;

    @FXML private void attemptLogin() {
        try {
            System.out.println(username.getText() + "  " + password.getText());
            networkUtil.write( new LoginCredential(username.getText(), password.getText()) );
            Object o = networkUtil.read();
            if ( o instanceof String && ((String)o).equals("NO_CRED_MATCH") )  
                WarningModal.display("Login failed", "Sorry, your credentials did not match any existing club.");
            else {
                App.setUserMode(UserMode.LOGGED_IN);
                App.setPlayerList( (PlayerList) o );
                System.out.println("ok");
                App.setRoot("signed-in");
                App.setRtc(new ReadThreadClient(networkUtil));
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            App.setUserMode(UserMode.NONE);
        }

    }

    @FXML private void attemptGuest() {
        try {
            networkUtil.write( "GUEST_ENTRY" );
            Object o = networkUtil.read();
            if ( o instanceof PlayerList ) {
                App.setUserMode(UserMode.GUEST);
                App.setPlayerList( (PlayerList) o );
                App.setRoot("guest");
            }
        } catch (Exception e) {
            System.out.println(e);
            App.setUserMode(UserMode.NONE);
        }
    }
}