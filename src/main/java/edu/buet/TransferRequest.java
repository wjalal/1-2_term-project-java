package edu.buet;

import java.io.Serializable;

public class TransferRequest implements Serializable {
    private Player player;
    private Club dest;

    public TransferRequest() {

    }

    public TransferRequest (Player player, Club dest) {
        this.player = player;
        this.dest = dest;
    }

    public Club getDest() {
        return dest;
    }

    public Player getPlayer() {
        return player;
    }

}
