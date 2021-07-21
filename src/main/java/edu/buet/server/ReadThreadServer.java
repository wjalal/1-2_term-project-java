package edu.buet.server;

import edu.buet.PlayerList;
import edu.buet.TransferRequest;
import edu.buet.Club;
import java.io.IOException;
import java.util.*;

public class ReadThreadServer implements Runnable {
    private Thread thr;
    private NetworkUtil networkUtil;
    public HashMap<Club, NetworkUtil> clientMap;


    public ReadThreadServer(HashMap<Club, NetworkUtil> map, NetworkUtil networkUtil) {
        this.clientMap = map;
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();
                if (o instanceof TransferRequest) {
                    TransferRequest req = (TransferRequest) o;
                    req.getPlayer().transferTo(req.getDest());
                }
            }
        } catch (Exception e) {
            System.out.println(e + "\nrts");
        } 
        
            // System.out.println("rts");
            // try {
            //     networkUtil.closeConnection();
            // } catch (IOException e) {
            //     e.printStackTrace();
            // }
    }
}



