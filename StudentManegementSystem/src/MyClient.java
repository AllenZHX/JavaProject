import java.io.*;  
import java.net.*;  
/**  
 * @author hnyer  
 *  
 */ 
public class Client {  
    public static void main(String[] args) throws Exception, IOException {  
        Socket client=new Socket("127.0.0.1",9000);//访问其他电脑时,ip地址改成相应的。  
        System.out.println("连接服务端成功！");  
        System.out.println("请在控制台输入信息(bye表示结束对话)：");  
        BufferedReader br1;  
        BufferedReader br2=new BufferedReader(new InputStreamReader(client.getInputStream()));//从服务端获得输入流  
        BufferedWriter bw1=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));//发给服务器  
        String line=null;  
        String line2=null;  
        while(true){  
            br1=new BufferedReader(new InputStreamReader(System.in));//控制台输入流  
            line=br1.readLine();  
            if(line==null){break;}  
            bw1.write(line);  
            bw1.newLine();  
            bw1.flush();  
            if("bye".equalsIgnoreCase(line)){  
                System.out.println("对话结束！谢谢使用");  
                break;  
            }  
            line2=br2.readLine();//接收服务器的反馈  
            System.out.println("服务器说："+line2);//显示服务器的消息  
        }//while  
    }  
}  
