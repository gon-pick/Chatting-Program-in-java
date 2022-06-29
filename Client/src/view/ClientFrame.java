package view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.WriteClass;

// 채팅 form
public class ClientFrame extends JFrame implements WindowListener, ActionListener{

	Socket socket;
	WriteClass wc;
	RoomAdmin ra;
	
	public static ArrayList<String> userlist = new ArrayList<>();
	public static JTextField textF = new JTextField(20);
	public static JTextArea textA = new JTextArea();
	public static JTextArea buffer = new JTextArea();
	
	JButton btnTransfer = new JButton("send");
	JButton btnExit = new JButton("exit");
	
	JPanel panel = new JPanel();
	
	public boolean isFirst = true;	// 첫번째 전송	
	
	public ClientFrame(Socket socket, RoomAdmin ra) {
		super("chatting");
		
		this.socket = socket;
		this.ra = ra;
		wc = new WriteClass(socket);
		
		
		JScrollPane scrPane = new JScrollPane(textA);
		scrPane.setPreferredSize(new Dimension(200, 120));
		
		add("Center", scrPane);
		
		panel.add(textF);
		panel.add(btnTransfer);
		panel.add(btnExit);
		
		add("South", panel);
		
		btnTransfer.addActionListener(this);
		btnExit.addActionListener(this);
		
		setBounds(200, 200, 450, 600);
		addWindowListener(this);
		
		setVisible(false);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == btnTransfer) {
			
			if(textF.getText().trim().equals("")) return;
			
			String id = IdFrame.tf.getText();
			
			textA.append("[" + id + "]" + textF.getText() + "\n");
			
			// server 전송
			wc.sendMessage(isFirst,buffer.getText());
			
			textF.setText("");			
		}else if(obj == btnExit) {
			
			wc.sendNameMessage("exit" + "-" + buffer.getText() + "-" + IdFrame.tf.getText() + "님이 퇴장했습니다.");
			textA.setText("");
			// ClientFrame 을 시각화;
			ra.initJRadio();
			// 현재창 close
			ra.setVisible(true);
			this.dispose();
		}
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		wc.sendNameMessage("logout"+"-"+IdFrame.tf.getText());
		System.out.println("windowClosing");
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}






