package edu.buet;

import java.io.Serializable;

public class AuctionUpdate implements Serializable {
    private Player player;
    private double price;

    public AuctionUpdate() {

    }

    public AuctionUpdate (AuctionRequest req) {
        this.player = req.getPlayer();
        this.price = req.getPrice();
    }

    public Player getPlayer() {
        return player;
    }

    public double getPrice() {
        return price;
    }
}
