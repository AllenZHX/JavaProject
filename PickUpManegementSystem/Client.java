import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
  
public class Client {
    public static void main(String[] args) {
        try {
//          Socket socket =new Socket("10.0.0.165",8000);
//        	Socket socket=new Socket("155.246.219.148",8000);
         	Socket socket=new Socket("155.246.161.85",8000);
            socket.setSoTimeout(60000);
  
            PrintWriter printWriter =new PrintWriter(socket.getOutputStream(),true);
            BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(socket.getInputStream()));
              
            String result ="";
            while(result.indexOf("bye") == -1){
                BufferedReader sysBuff =new BufferedReader(new InputStreamReader(System.in));
                printWriter.println(sysBuff.readLine());
                printWriter.flush();
                  
                result = bufferedReader.readLine();
//                System.out.println("Server say : " + result);////////////////
                System.out.println(result);
            } 
  
            printWriter.close();
            bufferedReader.close();
            socket.close();
        }catch (Exception e) {
            System.out.println("Exception:" + e);
        }
    }
}