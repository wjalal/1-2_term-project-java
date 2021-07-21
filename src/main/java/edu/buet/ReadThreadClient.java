package edu.buet;

import java.io.IOException;
import java.util.*;

public class ReadThreadClient implements Runnable {
    private Thread thr;
    private NetworkUtil networkUtil;

    public ReadThreadClient(NetworkUtil networkUtil) {
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                // Object o = networkUtil.read();
                // if (o instanceof Message) {
                //     Message obj = (Message) o;
                //     System.out.println("\n" + obj.getFrom() + " says: " + obj.getText() + "\n");
                // } else if (o instanceof String) {
                //     System.out.println((String) o);
                // }
            }
        } catch (Exception e) {
            System.out.println(e);
        } 

            // try {
            //     networkUtil.closeConnection();
            // } catch (IOException e) {
            //     e.printStackTrace();
            // }
    }
}



