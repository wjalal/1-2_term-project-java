package edu.buet.server;

import edu.buet.PlayerList;
import edu.buet.SignUpRequest;
import edu.buet.TransferRequest;
import edu.buet.Club;
import edu.buet.Country;
import edu.buet.LoginCredential;
import edu.buet.AuctionRequest;
import edu.buet.AuctionUpdate;
import edu.buet.Player;
import java.io.IOException;
import java.util.*;

public class ReadThreadServer implements Runnable {
    private Thread thr;
    private NetworkUtil networkUtil;
    public HashMap<Club, NetworkUtil> clientMap;
    private PlayerList playerList = Server.getPlayerList();

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
                if (o instanceof String && ((String) o).equals("GUEST_ENTRY") ) {
                    playerList.setClientClub(null);
                    networkUtil.write(playerList);
                } else if (o instanceof LoginCredential) {
                    LoginCredential clientCredential = (LoginCredential) o;
                    Club clientClub = playerList.getClub(clientCredential.getName());
                    if (clientClub == null) {
                        networkUtil.write("NO_CRED_MATCH");
                        // try {
                        //     networkUtil.closeConnection();
                        // } catch (IOException e) {
                        //     e.printStackTrace();
                        // }
                    } else {
                        playerList.readCredentialsFromFile();
                        System.out.println(clientCredential.getPasswordHash() + "\n" + clientClub.getPasswordHash());
                        if (clientCredential.getPasswordHash().equals(clientClub.getPasswordHash())) {
                            playerList.resetCredentials();
                            clientMap.put(clientClub, networkUtil);
                            playerList.setClientClub(clientClub);
                            networkUtil.write(playerList);
                        } else networkUtil.write("NO_CRED_MATCH");
                    }
                } else if (o instanceof TransferRequest) {
                    TransferRequest req = (TransferRequest) o;
                    Player p = playerList.searchByName(req.getPlayer().getName());
                    Club d = playerList.getClub(req.getDest().getName());
                    p.transferTo(d);
                    playerList.getAuctionList().remove(p);
                    System.out.println( p.getName() + ", " + d.getName() );
                    for (Club c : clientMap.keySet()) {
                        System.out.println("HELLO2");
                        // AuctionUpdate update = new Update(req);
                        (clientMap.get(c)).write(req);
                        System.out.println("DONE");
                    }
                } else if (o instanceof AuctionRequest) {
                    AuctionRequest req = (AuctionRequest) o;
                    Player p = playerList.searchByName(req.getPlayer().getName());
                    p.setAuctionState(true);
                    p.setPrice(req.getPrice());
                    playerList.getAuctionList().add(p);
                    for (Club c : clientMap.keySet()) {
                        System.out.println("HELLO1");
                        AuctionUpdate update = new AuctionUpdate(req);
                        (clientMap.get(c)).write(update);
                        System.out.println("DONE");
                    }
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
                    for (Club cl : clientMap.keySet()) {
                        System.out.println("HELLO2");
                        // AuctionUpdate update = new Update(req);
                        (clientMap.get(cl)).write(p);
                        System.out.println("DONE");
                    }
                } else if (o instanceof SignUpRequest) {
                    SignUpRequest req = (SignUpRequest) o;
                    if (playerList.getClub(req.getCredential().getName()) != null) {
                        networkUtil.write ("CLUB_PRE-EXIST");
                    } else {
                        playerList.readFromFile();
                        Club c = new Club();
                        c.setName(req.getCredential().getName());
                        c.setPasswordHash(req.getCredential().getPasswordHash());
                        c.setLogoBytes(req.getLogoBytes());
                        playerList.getClubList().add(c);
                        playerList.setClientClub(c);
                        networkUtil.write(playerList);
                    } 
                }
            }
        } catch (Exception e) {
            System.out.println(e + "\nrts");
            e.printStackTrace();
        } finally {
            try {
                clientMap.values().remove(networkUtil);
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("WHY");
            }
        }
        
            // System.out.println("rts");
            // try {
            //     networkUtil.closeConnection();
            // } catch (IOException e) {
            //     e.printStackTrace();
            // }
    }
}



