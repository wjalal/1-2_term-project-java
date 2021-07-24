package edu.buet;

import java.io.IOException;
import java.net.URL;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


import java.util.*;

public class DemographicsController implements Initializable {

    PlayerList playerList = App.getPlayerList();
    List<Club> clubList = playerList.getClubList();
    List<Country> countryList = playerList.getCountryList();

    @FXML private void switchToPrimary() throws IOException {
        if (App.getUserMode() == UserMode.GUEST) App.setRoot("guest");
        else if (App.getUserMode() == UserMode.LOGGED_IN) App.setRoot("signed-in");
    }

    @FXML private HBox hBox;
    @FXML private VBox clubTableVBox;

    @FXML private TableView<Country> countryTable;
    @FXML TableColumn<Country, String> countryColumn;
    @FXML TableColumn<Country, Integer> countryNumColumn;
    @FXML TableColumn<Country, Image> countryFlagColumn;

    @FXML private TableView<Club> clubTable;
    @FXML TableColumn<Club, String> clubColumn;
    @FXML TableColumn<Club, Integer> clubNumColumn;
    @FXML TableColumn<Club, Image> clubFlagColumn;

    public void initialize(URL arg0, ResourceBundle arg1) {

        countryColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (App.getUserMode() == UserMode.GUEST) countryNumColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        else if (App.getUserMode() == UserMode.LOGGED_IN) 
            countryNumColumn.setCellValueFactory(new PropertyValueFactory<>("countfromclub"));
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
        if (App.getUserMode() == UserMode.GUEST) countryTable.setItems(FXCollections.observableArrayList(countryList));
        else if (App.getUserMode() == UserMode.LOGGED_IN) 
            countryTable.setItems(FXCollections.observableArrayList(playerList.getCountryList(playerList.getClientClub())));

        if (App.getUserMode() == UserMode.LOGGED_IN) hBox.getChildren().remove(clubTableVBox);
        else if (App.getUserMode() == UserMode.GUEST) {
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

}
