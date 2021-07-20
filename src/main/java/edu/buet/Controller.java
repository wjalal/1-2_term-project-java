package edu.buet;

import java.io.IOException;
import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleGroup;
// import javafx.scene.control.Label;
// import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
// import javafx.scene.image.*;

import java.util.*;
import javafx.collections.FXCollections;

public class Controller {

    

    // @FXML private ListView<String> playerListView = playerDisplay.getPlayerListView();
    // @FXML private Label ageLabel = playerDisplay.getAgeLabel(), heightLabel = playerDisplay.getHeightLabel(), numberLabel = playerDisplay.getNumberLabel(), 
    //                     positionLabel = playerDisplay.getPositionLabel(), salaryLabel = playerDisplay.getSalaryLabel(), nameLabel = playerDisplay.getNameLabel(), 
    //                     clubLabel = playerDisplay.getClubLabel(), countryLabel = playerDisplay.getCountryLabel();
    // @FXML private ImageView playerImage = playerDisplay.getPlayerImage(), clubImage = playerDisplay.getClubImage(), countryImage = playerDisplay.getCountryImage();

    PlayerList playerList = App.getPlayerList();
    List<Club> clubList = playerList.getClubList();
    List<Country> countryList = playerList.getCountryList();

    @FXML private ComboBox<String> nameSearch, clubSearch, countrySearch, positionSearch, selectedClub;
    @FXML private Spinner<Double> r1Spinner, r2Spinner; 
    @FXML private PlayerDisplayController playerDisplayPController, playerDisplayCController;
    @FXML private ToggleGroup salaryMaxMin, ageMaxMin, heightMaxMin;

    @FXML protected void initialize() throws Exception {
        playerList.readFromFile();
        //playerDisplayPController.setPlayerList(playerList);

        playerDisplayPController.loadPlayers();
        playerDisplayCController.loadPlayers();
        clubSearch.setItems(FXCollections.observableArrayList(Club.nameList(clubList)));
        clubSearch.getItems().add(0, "—————————————— Any ——————————————");
        selectedClub.setItems(FXCollections.observableArrayList(Club.nameList(clubList)));
        selectedClub.getSelectionModel().select(0);
        countrySearch.setItems(FXCollections.observableArrayList(Country.nameList(countryList)));
        countrySearch.getItems().add(0, "—————————— Any ——————————");
        positionSearch.getItems().addAll("Forward", "Midfielder", "Defender", "Goalkeeper");

        // );
        nameSearch.getEditor().setOnKeyTyped((e) -> {
            System.out.println(nameSearch.getEditor().getText());
            if (!nameSearch.getEditor().getText().strip().equals("")) {
                List<String> predictions = new ArrayList<>();
                for (Player p : playerList.get()) 
                    if ( p.getName().toLowerCase().startsWith(nameSearch.getEditor().getText().strip().toLowerCase()) ) {
                        System.out.println(p.getName());
                        predictions.add(p.getName());
                    }
                nameSearch.setItems(FXCollections.observableArrayList(predictions));
                nameSearch.hide();
            }
            nameSearch.show();
        });

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
        App.setRoot("demographics");
    }

    @FXML private void searchByName() {
        Player p = playerList.searchByName((String) nameSearch.getValue());
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
        List<Player> result = playerList.searchByPosition(positionSearch.getValue());
        if (result == null) NullPlayerListWarning.display();
        else {
            playerDisplayPController.playerListView.setItems(FXCollections.observableArrayList(PlayerList.nameList(result)));
            playerDisplayPController.resetPlayerInfo();
        }
    }

    @FXML private void searchBySalary() {
        List<Player> result = playerList.searchBySalary(r1Spinner.getValue(), r2Spinner.getValue());
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

}
