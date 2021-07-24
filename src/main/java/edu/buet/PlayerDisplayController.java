package edu.buet;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
// import javafx.fxml.Initializable;
import java.net.URL;
// import java.util.ResourceBundle;
// import javafx.scene.layout.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.collections.FXCollections;
import java.util.*;

public class PlayerDisplayController implements Initializable {

    PlayerList playerList = App.getPlayerList();
    List<Club> clubList = playerList.getClubList();
    List<Country> countryList = playerList.getCountryList();
    NetworkUtil networkUtil = App.getNetworkUtil();

    @FXML public ListView<String> playerListView = new ListView<>();
    @FXML public Label ageLabel, heightLabel, numberLabel, positionLabel, salaryLabel, nameLabel, clubLabel, countryLabel, auctionLabel;
    @FXML public ImageView playerImage, clubImage, countryImage;
    @FXML public Spinner<Double> priceSpinner;
    @FXML public Button auctionButton;
    @FXML public HBox playerData;
    @FXML public VBox auctionBox;

    public void initialize (URL arg0, ResourceBundle arg1) {

    }

    // public void setPlayerList (PlayerList playerList) {
    //     this.playerList = playerList;
    // }

    // @FXML public void initialize() {
    //     loadPlayers();
    // }

    // public Label getAgeLabel() {
    //     return ageLabel;
    // }

    // public ImageView getClubImage() {
    //     return clubImage;
    // }

    // public Label getClubLabel() {
    //     return clubLabel;
    // }
    
    // public ImageView getCountryImage() {
    //     return countryImage;
    // }

    // public Label getCountryLabel() {
    //     return countryLabel;
    // }

    // public Label getHeightLabel() {
    //     return heightLabel;
    // }

    // public Label getNameLabel() {
    //     return nameLabel;
    // }

    // public Label getNumberLabel() {
    //     return numberLabel;
    // }

    // public ImageView getPlayerImage() {
    //     return playerImage;
    // }

    // public ListView<String> getPlayerListView() {
    //     return playerListView;
    // }

    // public Label getPositionLabel() {
    //     return positionLabel;
    // }

    // public Label getSalaryLabel() {
    //     return salaryLabel;
    // }

    @FXML public void loadPlayers() {
        // for (Player p : playerList.get())
        //     playerListView.getItems().add(p.getName());
        // playerListView.setItems( playerListView.getItems().sorted());
        List<Player> list = new ArrayList<>();
        if (App.getUserMode() == UserMode.GUEST) list = playerList.get();
        else if (App.getUserMode() == UserMode.LOGGED_IN) {
            list = playerList.getClientClub().getPlayers();
            auctionButton.setDisable(true);
            priceSpinner.setDisable(true);
            priceSpinner.getValueFactory().setValue(0.0);
            auctionLabel.setText("Enter price:");
        }
        playerListView.setItems( FXCollections.observableArrayList(PlayerList.nameList(list)));
    }

    @FXML public void resetPlayerInfo() {
        nameLabel.setText("Player Information");
        ageLabel.setText("No data selected");
        ageLabel.setDisable(true);
        heightLabel.setText("No data selected");
        heightLabel.setDisable(true);
        numberLabel.setText("No data selected");
        numberLabel.setDisable(true);
        positionLabel.setText("No data selected");
        positionLabel.setDisable(true);
        salaryLabel.setText("No data selected");
        salaryLabel.setDisable(true);
        clubLabel.setText("No data selected");
        clubLabel.setDisable(true);
        countryLabel.setText("No data selected");
        countryLabel.setDisable(true);
        Image pfp = new Image(getClass().getResourceAsStream("pfp/profile.png"));
        playerImage.setImage(pfp);
        Image cflag = new Image(getClass().getResourceAsStream("cflag/flag.png"));
        countryImage.setImage(cflag);
        Image clublogo = new Image(getClass().getResourceAsStream("clublogo/logo.png"));
        clubImage.setImage(clublogo);
        if (App.getUserMode() == UserMode.LOGGED_IN) {
            auctionButton.setDisable(true);
            priceSpinner.setDisable(true);
            priceSpinner.getValueFactory().setValue(0.0);
            auctionLabel.setText("Enter price:");
        }
    }

    @FXML public void showPlayerInfo(Player p) {
        nameLabel.setText(p.getName());
        ageLabel.setText(Integer.toString(p.getAge()) + " years");
        ageLabel.setDisable(false);
        heightLabel.setText(Double.toString(p.getHeight()) + " metres");
        heightLabel.setDisable(false);
        numberLabel.setText(Integer.toString(p.getNumber()));
        numberLabel.setDisable(false);
        positionLabel.setText(p.getPosition());
        positionLabel.setDisable(false);
        salaryLabel.setText(Player.showSalary(p.getSalary()));
        salaryLabel.setDisable(false);
        clubLabel.setText(p.getClub().getName());
        clubLabel.setDisable(false);
        countryLabel.setText(p.getCountry().getName());
        countryLabel.setDisable(false);
        // Image pfp = new Image(getClass().getResourceAsStream("pfp/" + p.getName() + ".png"));
        playerImage.setImage(p.getPfp());
        //Image cflag = new Image(getClass().getResourceAsStream("cflag/" + p.getCountry().getName() + ".png"));
        countryImage.setImage(p.getCountry().getFlag());
        //Image clublogo = new Image(getClass().getResourceAsStream("clublogo/" + p.getClub().getName() + ".png"));
        clubImage.setImage(p.getClub().getLogo());
        if (App.getUserMode() == UserMode.LOGGED_IN) {
            if (!p.isAuctioned()) {
                auctionButton.setDisable(false);
                priceSpinner.setDisable(false);
                priceSpinner.getValueFactory().setValue(0.0);
                auctionLabel.setText("Enter price:");
            } else {
                auctionButton.setDisable(true);
                priceSpinner.getValueFactory().setValue(p.getPrice());
                priceSpinner.setDisable(true);
                auctionLabel.setText("AUCTIONED");
            }
        }
    }

    @FXML public void showPlayerInfo() {
        // System.out.println((String)playerListView.getSelectionModel().getSelectedItem());

        if (App.getUserMode() == UserMode.GUEST) showPlayerInfo(playerList.searchByName((String) playerListView.getSelectionModel().getSelectedItem()));
        else if (App.getUserMode() == UserMode.LOGGED_IN) showPlayerInfo(playerList.searchByName((String) playerListView.getSelectionModel().getSelectedItem(), playerList.getClientClub().getPlayers()));
    }

    @FXML public void auctionPlayer() {
        try {
            Player player = playerList.searchByName((String) playerListView.getSelectionModel().getSelectedItem());
            System.out.println(player.getName());
            String message = "Are you sure you want to auction " + player.getName() + " for " + Player.showSalary(priceSpinner.getValue()) + "?";
            if ( ConfirmationModal.display("Confirmation", message) ) {
                networkUtil.write(new AuctionRequest(player, playerList.getClientClub(), priceSpinner.getValue()));
                loadPlayers();
                resetPlayerInfo();
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
            System.out.println("auction error");
        }
    }
}
