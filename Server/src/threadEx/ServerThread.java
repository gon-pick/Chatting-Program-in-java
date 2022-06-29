package threadEx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;

import main.MainClass;
import view.Room;
import view.User;

public class ServerThread extends Thread {
	Socket socket;
	List<Socket> list;
	
	public ServerThread(Socket socket, List<Socket> list) {
		this.socket = socket;
		this.list = list;
	}

	@Override
	public void run() {		
		super.run();
		
		try {
			
			while(true) {
				
				// 수신(recv)
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String str = reader.readLine();					
				System.out.println("client로부터 받은 메시지:" + str);
				String[] spl = str.split("-"); // 방금 생성한 방
				String sendroom = "";
				
				if(spl[0].equals("ckidpw")) {
					for(int i=0; i<MainClass.userlist.size(); i++) {
						sendroom += MainClass.userlist.get(i).getId() + "-";
						sendroom += MainClass.userlist.get(i).getPw() + "-";
					}
					System.out.println(sendroom);
					
					for(Socket s : list) {
						System.out.println(sendroom);
						PrintWriter wr = new PrintWriter(s.getOutputStream());
						wr.println("ckidpw"+"-"+sendroom);
						wr.flush();	
					}
				}
				else if(spl[0].equals("loginCheck")) {
					for(int i=0; i<MainClass.userlist.size(); i++) {
						sendroom += MainClass.userlist.get(i).getId() + "-";
						sendroom += MainClass.userlist.get(i).getPw() + "-";
					}
					System.out.println(sendroom);
					
					for(Socket s : list) {
						System.out.println(sendroom);
						PrintWriter wr = new PrintWriter(s.getOutputStream());
						wr.println("loginCheck"+"-"+sendroom);
						wr.flush();	
					}
				}
				else if(spl[0].equals("idpw")) {
					User user = new User(spl[1],spl[2]);
					MainClass.userlist.add(user);
					
					for(int i=0; i<MainClass.userlist.size(); i++) {
						sendroom += MainClass.userlist.get(i).getId() + "-";
						sendroom += MainClass.userlist.get(i).getPw() + "-";
					}
					System.out.println(sendroom);
					
					for(Socket s : list) {
						System.out.println(sendroom);
						PrintWriter wr = new PrintWriter(s.getOutputStream());
						wr.println("idpw"+"-"+sendroom);
						wr.flush();	
					}
				}
				else if(spl[0].equals("rooms") && MainClass.roomlist.size() < 1) {
					//로그인 -> 룸 관리자 화면 전체 유저 중 첫 로그인.
					for(Socket s : list) {
						PrintWriter wr = new PrintWriter(s.getOutputStream());
						wr.println("rooms");
						wr.flush();	
					}
				}
				else if((spl[0].equals("rooms") && MainClass.roomlist.size() >= 1)) {
					//로그인 -> 룸 관리자 화면 전환 시 다른 사람 생성한 방을 초기화.
					
					for(int i=0; i<MainClass.roomlist.size(); i++) {
						sendroom += MainClass.roomlist.get(i).getName() + "-";
					}
					
					for(Socket s : list) {
						System.out.println(sendroom);
						PrintWriter wr = new PrintWriter(s.getOutputStream());
						wr.println("room"+"-"+sendroom);
						wr.flush();	
					}
				}
				else if(spl[0].equals("room")) {
					//방 생성 요청이 왔을 경우.
					Room rm = new Room(MainClass.roomlist.size(),spl[1]);
					MainClass.roomlist.add(rm);
					for(int i=0; i<MainClass.roomlist.size(); i++) {
						sendroom += MainClass.roomlist.get(i).getName() + "-";
					}
					
					for(Socket s : list) {
						System.out.println(sendroom);
						PrintWriter wr = new PrintWriter(s.getOutputStream());
						wr.println("room"+"-"+sendroom);
						wr.flush();	
					}
				}
				else if(spl[0].equals("roomenter")) {
					//Room에 입장했을 경우 ID를 키값으로 해서 PORT 번호랑 같이 넣는다.
					MainClass.roomlist.get(Integer.parseInt(spl[1])).users.put(socket.getPort(),spl[2]);
				}
				else if(spl[0].equals("send")){
					for (Socket s : list) {
						System.out.println("send : " + s.getPort() + "내용 : " + "spl[2]");
						if(s != socket &&MainClass.roomlist.get(Integer.parseInt(spl[1])).users.containsKey(s.getPort())) {
							PrintWriter writer = new PrintWriter(s.getOutputStream());
							writer.println("send" + "-" + spl[2]);
							writer.flush();
						}
					}
				}
				else if(spl[0].equals("exit")) {
					MainClass.roomlist.get(Integer.parseInt(spl[1])).users.remove(socket.getPort());
					for (Socket s : list) {
						if(s != socket &&MainClass.roomlist.get(Integer.parseInt(spl[1])).users.containsKey(s.getPort())) {
							PrintWriter writer = new PrintWriter(s.getOutputStream());
							writer.println("exit" + "-" + spl[2]);
							writer.flush();
						}
					}
				}
				else if(spl[0].equals("login")) {
					if(MainClass.loginCheck.contains(spl[1])) {
						PrintWriter writer = new PrintWriter(socket.getOutputStream());
						writer.println("login-1");
						writer.flush();
					}else {
						MainClass.loginCheck.add(spl[1]);
						PrintWriter writer = new PrintWriter(socket.getOutputStream());
						writer.println("login-0");
						writer.flush();
					}
				}
				else if(spl[0].equals("logout")) {
					if(MainClass.loginCheck.contains(spl[1])) MainClass.loginCheck.remove(spl[1]);
				}
				
				Thread.sleep(300);				
			}
		
		} catch (Exception e) {			
			System.out.println("연결이 끊긴 IP:" + socket.getInetAddress() + " Post:" + socket.getInetAddress());
			list.remove(socket);
			
			// 접속되어 있는 남아있는 클라이언트 출력
			for (Socket s : list) {
				System.out.println("접속되어 있는 IP:" + s.getInetAddress()
							+ " Post:" + s.getPort());
			}
			
			try {
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 	
	}
}
