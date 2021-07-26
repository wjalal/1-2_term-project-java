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
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;

import java.util.*;

public class AuctionController implements Initializable {

    PlayerList playerList = App.getPlayerList();
    List<Club> clubList = playerList.getClubList();
    List<Country> countryList = playerList.getCountryList();
    NetworkUtil networkUtil = App.getNetworkUtil();

    @FXML private void switchToPrimary() throws IOException {
        if (App.getUserMode() == UserMode.GUEST) App.setRoot("guest");
        else if (App.getUserMode() == UserMode.LOGGED_IN) App.setRoot("signed-in");
    }

    @FXML private TableView<Player> playerTable;
    @FXML TableColumn<Player, String> nameColumn;
    @FXML TableColumn<Player, Club> clubColumn;
    @FXML TableColumn<Player, String> salaryColumn;
    @FXML TableColumn<Player, Club> logoColumn;
    @FXML TableColumn<Player, Image> playerImageColumn;
    @FXML TableColumn<Player, Country> flagColumn;
    @FXML TableColumn<Player, Country> countryColumn;
    @FXML TableColumn<Player, String> positionColumn;
    @FXML TableColumn<Player, Integer> ageColumn;
    @FXML TableColumn<Player, Double> heightColumn;
    @FXML TableColumn<Player, Integer> priceColumn;
    @FXML TableColumn<Player, Player> buyColumn;
    

    @FXML Label title, salaryLabel = new Label();

    public void initialize (URL arg0, ResourceBundle arg1) {

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        playerImageColumn.setCellFactory(p -> {
            final ImageView imageView = new ImageView();
            imageView.setFitHeight(70);
            imageView.setFitWidth(70);
            TableCell<Player, Image> cell = new TableCell<Player, Image>() {
                public void updateItem (Image item, boolean empty) {
                    if (item != null) imageView.setImage(item);
                }
            };
            cell.setGraphic(imageView);
            return cell;
        });
        playerImageColumn.setCellValueFactory(new PropertyValueFactory<>("pfp"));
      
        logoColumn.setCellFactory(p -> {
            final ImageView logoView = new ImageView();
            logoView.setFitHeight(50);
            logoView.setFitWidth(50);
            TableCell<Player, Club> cell = new TableCell<Player, Club>() {
                public void updateItem (Club item, boolean empty) {
                    if (item != null) logoView.setImage(item.getLogo());
                }
            };
            cell.setGraphic(logoView);
            return cell;
        });
        logoColumn.setCellValueFactory(new PropertyValueFactory<>("club"));

        clubColumn.setCellValueFactory(new PropertyValueFactory<>("club"));

        flagColumn.setCellFactory(p -> {
            final ImageView flagView = new ImageView();
            flagView.setFitHeight(44);
            flagView.setFitWidth(70);
            TableCell<Player, Country> cell = new TableCell<Player, Country>() {
                public void updateItem (Country item, boolean empty) {
                    if (item != null) flagView.setImage(item.getFlag());
                }
            };
            cell.setGraphic(flagView);
            return cell;
        });
        flagColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));

        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));

        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));

        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salaryLabel"));

        priceColumn.setCellValueFactory(new PropertyValueFactory<>("priceLabel"));

        buyColumn.setCellFactory(p -> {
            final Button buyButton = new Button("Buy");
            TableCell<Player, Player> cell = new TableCell<Player, Player>() {
                public void updateItem (Player p, boolean empty) {
                    if (p != null && p.getClub() != playerList.getClientClub() ) buyButton.setOnAction(e -> {
                        if (p.getPrice() <= playerList.getClientClub().getBalance()) {
                            String message = "Are you sure you want to buy " + p.getName() + " for " + p.getPriceLabel() + "?";
                            if (ConfirmationModal.display("Confirmation", message)) try {
                                networkUtil.write (new TransferRequest(p, playerList.getClientClub()));
                                switchToPrimary();
                            } catch (Exception x) {
                                System.out.println(x);
                                x.printStackTrace();
                            }
                        } else {
                            WarningModal.display("Transfer faield", "Your club's funds are insufficient for this transfer." + 
                                                                    "\nPlease source funds and ask the administrator to update your data.");
                        }
                    });
                    else buyButton.setVisible(false);
                }
            };
            cell.setGraphic(buyButton);
            return cell;
        });
        buyColumn.setCellValueFactory(new PropertyValueFactory<>("player"));

        playerTable.setItems(FXCollections.observableArrayList(playerList.getAuctionList()));
    } 

}
