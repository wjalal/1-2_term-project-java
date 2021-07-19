package edu.buet;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

import java.util.*;

public class DemographicsController {

    PlayerList playerList = App.getPlayerList();
    List<Club> clubList = playerList.getClubList();
    List<Country> countryList = playerList.getCountryList();

    @FXML private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML private TableView<Country> countryTable;

    @FXML TableColumn<Country, String> countryColumn;
    @FXML TableColumn<Country, Integer> countryNumColumn;

    @FXML protected void initialize() throws Exception {
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

    }

}
