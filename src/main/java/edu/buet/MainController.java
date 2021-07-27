package edu.buet;

import java.io.IOException;
import java.io.File;
import java.nio.file.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;

import javafx.scene.control.ChoiceBox;
// import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.scene.control.Label;
// import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.image.*;

import java.util.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

public class MainController implements Initializable {

    

    // @FXML private ListView<String> playerListView = playerDisplay.getPlayerListView();
    // @FXML private Label ageLabel = playerDisplay.getAgeLabel(), heightLabel = playerDisplay.getHeightLabel(), numberLabel = playerDisplay.getNumberLabel(), 
    //                     positionLabel = playerDisplay.getPositionLabel(), salaryLabel = playerDisplay.getSalaryLabel(), nameLabel = playerDisplay.getNameLabel(), 
    //                     clubLabel = playerDisplay.getClubLabel(), countryLabel = playerDisplay.getCountryLabel();
    // @FXML private ImageView playerImage = playerDisplay.getPlayerImage(), clubImage = playerDisplay.getClubImage(), countryImage = playerDisplay.getCountryImage();

    PlayerList playerList = App.getPlayerList();
    List<Club> clubList = playerList.getClubList();
    List<Country> countryList = playerList.getCountryList();
    byte[] newPlayerPfpBytes = null, newCountryFlagBytes = null;

    @FXML private ComboBox<String> nameSearch, clubSearch, countrySearch, positionSearch, selectedClub;
    @FXML private Spinner<Double> r1Spinner, r2Spinner; 
    @FXML private PlayerDisplayController playerDisplayPController, playerDisplayCController;
    @FXML private ToggleGroup salaryMaxMin, ageMaxMin, heightMaxMin;

    @FXML private TextField addName;
    @FXML private ChoiceBox<String> addPosition;
    @FXML private ComboBox<String> addCountry;
    @FXML private Spinner<Integer> addAge, addNumber;
    @FXML private Spinner<Double> addHeight, addSalary;
    @FXML private ImageView newPlayerPfp, clubLogoDisplay;
    @FXML private Label clubNameDisplay, clubBalanceDisplay;

    @FXML private TabPane tabPane1;


    public void initialize (URL arg0, ResourceBundle arg1) {
        //playerList.readFromFile();
        //playerDisplayPController.setPlayerList(playerList);

        playerDisplayPController.loadPlayers();
        playerDisplayCController.loadPlayers();
                
        tabPane1.getSelectionModel().select(App.getTabIdx1());
        System.out.println("SELECTED:  " + tabPane1.getSelectionModel().getSelectedIndex());
        //App.getRtc().setpDisp(playerDisplayPController);
        if (App.getUserMode() == UserMode.GUEST) {

            playerDisplayPController.playerData.getChildren().remove(playerDisplayPController.auctionBox);
            playerDisplayCController.playerData.getChildren().remove(playerDisplayCController.auctionBox);
            selectedClub.setItems(FXCollections.observableArrayList(Club.nameList(clubList)));
            selectedClub.getSelectionModel().select(0);

            selectedClub.getEditor().setOnKeyTyped((e) -> {
                System.out.println(selectedClub.getEditor().getText());
                if (!selectedClub.getEditor().getText().strip().equals("")) {
                    List<String> predictions = new ArrayList<>();
                    for (Club p : clubList) 
                        if ( p.getName().toLowerCase().startsWith(selectedClub.getEditor().getText().strip().toLowerCase()) ) {
                            System.out.println(p.getName());
                            predictions.add(p.getName());
                        }
                    selectedClub.setItems(FXCollections.observableArrayList(predictions));
                    selectedClub.hide();
                } else {
                    selectedClub.setItems(FXCollections.observableArrayList(Club.nameList(clubList)));
                    selectedClub.hide();
                }
                selectedClub.show();
            });

        } else if (App.getUserMode()==UserMode.LOGGED_IN) {

            clubNameDisplay.textProperty().bind(new SimpleStringProperty(playerList.getClientClub().getName()));
            clubBalanceDisplay.textProperty().bind(new SimpleStringProperty(Player.showSalary(playerList.getClientClub().getBalance())));
            clubLogoDisplay.setImage(playerList.getClientClub().getLogo());

            addCountry.setItems(FXCollections.observableArrayList(Country.nameList(countryList)));
            addPosition.getItems().addAll("Forward", "Midfielder", "Defender", "Goalkeeper");
            
            addCountry.getEditor().setOnKeyTyped((e) -> {
                System.out.println(addCountry.getEditor().getText());
                if (!addCountry.getEditor().getText().strip().equals("")) {
                    List<String> predictions = new ArrayList<>();
                    for (Country p : countryList) 
                        if ( p.getName().toLowerCase().startsWith(addCountry.getEditor().getText().strip().toLowerCase()) ) {
                            System.out.println(p.getName());
                            predictions.add(p.getName());
                        }
                    addCountry.setItems(FXCollections.observableArrayList(predictions));
                    addCountry.hide();
                } else {
                    addCountry.setItems(FXCollections.observableArrayList(Country.nameList(countryList)));
                    addCountry.hide();
                }
                addCountry.show();
            });

        }

        clubSearch.setItems(FXCollections.observableArrayList(Club.nameList(clubList)));
        clubSearch.getItems().add(0, "—————————————— Any ——————————————");
        countrySearch.setItems(FXCollections.observableArrayList(Country.nameList(countryList)));
        countrySearch.getItems().add(0, "—————————— Any ——————————");
        positionSearch.getItems().addAll("Forward", "Midfielder", "Defender", "Goalkeeper");

        // );
        nameSearch.getEditor().setOnKeyTyped((e) -> {
            System.out.println(nameSearch.getEditor().getText());
            if (!nameSearch.getEditor().getText().strip().equals("")) {
                List<String> predictions = new ArrayList<>();
                List<Player> players = new ArrayList<>();
                if (App.getUserMode()==UserMode.GUEST) players = playerList.get();
                else if (App.getUserMode()==UserMode.LOGGED_IN) players = playerList.getClientClub().getPlayers();
                for (Player p : players) 
                    if ( p.getName().toLowerCase().startsWith(nameSearch.getEditor().getText().strip().toLowerCase()) ) {
                        System.out.println(p.getName());
                        predictions.add(p.getName());
                    }
                nameSearch.setItems(FXCollections.observableArrayList(predictions));
                nameSearch.hide();
            }
            nameSearch.show();
        });

        if (App.getUserMode()==UserMode.LOGGED_IN) {
            clubSearch.setValue(playerList.getClientClub().getName());
            clubSearch.setDisable(true);
            // selectedClub.setValue(playerList.getClientClub().getName());
            // selectedClub.setDisable(true);
        } else {
            clubSearch.getEditor().setOnKeyTyped((e) -> {
                System.out.println(clubSearch.getEditor().getText());
                if (!clubSearch.getEditor().getText().strip().equals("")) {
                    List<String> predictions = new ArrayList<>();
                    for (Club p : clubList) 
                        if ( p.getName().toLowerCase().startsWith(clubSearch.getEditor().getText().strip().toLowerCase()) ) {
                            System.out.println(p.getName());
                            predictions.add(p.getName());
                        }
                    clubSearch.setItems(FXCollections.observableArrayList(predictions));
                    clubSearch.hide();
                } else {
                    clubSearch.setItems(FXCollections.observableArrayList(Club.nameList(clubList)));
                    clubSearch.hide();
                }
                clubSearch.show();
            });

        }

        countrySearch.getEditor().setOnKeyTyped((e) -> {
            System.out.println(countrySearch.getEditor().getText());
            if (!countrySearch.getEditor().getText().strip().equals("")) {
                List<String> predictions = new ArrayList<>();
                for (Country p : countryList) 
                    if ( p.getName().toLowerCase().startsWith(countrySearch.getEditor().getText().strip().toLowerCase()) ) {
                        System.out.println(p.getName());
                        predictions.add(p.getName());
                    }
                countrySearch.setItems(FXCollections.observableArrayList(predictions));
                countrySearch.hide();
            } else {
                countrySearch.setItems(FXCollections.observableArrayList(Country.nameList(countryList)));
                countrySearch.hide();
            }
            countrySearch.show();
        });

    }

    @FXML private void showDemographics() throws IOException {
        // App.setTabIdx1(tabPane1.getSelectionModel().getSelectedIndex());
        App.setRoot("demographics");
    }

    @FXML private void searchByName() {
        Player p = new Player();
        if (App.getUserMode() == UserMode.GUEST) 
            p = playerList.searchByName((String) nameSearch.getValue());
        else if (App.getUserMode() == UserMode.LOGGED_IN) 
            p = playerList.searchByName((String) nameSearch.getValue(), playerList.getClientClub().getPlayers());
        if (p == null) NullPlayerListWarning.display();
        else {
            playerDisplayPController.playerListView.getSelectionModel().select(p.getName());
            playerDisplayPController.playerListView.scrollTo(p.getName());
            playerDisplayPController.showPlayerInfo(p);
        }
    }

    @FXML private void searchByCountryAndClub() {

        Country country; Club club; List<Player> result = new ArrayList<>();
        boolean change = true;

        if (clubSearch.getSelectionModel().getSelectedIndex() == 0) {
            if (countrySearch.getSelectionModel().getSelectedIndex() == 0) {
                playerDisplayPController.loadPlayers();
                change = false;
            } else {
                country = playerList.getCountry(countrySearch.getValue().strip());
                result = country.searchByClubAndCountry();
            }
        } else {
            if (countrySearch.getSelectionModel().getSelectedIndex() == 0) {
                club = playerList.getClub(clubSearch.getValue().strip());
                result = club.searchByClubAndCountry();
            } else {
                club = playerList.getClub(clubSearch.getValue().strip());
                country = playerList.getCountry(countrySearch.getValue().strip());
                result = country.searchByClubAndCountry(club);
            }
        }
        
        if (result == null) NullPlayerListWarning.display();
        else if (change) {
            playerDisplayPController.playerListView.setItems(FXCollections.observableArrayList(PlayerList.nameList(result)));
            playerDisplayPController.resetPlayerInfo();
        }
    }

    @FXML private void searchByPosition() {
        List<Player> result = new ArrayList<>();
        if (App.getUserMode() == UserMode.GUEST) result = playerList.searchByPosition(positionSearch.getValue());
        else if (App.getUserMode() == UserMode.LOGGED_IN) result = playerList.searchByPosition(positionSearch.getValue(), playerList.getClientClub().getPlayers());
        if (result == null) NullPlayerListWarning.display();
        else {
            playerDisplayPController.playerListView.setItems(FXCollections.observableArrayList(PlayerList.nameList(result)));
            playerDisplayPController.resetPlayerInfo();
        }
    }

    @FXML private void searchBySalary() {
        List<Player> result = new ArrayList<>();
        if (App.getUserMode() == UserMode.GUEST) result = playerList.searchBySalary(r1Spinner.getValue(), r2Spinner.getValue());
        else if (App.getUserMode() == UserMode.LOGGED_IN) result = playerList.searchBySalary(r1Spinner.getValue(), r2Spinner.getValue(), playerList.getClientClub().getPlayers());
        if (result == null) NullPlayerListWarning.display();
        else {
            playerDisplayPController.playerListView.setItems(FXCollections.observableArrayList(PlayerList.nameList(result)));
            playerDisplayPController.resetPlayerInfo();
        }
    }

    @FXML private void searchMaxMinSalary() {
        Club club = new Club();
        if (App.getUserMode() == UserMode.GUEST) club = playerList.getClub(selectedClub.getValue().strip());
        else if (App.getUserMode() == UserMode.LOGGED_IN) club = playerList.getClientClub();
        List<Player> result = new ArrayList<>();
        System.out.println(((RadioButton) salaryMaxMin.getSelectedToggle()).getText());
        if ( ((RadioButton) salaryMaxMin.getSelectedToggle()).getText().equals("MAX") ) result = club.getMaxSalary();
        else if ( ((RadioButton) salaryMaxMin.getSelectedToggle()).getText().equals("MIN") ) result = club.getMinSalary();

        playerDisplayCController.playerListView.setItems(FXCollections.observableArrayList(PlayerList.nameList(result)));
        playerDisplayCController.resetPlayerInfo();
        
    }

    @FXML private void searchMaxMinAge() {
        Club club = new Club();
        if (App.getUserMode() == UserMode.GUEST) club = playerList.getClub(selectedClub.getValue().strip());
        else if (App.getUserMode() == UserMode.LOGGED_IN) club = playerList.getClientClub();
        List<Player> result = new ArrayList<>();
        System.out.println(((RadioButton) ageMaxMin.getSelectedToggle()).getText());
        if ( ((RadioButton) ageMaxMin.getSelectedToggle()).getText().equals("MAX") ) result = club.getMaxAge();
        else if ( ((RadioButton) ageMaxMin.getSelectedToggle()).getText().equals("MIN") ) result = club.getMinAge();

        playerDisplayCController.playerListView.setItems(FXCollections.observableArrayList(PlayerList.nameList(result)));
        playerDisplayCController.resetPlayerInfo();
        
    }

    @FXML private void searchMaxMinHeight() {
        Club club = new Club();
        if (App.getUserMode() == UserMode.GUEST) club = playerList.getClub(selectedClub.getValue().strip());
        else if (App.getUserMode() == UserMode.LOGGED_IN) club = playerList.getClientClub();
        List<Player> result = new ArrayList<>();
        System.out.println(((RadioButton) heightMaxMin.getSelectedToggle()).getText());
        if ( ((RadioButton) heightMaxMin.getSelectedToggle()).getText().equals("MAX") ) result = club.getMaxHeight();
        else if ( ((RadioButton) heightMaxMin.getSelectedToggle()).getText().equals("MIN") ) result = club.getMinHeight();

        playerDisplayCController.playerListView.setItems(FXCollections.observableArrayList(PlayerList.nameList(result)));
        playerDisplayCController.resetPlayerInfo();
        
    }

    @FXML private void showAnnualSalary() throws IOException {
        App.setRoot("salary");
    }

    @FXML private void showAuction() throws IOException {
        App.setRoot("auction");
    }

    @FXML private void showSettings() throws IOException {
        App.setRoot("settings");
    }

    @FXML private void logout() throws IOException {
        if ( ConfirmationModal.display("Sign-out Confirmation", "Are you sure you want to sign out?") )  {
            App.getNetworkUtil().closeConnection();
            App.setNetworkUtil(new NetworkUtil("127.0.0.1", 33333));
            App.setRoot("login");
        } else if (App.getUserMode() == UserMode.GUEST) App.setRoot("guest");
    }

    @FXML private void addPfpChoose() throws Exception {
        FileChooser pfpChooser = new FileChooser();
        File pfpFile = pfpChooser.showOpenDialog(App.getMainStage());
        System.out.println(pfpFile.toURI().toString());
        Image pfpImage = new Image(pfpFile.toURI().toString());
        newPlayerPfp.setImage(pfpImage);
        newPlayerPfpBytes = Files.readAllBytes(pfpFile.toPath());
    }

    @FXML private void resetAddPlayer() {
        addName.clear();
        addPosition.getSelectionModel().clearSelection();
        addCountry.getSelectionModel().clearSelection();
        addCountry.getEditor().clear();
        addAge.getValueFactory().setValue(15);
        addHeight.getValueFactory().setValue(1.4);
        addSalary.getValueFactory().setValue(0.0);
        addNumber.getValueFactory().setValue(1);
        newPlayerPfpBytes = null;
        newPlayerPfp.setImage(new Image(getClass().getResourceAsStream("images/profile.png")));
    }

    @FXML private void submitPlayer() throws Exception {
        if (addName.getText().strip().isEmpty()) WarningModal.display("Input missing", "Please enter a player name.");
        else if (addPosition.getSelectionModel().isEmpty()) WarningModal.display("Input missing", "Please choose a position.");
        else if (addCountry.getSelectionModel().isEmpty() && addCountry.getEditor().getText().isEmpty()) WarningModal.display("Input missing", "Please choose a country.");
        else if (newPlayerPfpBytes == null)  WarningModal.display("Input missing", "Please choose a picture for the player.");
        else if (playerList.searchByName(addName.getText()) != null) WarningModal.display("Invalid Name", "A player with this name already exists!");
        else if (!playerList.getClientClub().isNumberValid(addNumber.getValue())) WarningModal.display("Invalid Number", "A player with this number already exists in the club!");
        else {
            if (ConfirmationModal.display("Confirmation", "Do you confirm these attributes to add the new player?")) {
                boolean continUe = true;
                Country c = playerList.getCountry(addCountry.getValue());
                if (c == null) {
                    WarningModal.display("New country", "The country you entered does not appear in the database.\n" +
                                            "You will now be prompted to select a picture as the new country's flag.\n" + 
                                            "(If you wish to cancel then please just choose Cancel in the file dialog.)");
                    FileChooser flagChooser = new FileChooser();
                    File flagFile = flagChooser.showOpenDialog(App.getMainStage());
                    if (flagFile == null) continUe = false;
                    else {
                        System.out.println(flagFile.toURI().toString());
                        // Image pfpImage = new Image(pfpFile.toURI().toString());
                        // newPlayerPfp.setImage(pfpImage);
                        newCountryFlagBytes = Files.readAllBytes(flagFile.toPath());
                        c = new Country();
                        c.setName(addCountry.getEditor().getText().strip());
                        c.setFlagBytes(newCountryFlagBytes);
                    }
                }
                if (continUe) {
                    Player p = new Player (
                        addName.getText().strip(),
                        c, addAge.getValue(),
                        addHeight.getValue(),
                        playerList.getClientClub(),
                        addPosition.getValue(),
                        addNumber.getValue(),
                        addSalary.getValue(),
                        newPlayerPfpBytes
                    );
                    App.getNetworkUtil().write(p);
                    resetAddPlayer();
                }
            }
        }
    }

    @FXML private void refreshList() {
            playerDisplayCController.loadPlayers();
            playerDisplayCController.resetPlayerInfo();
            playerDisplayPController.loadPlayers();
            playerDisplayPController.resetPlayerInfo();
        if (tabPane1.getSelectionModel().getSelectedIndex() != 2) {
            App.setTabIdx1 (tabPane1.getSelectionModel().getSelectedIndex());
            System.out.println("SELECTION DONE: " + App.getTabIdx1());
        }
    }

}
