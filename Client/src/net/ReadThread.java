package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import view.ClientFrame;
import view.IdFrame;
import view.RoomAdmin;

public class ReadThread extends Thread{

	Socket socket;
	RoomAdmin ra;
	
	public ReadThread(Socket socket, RoomAdmin ra) {
		this.socket = socket;
		this.ra = ra;
	}

	@Override
	public void run() {		
		super.run();
		try {
			
			while(true) {			
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				String str = br.readLine();
				if(str == null) {
					System.out.println("접속끊김");
				}
				
				String[] spl = str.split("-");
				
				if(spl[0].equals("ckidpw")) {
					IdFrame.loginmap.clear();
					for(int i=1;i<spl.length;i=i+2) {
						IdFrame.loginmap.put(spl[i], spl[i+1]);
					}
				}
				else if(spl[0].equals("idpw")) {
					IdFrame.loginmap.clear();
					for(int i=1;i<spl.length;i=i+2) {
						IdFrame.loginmap.put(spl[i], spl[i+1]);
					}
				}
				else if(spl[0].equals("rooms") && spl.length == 1) {
					//데이터가 없는 로그인 -> 룸 관리자 화면
					ra.roomlist.clear();
					ra.initJRadio();
				}
				else if(spl[0].equals("room") || (spl[0].equals("rooms") && spl.length > 1)) {
					ra.roomlist.clear();
					for(int i=1; i<spl.length; i++) {
						ra.roomlist.add(spl[i]);
					}
					ra.initJRadio();
				}
				else if(spl[0].equals("send")) {
					ClientFrame.textA.append(spl[1] + "\n");
				}
				else if(spl[0].equals("exit")) {
					ClientFrame.textA.append(spl[1] + "\n");
				}
				else if(spl[0].equals("login")) {
					IdFrame.tmp.setText(spl[1]);
				}
				
				Thread.sleep(300);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	
}





