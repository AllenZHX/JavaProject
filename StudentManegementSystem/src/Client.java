import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;


public class Client extends JFrame{
	private PrintWriter printWriter;
	private JTextArea jta;
	private JScrollPane jsp;
	private Socket socket;
	
	
	public Client(){
		Container c=getContentPane();
		c.setBackground(Color.RED);
		
		JTextField jtf=new JTextField();
		jtf.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				printWriter.println(jtf.getText());
				jta.append(jtf.getText()+"\n");
				jta.setSelectionEnd(jta.getText().length());
				jtf.setText("");
			}
		});
		c.add(jtf, BorderLayout.SOUTH);
		
		jta=new JTextArea();
		
		jsp=new JScrollPane(jta);
		c.add(jsp, BorderLayout.CENTER);
		
		setSize(600,400);
		setTitle("send message to server");
		setVisible(true);
	}

	
	
	
	
	public void connect() throws Exception{
		jta.append("Trying to connect...\n");
		socket=new Socket("Xiang-Cao",8087);
		printWriter=new PrintWriter(socket.getOutputStream(),true);
		jta.append("Connected to server!");
		
	}
	
	
	
	public static void main(String[] args) throws Exception{
		Client client=new Client();
		client.connect();
	}
}