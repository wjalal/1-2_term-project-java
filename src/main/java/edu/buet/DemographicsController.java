package edu.buet;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.image.*;


import java.util.*;

public class DemographicsController {

    PlayerList playerList = App.getPlayerList();
    List<Club> clubList = playerList.getClubList();
    List<Country> countryList = playerList.getCountryList();

    @FXML private void switchToPrimary() throws IOException {
        if (App.getUserMode() == UserMode.GUEST) App.setRoot("guest");
        else if (App.getUserMode() == UserMode.LOGGED_IN) App.setRoot("signed_in");
    }

    @FXML private TableView<Country> countryTable;
    @FXML TableColumn<Country, String> countryColumn;
    @FXML TableColumn<Country, Integer> countryNumColumn;
    @FXML TableColumn<Country, Image> countryFlagColumn;

    @FXML private TableView<Club> clubTable;
    @FXML TableColumn<Club, String> clubColumn;
    @FXML TableColumn<Club, Integer> clubNumColumn;
    @FXML TableColumn<Club, Image> clubFlagColumn;

    @FXML protected void initialize() throws Exception {

        countryColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryNumColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        countryFlagColumn.setCellFactory(p -> {
            final ImageView flagView = new ImageView();
            flagView.setFitHeight(44);
            flagView.setFitWidth(70);
            TableCell<Country, Image> cell = new TableCell<Country, Image>() {
                public void updateItem (Image item, boolean empty) {
                    if (item != null) {
                        flagView.setImage(item);
                    }
                }
            };

            cell.setGraphic(flagView);
            return cell;
        });
        countryFlagColumn.setCellValueFactory(new PropertyValueFactory<>("flag"));
        countryTable.setItems(FXCollections.observableArrayList(countryList));

        clubColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        clubNumColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        clubFlagColumn.setCellFactory(p -> {
            final ImageView logoView = new ImageView();
            logoView.setFitHeight(50);
            logoView.setFitWidth(50);
            TableCell<Club, Image> cell = new TableCell<Club, Image>() {
                public void updateItem (Image item, boolean empty) {
                    if (item != null) {
                        logoView.setImage(item);
                    }
                }
            };

            cell.setGraphic(logoView);
            return cell;
        });
        clubFlagColumn.setCellValueFactory(new PropertyValueFactory<>("logo"));
        clubTable.setItems(FXCollections.observableArrayList(clubList));
        
    }

}
