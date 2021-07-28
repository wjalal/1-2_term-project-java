package edu.buet;

import java.io.Serializable;

public class AuctionRequest implements Serializable {
    private Player player;
    private Club from;
    // private NetworkUtil networkUtil;
    private double price;

    public AuctionRequest() {

    }

    public AuctionRequest (Player player, Club from, double price) {
        this.player = player;
        this.from = from;
        this.price = price;
        // this.networkUtil = networkUtil;
    }

    public Player getPlayer() {
        return player;
    }

    public Club getFrom() {
        return from;
    }

    public double getPrice() {
        return price;
    }
}
