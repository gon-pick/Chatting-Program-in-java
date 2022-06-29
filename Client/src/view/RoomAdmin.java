package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import net.ReadThread;
import net.WriteClass;

public class RoomAdmin extends JFrame implements WindowListener, ActionListener{

	Socket socket;
	WriteClass wc;
	
	public static TextField roomtext = new TextField(160);
	public static ArrayList<String> roomlist = new ArrayList<>();
	
	JButton btn = new JButton("Room Making");
	JButton btn1 = new JButton("Room Enter");
	
	ImageIcon icon;
	Image img;
	Image changeImage;
	ImageIcon changeIcon;
	
	ArrayList<JRadioButton> rb = new ArrayList<>();
	ButtonGroup bgroup = new ButtonGroup();
	JPanel bPanel = new JPanel();
	Box box1 = Box.createVerticalBox();
	
	public RoomAdmin(Socket socket) {
		this.socket = socket;
		
		wc = new WriteClass(socket);
		new IdFrame(wc,this);
		
		setTitle("RoomAdmin");
		setLayout(null);
		setSize(1280, 800);
		setLocationRelativeTo(null);
		
		JLabel title = new JLabel("Cat Chatting Room ");
		title.setBounds(400, 100, 500, 100);
		title.setForeground(Color.GREEN);
		Font font = new Font("맑은 고딕",Font.BOLD,50);
		title.setFont(font);
		add(title);
		
		JLabel password = new JLabel("방제목 : ");
		password.setBounds(320, 230, 50, 30);
		add(password);
		
		roomtext.setBounds(380, 230, 200, 30);
		add(roomtext);
		
		btn.setBounds(320, 280, 120, 30);
		btn.addActionListener(this);
		add(btn);
		
		btn1.setBounds(480,280,120,30);
		btn1.addActionListener(this);
		add(btn1);
		
		icon = new ImageIcon("C:\\01.Client\\GUIClient\\src\\image\\background.jpg");
		img = icon.getImage();
		changeImage = img.getScaledInstance(1280, 800, Image.SCALE_SMOOTH);
		changeIcon = new ImageIcon(changeImage);
		JLabel lbl = new JLabel(changeIcon);
		lbl.setBounds(0, 0, 1100, 800);
		add(lbl);
		addWindowListener(this);
		setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == btn) {
			if(roomtext.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "방이름이 비어있습니다.", "Room Making", JOptionPane.WARNING_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null, "방 생성이 완료됐습니다.", "Room Making", JOptionPane.INFORMATION_MESSAGE);

				roomlist.add(roomtext.getText());				
				wc.sendNameMessage("room" + "-" + roomtext.getText());			
			}
			
		}else if(obj == btn1) {
			int flag = 0;
			
			for(int i=0; i<roomlist.size(); i++) {
				if(rb.get(i).isSelected()) {
					enterRoom(rb.get(i).getText(),i+"",IdFrame.tf.getText());
					flag = 1;
					break;
				}
			}
			
			if(flag == 0) {
				JOptionPane.showMessageDialog(null, "방을 선택하세요.", "Room Making", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	public void initJRadio() {
		rb.clear();
		bgroup.clearSelection();
		box1.removeAll();
		bPanel.removeAll();
		
		if(roomlist.size() > 0) {
			for(int i=0; i<roomlist.size(); i++) {
				JRadioButton jrb = new JRadioButton();
				jrb.setText(roomlist.get(i));
				rb.add(jrb);
				bgroup.add(rb.get(i));
				box1.add(rb.get(i));
			}	
		}
		
		bPanel.add(box1);
		bPanel.setBounds(1100,0,180,Integer.MAX_VALUE);
		add(bPanel);
		
		this.revalidate();
		this.repaint();
	}
	
	public void enterRoom(String room, String roomindex, String id) {
		// id 전송
		ClientFrame cf = new ClientFrame(socket, this);
		//방 처음 입장.
		wc.roomEnter(roomindex, id);
		
		// ClientFrame 을 시각화
		cf.setTitle(room);
		cf.buffer.setText(roomindex);
		setVisible(false);
		cf.setVisible(true);
		
		// 첫번째 전송
		wc.sendMessage(cf.isFirst,roomindex);
		cf.isFirst = false;
		
		// 현재창 close
		this.dispose();
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
