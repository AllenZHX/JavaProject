import java.io.*;  
import java.net.*;  
/**  
 * @author hnyer  
 *  
 */ 
public class Client {  
    public static void main(String[] args) throws Exception, IOException {  
        Socket client=new Socket("127.0.0.1",9000);//������������ʱ,ip��ַ�ĳ���Ӧ�ġ�  
        System.out.println("���ӷ���˳ɹ���");  
        System.out.println("���ڿ���̨������Ϣ(bye��ʾ�����Ի�)��");  
        BufferedReader br1;  
        BufferedReader br2=new BufferedReader(new InputStreamReader(client.getInputStream()));//�ӷ���˻��������  
        BufferedWriter bw1=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));//����������  
        String line=null;  
        String line2=null;  
        while(true){  
            br1=new BufferedReader(new InputStreamReader(System.in));//����̨������  
            line=br1.readLine();  
            if(line==null){break;}  
            bw1.write(line);  
            bw1.newLine();  
            bw1.flush();  
            if("bye".equalsIgnoreCase(line)){  
                System.out.println("�Ի�������ллʹ��");  
                break;  
            }  
            line2=br2.readLine();//���շ������ķ���  
            System.out.println("������˵��"+line2);//��ʾ����������Ϣ  
        }//while  
    }  
}  