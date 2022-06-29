package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import net.WriteClass;

// ID 입력받기 위한 form
public class IdFrame extends JFrame implements WindowListener, ActionListener{

	public static TextField tf = new TextField(160);
	public static TextField tf1 = new TextField(160);
	
	public static Map<String, String> loginmap = new HashMap<>();

	public static JTextArea tmp = new JTextArea("0");
	JButton btn = new JButton("Login");
	JButton btn1 = new JButton("Sing up");

	
	ImageIcon icon;
	Image img;
	Image changeImage;
	ImageIcon changeIcon;
	
	// WriteClass 추가
	WriteClass wc;
	RoomAdmin ra;
	
	public IdFrame(WriteClass wc, RoomAdmin ra) {
		
		this.ra = ra;
		this.wc = wc;
		
		setTitle("login & SignUp");
		setLayout(null);
		setSize(1280, 800);
		setLocationRelativeTo(null);		
		
		JLabel title = new JLabel("Cat Chatting Room ");
		title.setBounds(400, 100, 500, 100);
		title.setForeground(Color.GREEN);
		Font font = new Font("맑은 고딕",Font.BOLD,50);
		title.setFont(font);
		add(title);
		
		JLabel label = new JLabel("ID : ");
		label.setBounds(480, 250, 40, 30);
		add(label);
		
		tf.setBounds(520, 250, 200, 30);
		add(tf);
		
		JLabel password = new JLabel("PW : ");
		password.setBounds(480, 280, 40, 30);
		add(password);
		
		tf1.setBounds(520, 280, 200, 30);
		tf1.setEchoChar('*');
		add(tf1);
		
		btn.setBounds(480, 330, 120, 30);
		btn.addActionListener(this);
		add(btn);
		
		btn1.setBounds(610,330,120,30);
		btn1.addActionListener(this);
		add(btn1);
		
		icon = new ImageIcon("C:\\01.Client\\GUIClient\\src\\image\\background.jpg");
		img = icon.getImage();
		changeImage = img.getScaledInstance(1280, 800, Image.SCALE_SMOOTH);
		changeIcon = new ImageIcon(changeImage);
		JLabel lbl = new JLabel(changeIcon);
		lbl.setBounds(0, 0, 1280, 800);
		add(lbl);
		wc.sendNameMessage("ckidpw");
		
		addWindowListener(this);
		setVisible(true);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj == btn) {
			if(tf.getText().trim().equals("") || tf1.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "ID or PW가 비어있습니다.", "ID&PW", JOptionPane.WARNING_MESSAGE);
			}else {
				if(loginmap.containsKey(tf.getText())) { //ID 일치
					if(loginmap.get(tf.getText()).equals(tf1.getText())) { //PW일치
						// ClientFrame 을 시각화
						wc.sendNameMessage("rooms");
						wc.sendNameMessage("login"+"-"+tf.getText());
						
						try {
							TimeUnit.MILLISECONDS.sleep(500);;
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						if(tmp.getText().equals("1")) {
							JOptionPane.showMessageDialog(null, "해당 ID는 로그인된 상태입니다. 관리자에게 연락하세요.", "중복 로그인", JOptionPane.WARNING_MESSAGE);
						}else {
							ra.setVisible(true);
							
							// 현재창 close
							this.dispose();
						}
					}else {
						JOptionPane.showMessageDialog(null, "비밀번호가 틀렸습니다. 확인부탁드립니다.", "비밀번호 오류", JOptionPane.WARNING_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null, "해당 ID가 없으니 회원가입 후 진행해주세요.", "회원가입 요청", JOptionPane.WARNING_MESSAGE);
				}
			}
			
		}else if(obj == btn1) {
			if(tf.getText().trim().equals("") || tf1.getText().trim().equals("")) {
				JOptionPane.showMessageDialog(null, "ID or PW가 비어있습니다.", "ID&PW", JOptionPane.WARNING_MESSAGE);
			}
			else {
				if(loginmap.containsKey(tf.getText())) {
					JOptionPane.showMessageDialog(null, "ID가 중복입니다. 다른 ID를 사용하세요.", "ID 중복창", JOptionPane.WARNING_MESSAGE);
				}else if(tf.getText().length() < 3 || tf1.getText().length() < 3) {
					JOptionPane.showMessageDialog(null, "ID와 비밀번호는 3자 이상만 가능합니다.", "비밀번호 조건", JOptionPane.WARNING_MESSAGE);
				}
				else {
					loginmap.put(tf.getText(), tf1.getText());
					wc.sendNameMessage("idpw"+"-"+tf.getText()+"-"+tf1.getText());
					JOptionPane.showMessageDialog(null, "회원가입이 완료됐습니다.", "회원가입", JOptionPane.INFORMATION_MESSAGE);
				}
			}			
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





