package edu.buet;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.*;

import java.util.*;
import javafx.collections.FXCollections;

public class Controller {

    @FXML private ListView<String> playerListView = new ListView<>();
    @FXML private ComboBox<String> nameSearch, clubSearch, countrySearch, positionSearch;
    @FXML private Spinner<Double> r1Spinner, r2Spinner; 
    @FXML private Label ageLabel, heightLabel, numberLabel, positionLabel, salaryLabel, nameLabel, clubLabel, countryLabel;
    @FXML private ImageView playerImage, clubImage, countryImage;

    PlayerList playerList = App.getPlayerList();
    List<Club> clubList = playerList.getClubList();
    List<Country> countryList = playerList.getCountryList();

    @FXML protected void initialize() throws Exception {
        playerList.readFromFile(clubList, countryList);

        loadPlayers();

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

    @FXML private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML private void loadPlayers() {
        // for (Player p : playerList.get())
        //     playerListView.getItems().add(p.getName());
        // playerListView.setItems( playerListView.getItems().sorted());

        playerListView.setItems( FXCollections.observableArrayList(PlayerList.nameList(playerList.get())));

    }

    @FXML private void predictName() {
        //nameSearch.show();
        

    }

    @FXML private void searchByName() {
        Player p = playerList.searchByName((String) nameSearch.getValue());
        if (p == null) NullPlayerListWarning.display();
        else {
            playerListView.getSelectionModel().select(p.getName());
            playerListView.scrollTo(p.getName());
            showPlayerInfo(p);
        }
    }

    @FXML private void searchByCountryAndClub() {

        Country country; Club club; List<Player> result = new ArrayList<>();
        boolean change = true;

        if (clubSearch.getSelectionModel().getSelectedIndex() == 0) {
            if (countrySearch.getSelectionModel().getSelectedIndex() == 0) {
                loadPlayers();
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
            playerListView.setItems(FXCollections.observableArrayList(PlayerList.nameList(result)));
            resetPlayerInfo();
        }
    }

    @FXML private void searchByPosition() {
        List<Player> result = playerList.searchByPosition(positionSearch.getValue());
        if (result == null) NullPlayerListWarning.display();
        else {
            playerListView.setItems(FXCollections.observableArrayList(PlayerList.nameList(result)));
            resetPlayerInfo();
        }
    }

    @FXML private void searchBySalary() {
        List<Player> result = playerList.searchBySalary(r1Spinner.getValue(), r2Spinner.getValue());
        if (result == null) NullPlayerListWarning.display();
        else {
            playerListView.setItems(FXCollections.observableArrayList(PlayerList.nameList(result)));
            resetPlayerInfo();
        }
    }

    @FXML private void resetPlayerInfo() {
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
    }

    @FXML private void showPlayerInfo(Player p) {
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
        Image pfp = new Image(getClass().getResourceAsStream("pfp/" + p.getName() + ".png"));
        playerImage.setImage(pfp);
        Image cflag = new Image(getClass().getResourceAsStream("cflag/" + p.getCountry().getName() + ".png"));
        countryImage.setImage(cflag);
        Image clublogo = new Image(getClass().getResourceAsStream("clublogo/" + p.getClub().getName() + ".png"));
        clubImage.setImage(clublogo);
    }

    @FXML private void showPlayerInfo() {
        // System.out.println((String)playerListView.getSelectionModel().getSelectedItem());
        showPlayerInfo(playerList.searchByName((String) playerListView.getSelectionModel().getSelectedItem()));

    }
}
