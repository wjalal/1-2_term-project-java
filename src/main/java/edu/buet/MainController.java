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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
// import javafx.scene.control.Label;
// import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.image.*;

import java.util.*;
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
    byte[] newPlayerPfpBytes;

    @FXML private ComboBox<String> nameSearch, clubSearch, countrySearch, positionSearch, selectedClub;
    @FXML private Spinner<Double> r1Spinner, r2Spinner; 
    @FXML private PlayerDisplayController playerDisplayPController, playerDisplayCController;
    @FXML private ToggleGroup salaryMaxMin, ageMaxMin, heightMaxMin;

    @FXML private TextField addName;
    @FXML private ChoiceBox<String> addPosition;
    @FXML private ComboBox<String> addCountry;
    @FXML private Spinner<Integer> addAge, addNumber;
    @FXML private Spinner<Double> addHeight, addSalary;
    @FXML private ImageView newPlayerPfp;


    public void initialize (URL arg0, ResourceBundle arg1) {
        //playerList.readFromFile();
        //playerDisplayPController.setPlayerList(playerList);

        playerDisplayPController.loadPlayers();
        playerDisplayCController.loadPlayers();
        //App.getRtc().setpDisp(playerDisplayPController);
        if (App.getUserMode() == UserMode.GUEST) {
            playerDisplayPController.playerData.getChildren().remove(playerDisplayPController.auctionBox);
            playerDisplayCController.playerData.getChildren().remove(playerDisplayCController.auctionBox);
        }
        clubSearch.setItems(FXCollections.observableArrayList(Club.nameList(clubList)));
        clubSearch.getItems().add(0, "—————————————— Any ——————————————");
        selectedClub.setItems(FXCollections.observableArrayList(Club.nameList(clubList)));
        selectedClub.getSelectionModel().select(0);
        countrySearch.setItems(FXCollections.observableArrayList(Country.nameList(countryList)));
        countrySearch.getItems().add(0, "—————————— Any ——————————");
        positionSearch.getItems().addAll("Forward", "Midfielder", "Defender", "Goalkeeper");

        addCountry.setItems(FXCollections.observableArrayList(Country.nameList(countryList)));
        addPosition.getItems().addAll("Forward", "Midfielder", "Defender", "Goalkeeper");

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
            selectedClub.setValue(playerList.getClientClub().getName());
            selectedClub.setDisable(true);
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

    @FXML private void showDemographics() throws IOException {
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
        Club club = playerList.getClub(selectedClub.getValue().strip());
        List<Player> result = new ArrayList<>();
        System.out.println(((RadioButton) salaryMaxMin.getSelectedToggle()).getText());
        if ( ((RadioButton) salaryMaxMin.getSelectedToggle()).getText().equals("MAX") ) result = club.getMaxSalary();
        else if ( ((RadioButton) salaryMaxMin.getSelectedToggle()).getText().equals("MIN") ) result = club.getMinSalary();

        playerDisplayCController.playerListView.setItems(FXCollections.observableArrayList(PlayerList.nameList(result)));
        playerDisplayCController.resetPlayerInfo();
        
    }

    @FXML private void searchMaxMinAge() {
        Club club = playerList.getClub(selectedClub.getValue().strip());
        List<Player> result = new ArrayList<>();
        System.out.println(((RadioButton) ageMaxMin.getSelectedToggle()).getText());
        if ( ((RadioButton) ageMaxMin.getSelectedToggle()).getText().equals("MAX") ) result = club.getMaxAge();
        else if ( ((RadioButton) ageMaxMin.getSelectedToggle()).getText().equals("MIN") ) result = club.getMinAge();

        playerDisplayCController.playerListView.setItems(FXCollections.observableArrayList(PlayerList.nameList(result)));
        playerDisplayCController.resetPlayerInfo();
        
    }

    @FXML private void searchMaxMinHeight() {
        Club club = playerList.getClub(selectedClub.getValue().strip());
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
        newPlayerPfp.setImage(new Image(getClass().getResourceAsStream("pfp/profile.png")));
    }

    @FXML private void submitPlayer() throws Exception {
        if (ConfirmationModal.display("Confirmation", "Do you confirm these attributes to add the new player?")) {
            Player p = new Player (
                addName.getText(),
                playerList.getCountry(addCountry.getValue()),
                addAge.getValue(),
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

    @FXML private void refreshList() {
        playerDisplayCController.loadPlayers();
        playerDisplayCController.resetPlayerInfo();
        playerDisplayPController.loadPlayers();
        playerDisplayPController.resetPlayerInfo();
    }

}
