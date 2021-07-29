package edu.buet.server;

import edu.buet.PlayerList;
import edu.buet.Club;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.*;

public class Server {

    private static int serverPort = 6969;
    private ServerSocket serverSocket;
    private static PlayerList playerList = new PlayerList();
    public HashMap<Club, NetworkUtil> clientMap;

    Server() {
        clientMap = new HashMap<>();
        try {
            serverSocket = new ServerSocket(serverPort);
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

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("--help") || args[0].equalsIgnoreCase("-h")) {
                System.out.println("\nIf the server is launched without arguments, it will use port 6969 by default.");
                System.out.println("If only one argument is put, it will be assumed as port to run on. Eg. :");
                System.out.println("\n\tjava -jar server.jar 3000");
                System.out.println("\n(Server will try to launch on port 3000)\n");
                System.exit(0);
            } else {
                serverPort = Integer.parseInt(args[0]);
            }
        } else if (args.length != 0) {
            System.out.println("\nInvalid arguments, run with argument --help or -h for help:");
            System.out.println("\n\tjava -jar server.jar --help \n");
            System.exit(0);
        }

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
        
        new Server();
        
    }
}
