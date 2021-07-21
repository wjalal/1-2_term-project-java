package edu.buet.server;

import edu.buet.PlayerList;
import edu.buet.Club;
import edu.buet.LoginCredential;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.*;

public class Server {

    private ServerSocket serverSocket;
    private static PlayerList playerList = new PlayerList();
    public HashMap<Club, NetworkUtil> clientMap;

    Server() {
        clientMap = new HashMap<>();
        try {
            serverSocket = new ServerSocket(33333);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }

    public void serve(Socket clientSocket) throws IOException, ClassNotFoundException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        Object o = networkUtil.read();
        if (o instanceof String && ((String) o).equals("GUEST_ENTRY") ) {
            playerList.setClientClub(null);
            networkUtil.write(playerList);
        } else {
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
                clientMap.put(clientClub, networkUtil);
                playerList.setClientClub(clientClub);
                networkUtil.write(playerList);
                new ReadThreadServer(clientMap, networkUtil);
            }
        }
    }

    public static void main(String args[]) throws Exception {
        playerList.readFromFile();
        new Server();
    }
}
