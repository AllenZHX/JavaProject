import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;



public class MyClient extends JFrame {
	PrintWriter writer;
	Container c;
	Socket socket;
	JTextArea ta=new JTextArea();
	JTextField tf=new JTextField();
	
	
	public MyClient(String title){
		super(title);
		c=getContentPane();
		JScrollPane jsp=new JScrollPane();
		c.add(jsp, BorderLayout.CENTER);
		jsp.setViewportView(ta);
		c.add(tf, BorderLayout.SOUTH);
		
		tf.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				writer.println(tf.getText());
				ta.append(tf.getText()+"\n");
				ta.setSelectionEnd(ta.getText().length());
				tf.setText("");
			}
		});
		
		
		setVisible(true);
		setSize(800,600);
		
	}
	
	public void connect() throws Exception{
		ta.append("try to connect!\n");
		socket =new Socket("127.0.0.1",9008);
		writer =new PrintWriter(socket.getOutputStream(),true);
		ta.append("Finish Connectting!\n");
	}
	
	
	public static void main(String[] args) throws Exception{
		MyClient client=new MyClient("send data to server");
		client.connect();
		
	}
}
