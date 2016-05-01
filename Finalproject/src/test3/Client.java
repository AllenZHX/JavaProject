package test3;
/*
 * @ author: Hongxiang Zheng, Xiang Cao
 * 
 * ***************************************************
 * **********   Client for pickup part    ************
 * ***************************************************
 * 
 */
import java.net.*;

import javax.swing.JFrame;

import java.io.*;
 
public class Client {
 
    private Socket client;
 
    public Client(String info) {
        try {
            client = new Socket("172.20.10.5", 8000);
            BufferedReader input = new BufferedReader(new StringReader(info));
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintStream out = new PrintStream(client.getOutputStream());;
            boolean flag = true;
            //System.out.println("Successfully connected! Start communicating!");
            int n = 0;
            while (flag & n < 5) {
                out.println(input.readLine()); 
                n++;
                if (isConnected()) {
                   // System.out.println("successfully sent!");
                   // System.out.println("Server:" + in.readLine());
                } else {
                    //System.out.println("Failed sent!");
                    //System.out.println("disconnected with Server!");
                    client.close();
                    break;
                }
                if(n == 4)
                	new Popup(new JFrame(), 5);
            }
            new Popup(new JFrame(), 5);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public boolean isConnected() {
        try {
            client.sendUrgentData(0xFF);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

