import java.io.*;
import java.net.*;


public class MyTcp{
	private ServerSocket server;
	public Socket socket;
	private BufferedReader reader;
	
	void getServer() throws Exception{
		server=new ServerSocket(9008);
		System.out.println("server socket has been created");
		
		while(true){
			System.out.println("Waiting for client to connect...");
			socket=server.accept();
			reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			getClientMessage();
			
		}
	}
	
	void getClientMessage() throws Exception{
		while(true){
			System.out.println("Client says:"+reader.readLine());
		}
	}
	
	
	
	
	public static void main(String[] args) throws Exception{
		MyTcp tcp=new MyTcp();
		tcp.getServer();
	}
}