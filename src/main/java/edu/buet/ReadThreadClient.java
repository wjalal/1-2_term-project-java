package edu.buet;

// import javafx.fxml.Initializable;
// import java.net.URL;
// import java.util.ResourceBundle;
// import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.application.Platform;

import java.io.IOException;
import java.util.*;

public class ReadThreadClient implements Runnable {
    private Thread thr;
    private NetworkUtil networkUtil;
    private PlayerList playerList = App.getPlayerList();
    // private PlayerDisplayController pDisp;

    public ReadThreadClient(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        thr.start();
    }

    // public void setpDisp(PlayerDisplayController pDisp) {
    //     this.pDisp = pDisp;
    // }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();

                if (o instanceof AuctionUpdate) {
                    AuctionUpdate update = (AuctionUpdate) o;
                    Player p = playerList.searchByName(update.getPlayer().getName());
                    p.setAuctionState(true);
                    p.setPrice(update.getPrice());
                    playerList.getAuctionList().add(p);
                    System.out.println("DONE");
                    Platform.runLater( () -> {
                        WarningModal.display("Auction Successful", p.getName() + " is now available in the auction market");
                    });
                    // this.pDisp.loadPlayers();
                    //App.setUiUpdate(true);
                } else if (o instanceof TransferRequest) {
                    TransferRequest req = (TransferRequest) o;
                    Player p = playerList.searchByName(req.getPlayer().getName());
                    Club d = playerList.getClub(req.getDest().getName());
                    p.transferTo(d);
                    playerList.getAuctionList().remove(p);
                    if (d == playerList.getClientClub()) Platform.runLater( () -> {
                        WarningModal.display("Transfer Successful", p.getName() + " is now part of this club.");
                    });
                } else if (o instanceof Player) {
                    Player p = (Player) o;
                    Country c = playerList.getCountry(p.getCountry().getName());
                    if (c == null) {
                        c = new Country();
                        c.setName(p.getCountry().getName());
                        c.setFlagBytes(p.getCountry().getFlagBytes());
                        p.setCountry(c);
                    }
                    p.setClub(playerList.getClub(p.getClub().getName()));
                    p.setCountry(c);
                    playerList.get().add(p);
                    p.getClub().getPlayers().add(p);
                    p.getCountry().getPlayers().add(p);
                    playerList.getCountryList().add(c);
                    if (p.getClub() == playerList.getClientClub()) Platform.runLater( () -> {
                        WarningModal.display("Added successfully", p.getName() + " is now part of this club.");
                    });
                } else if (o instanceof Club) {
                    playerList.getClubList().add((Club) o);
                } else if (o instanceof ThemeUpdateRequest) {
                    ThemeUpdateRequest req = (ThemeUpdateRequest) o;
                    Club c = playerList.getClub(req.getClub().getName());
                    c.setTheme(req.getTheme());
                    App.setTheme( req.getTheme() );
                    Platform.runLater(() -> {
                        WarningModal.display("Settings applied", "Your app theme preference has been updated");
                    });
                } else if ( ((String)o).equals("OLD_PWD_NOT_MATCH") ) {
                    Platform.runLater(() -> {
                        WarningModal.display("Password Reset Failed", "Old password did not match.");
                    }); 
                } else if ( ((String)o).equals("PWD_RESET_SUCCESS") ) {
                    Platform.runLater(() -> {
                        WarningModal.display("Password Reset", "Your password was successfully changed.");
                    }); 
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

            // try {
            //     networkUtil.closeConnection();
            // } catch (IOException e) {
            //     e.printStackTrace();
            // }
    }
}



