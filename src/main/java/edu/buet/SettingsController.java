package edu.buet;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
// import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;  
import javafx.scene.media.MediaPlayer;  
import javafx.scene.media.MediaView; 
import javafx.util.Duration;
// import javafx.scene.control.Label;
// import javafx.scene.control.ListView;
// import javafx.scene.image.*;
import java.io.File;
import java.nio.file.*;
import java.net.URL;

import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.scene.input.KeyCode;


public class SettingsController {

    PlayerList playerList = App.getPlayerList();
    NetworkUtil networkUtil = App.getNetworkUtil();

    @FXML private PasswordField oldPwd, newPwd1, newPwd2;
    @FXML private ComboBox<String> themeSelector;

    @FXML protected void initialize() throws Exception {

        themeSelector.getItems().addAll("Dark", "Light", "Midnight", "Abyss");
        themeSelector.getSelectionModel().select(App.getTheme());
    }

    @FXML private void resetPassword() throws Exception {
        if ( oldPwd.getText() != null && newPwd1.getText() != null && newPwd2.getText() != null && !(oldPwd.getText().equals("") || newPwd1.getText().equals("") || newPwd2.getText().equals("") ) )
        if ( newPwd1.getText().equals(newPwd2.getText()) ) {
            networkUtil.write ( new PasswordUpdateRequest (oldPwd.getText(), newPwd1.getText(), playerList.getClientClub()) );
            // App.getRtc().wait();
            // Object o = networkUtil.read();
            // if ( ((String)o).equals("OLD_PWD_NOT_MATCH") ) {
            //     App.getRtc().notify();
            //     WarningModal.display("Password Reset Failed", "Old password did not match.");
            // } else if ( ((String)o).equals("PWD_RESET_SUCCESS") ) {
            //     App.getRtc().notify();
            //     WarningModal.display("Password Reset", "Your password was successfully changed.");
            // }
        } else WarningModal.display("Password Reset Failed", "New password did not match.");
    }

    @FXML private void updateTheme() {
        App.setTheme( themeSelector.getSelectionModel().getSelectedItem() );
    }

    @FXML private void switchToPrimary() throws Exception {
        App.setRoot("signed-in");
    }

}