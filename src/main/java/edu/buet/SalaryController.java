package edu.buet;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;

import java.util.*;

public class SalaryController {

    PlayerList playerList = App.getPlayerList();
    List<Club> clubList = playerList.getClubList();
    List<Country> countryList = playerList.getCountryList();

    @FXML private void switchToPrimary() throws IOException {
        if (App.getUserMode() == UserMode.GUEST) App.setRoot("guest");
        else if (App.getUserMode() == UserMode.LOGGED_IN) App.setRoot("signed-in");
    }

    @FXML private VBox vBox;

    @FXML private TableView<Club> salaryTable;
    @FXML TableColumn<Club, String> clubColumn;
    @FXML TableColumn<Club, String> salaryColumn;
    @FXML TableColumn<Club, Image> logoColumn;
    @FXML Label title, salaryLabel = new Label();

    @FXML protected void initialize() throws Exception {

        if (App.getUserMode() == UserMode.GUEST) {
            clubColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salarystring"));
            logoColumn.setCellFactory(p -> {
                final ImageView logoView = new ImageView();
                logoView.setFitHeight(50);
                logoView.setFitWidth(50);
                TableCell<Club, Image> cell = new TableCell<Club, Image>() {
                    public void updateItem (Image item, boolean empty) {
                        if (item != null) logoView.setImage(item);
                    }
                };
                cell.setGraphic(logoView);
                return cell;
            });
            logoColumn.setCellValueFactory(new PropertyValueFactory<>("logo"));
            salaryTable.setItems(FXCollections.observableArrayList(clubList));
        } else if (App.getUserMode() == UserMode.LOGGED_IN) {
            vBox.getChildren().remove(salaryTable);
            title.setText("Total annual salary of " + playerList.getClientClub().getName() + ":");
            salaryLabel.setText(Player.showSalary(playerList.getClientClub().getAnnualSalary()));
            salaryLabel.setStyle("-fx-font: 50 System");
            vBox.getChildren().add(1, salaryLabel);
            vBox.setSpacing(70);

        }
    } 

}
