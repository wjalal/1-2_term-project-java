package edu.buet;

import java.io.IOException;
import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
// import javafx.scene.control.Label;
// import javafx.scene.control.ListView;
// import javafx.scene.image.*;
import java.io.File;
import java.nio.file.*;
import java.net.URL;

import javafx.scene.control.TextField;
import javafx.stage.FileChooser;



public class LoginController {

    PlayerList playerList = App.getPlayerList();
    NetworkUtil networkUtil = App.getNetworkUtil();

    @FXML private TextField username;
    @FXML private PasswordField password;

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
            e.printStackTrace();
        }
    }

    @FXML private void attemptSignUp() {
        byte[] newClubLogoBytes = null;
        String msg1 = "Are you sure you want to sign up your club with the name " + username.getText();
        if ( ConfirmationModal.display("Confirmation", msg1) ) {
            WarningModal.display("Club Logo", "You will now be prompted to upload your club's logo");
            FileChooser logoChooser = new FileChooser();
            File logoFile = logoChooser.showOpenDialog(App.getMainStage());
            System.out.println(logoFile.toURI().toString());
            try { newClubLogoBytes = Files.readAllBytes(logoFile.toPath());
            } catch (Exception e) { e.printStackTrace();}

            if ( !(PasswordModal.display().equals(password.getText())) ) {
                WarningModal.display("Sign up Failed", "Passwords do not match!");
            } else if (logoFile != null) {
                try {
                    networkUtil.write ( new SignUpRequest ( new LoginCredential (username.getText(), password.getText()), newClubLogoBytes) );
                    Object o = networkUtil.read();
                    if ( o instanceof String && ((String)o).equals("CLUB_PRE-EXIST") )  
                        WarningModal.display("Sign up Failed", "A club with this name already exists");
                    else {
                        App.setUserMode(UserMode.LOGGED_IN);
                        App.setPlayerList( (PlayerList) o );
                        System.out.println("ok");
                        App.setRoot("signed-in");
                        App.setRtc(new ReadThreadClient(networkUtil));
                    }
                } catch (Exception e) {
                    e.printStackTrace(); 
                }
            }
        }
    }
}