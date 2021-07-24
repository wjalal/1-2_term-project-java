package edu.buet;

import javafx.fxml.FXML;
// import javafx.fxml.Initializable;
// import java.net.URL;
// import java.util.ResourceBundle;
// import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.collections.FXCollections;

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
                // if (o instanceof Message) {
                //     Message obj = (Message) o;
                //     System.out.println("\n" + obj.getFrom() + " says: " + obj.getText() + "\n");
                // } else if (o instanceof String) {
                //     System.out.println((String) o);
                // }
                if (o instanceof AuctionUpdate) {
                    AuctionUpdate update = (AuctionUpdate) o;
                    Player p = playerList.searchByName(update.getPlayer().getName());
                    p.setAuctionState(true);
                    p.setPrice(update.getPrice());
                    playerList.getAuctionList().add(p);
                    System.out.println("DONE");
                    // this.pDisp.loadPlayers();
                    //App.setUiUpdate(true);
                } else if (o instanceof TransferRequest) {
                    TransferRequest req = (TransferRequest) o;
                    Player p = playerList.searchByName(req.getPlayer().getName());
                    Club d = playerList.getClub(req.getDest().getName());
                    p.transferTo(d);
                    playerList.getAuctionList().remove(p);
                } else if (o instanceof Player) {
                    Player p = (Player) o;
                    p.setClub(playerList.getClub(p.getClub().getName()));
                    p.setCountry(playerList.getCountry(p.getCountry().getName()));
                    playerList.get().add(p);
                    p.getClub().getPlayers().add(p);
                    p.getCountry().getPlayers().add(p);
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



