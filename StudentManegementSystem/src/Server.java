import java.io.*;  
import java.net.*;  
/**  
 * @author hnyer  
 *  
 */ 
public class Server {  
    public static void main(String[] args) throws Exception {  
        ServerSocket ss=new ServerSocket(9001);//���Ŷ˿� 888  
        System.out.println("������������");  
        Socket s=ss.accept();//�ȴ��ͻ���������  
        System.out.println("�пͻ������ӳɹ���");  
        BufferedReader br2;  
        BufferedReader br1=new BufferedReader(new InputStreamReader(s.getInputStream()));//�ӿͻ��˻��������  
        BufferedWriter bw1=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));//���͸��ͻ��˵������  
        String line=null;  
        String line2=null;  
        while(true){  
            line=br1.readLine();  
            if(line==null){break;}  
            if("bye".equalsIgnoreCase(line)){  
                System.out.println("�Ի�������ллʹ��");  
                break;  
            }  
            System.out.println("�ͻ���˵��"+line);  
            br2=new BufferedReader(new InputStreamReader(System.in));//����̨������.  
            System.out.println("����ش�ͻ��˵���Ϣ:");  
            line2=br2.readLine();  
            bw1.write(line2);//����˻�Ӧ�ͻ���  
            bw1.newLine();  
            bw1.flush();  
              
        }  
    }  
}  