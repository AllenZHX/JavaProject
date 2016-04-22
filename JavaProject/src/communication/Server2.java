package communication;

import java.net.*;
import java.io.*;
 
public class Server2 {
 
    private ServerSocket server;
    private Socket client;
 
    public Server2() {
        boolean flag = true;
        try {
            server = new ServerSocket(8888);    
            System.out.println("Waiting for Client to connected!");
            while (flag) {
                client = server.accept();  
                new ServerThread(client).start();
            }
            server.close();
        } catch (Exception e) {
        }
    }
 
    public static void main(String[] args) {
        new Server2();
    }
 
    private class ServerThread extends Thread {
 
        private Socket clientThread;
        private PrintStream out;
        private BufferedReader in;
        private boolean flag = true;
 
        public ServerThread(Socket client) {
            this.clientThread = client;
            System.out.println("Sucessfully connected with Client!");
        }
 
        public void run() {
            try {
                
                clientThread.setSoTimeout(300000);
                out = new PrintStream(client.getOutputStream());
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                while (flag) {
                    String str = in.readLine(); 
                    System.out.println("Client2:" + str);
                    out.println("Successfully received the info!");
                }
                client.close();
            } catch (Exception e) {
                if (e.getMessage() == "Connection reset") {
                    System.out.println("disconected!");
                }
            }
        }
    }
}
