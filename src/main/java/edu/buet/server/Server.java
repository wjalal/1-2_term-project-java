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

    public static PlayerList getPlayerList() {
        return playerList;
    }

    public void serve(Socket clientSocket) throws IOException, ClassNotFoundException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        new ReadThreadServer(clientMap, networkUtil);
    }

    public static void main(String args[]) throws Exception {
        playerList.readFromFile();
        (new Thread() {
            public void run() {
                Scanner input = new Scanner(System.in);
                String text = new String();
                while (true) {
                    System.out.println("Prompt:");
                    text = input.nextLine();
                    if (text.equalsIgnoreCase("exit")) {
                        try {
                            playerList.saveToFile();
                            System.out.println("Saved successfully");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        input.close();
                        System.exit(0);
                    }
                }
            }
        }).start();
        // (new Thread() {
        //     public void run() {
                new Server();
        //     }
        // }).r
        
    }
}
