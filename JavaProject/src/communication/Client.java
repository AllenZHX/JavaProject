package communication;

import java.net.*;
import java.io.*;
 
public class Client {
 
    private Socket client;
 
    public Client() {
        try {
            System.out.println("Connecting to Server");
 
            client = new Socket("155.246.161.87", 8888);
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintStream out = new PrintStream(client.getOutputStream());;
            boolean flag = true;
 
            System.out.println("Successfully connected! Start communicating!");
 
            while (flag) {
                System.out.printf("please input info:");
                out.println(input.readLine()); 

                if (isConnected()) {
                    System.out.println("successfully sent!");
                    System.out.println("Server:" + in.readLine());
                } else {
                    System.out.println("Failed sent!");
                    System.out.println("disconnected with Server!");
                    client.close();
                    break;
                }
            }
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
 
    public static void main(String[] args) {
        new Client();
    }
}
