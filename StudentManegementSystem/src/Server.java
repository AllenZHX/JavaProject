import java.io.*;
import java.net.*;

public class Server{
	
	private ServerSocket serverSocket;
	private Socket socket;
	private BufferedReader bufferedReader;
	
	public void getServer() throws Exception{
		serverSocket=new ServerSocket(8087);
		System.out.println("server socket created!");
		
		while(true){
			System.out.println("Waiting client to connect...");
			socket=serverSocket.accept();
			bufferedReader=new BufferedReader( new InputStreamReader( socket.getInputStream()) );
			
			getClientMessage();
		}
	}
	
	public void getClientMessage() throws Exception{
		while(true){
			System.out.println("Client says:"+bufferedReader.readLine());
		}
	}
	
	public static void main(String[] args) throws Exception{

		InetAddress inetAddress=InetAddress.getLocalHost();
		String name=inetAddress.getHostName();
		String ip=inetAddress.getHostAddress();
		
		System.out.println(name);
		System.out.println(ip);
		
		Server server=new Server();
		server.getServer();
	}
}