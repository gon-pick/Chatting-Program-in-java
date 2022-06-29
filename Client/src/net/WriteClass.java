package net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import view.ClientFrame;
import view.IdFrame;
import view.RoomAdmin;

public class WriteClass {

	Socket socket;
	
	public WriteClass(Socket socket) {		
		this.socket = socket;
	}
	
	public void roomEnter(String roomindex, String id) {
		
		try {
			
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			String msg ="roomenter" +"-"+roomindex+"-"+id;				
			
			// server로 전송
			pw.println(msg);
			pw.flush();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void sendMessage(boolean isFirst,String roomindex) {
		
		try {
			
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			String msg = "";
			String id = IdFrame.tf.getText();
			
			// 첫번째 전송
			if(isFirst) {
				InetAddress iaddr = socket.getLocalAddress();
				int port = socket.getPort();
				String ip = iaddr.getHostAddress();	// xxx.xxx.xxx.xxx					
				msg = "send" + "-" + roomindex + "-" + "[" + id + "]님 로그인(" + ip + " : " + port + ")" ;
			}			
			// 그외 전송
			else {				
				msg = "send" + "-" + roomindex + "-" + "[" + id + "] : " + ClientFrame.textF.getText();
			}
			
			// server로 전송
			pw.println(msg);
			pw.flush();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	
	
	public void sendNameMessage(String name) {
		
		try {
			
			PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
			String names = name;
			
			// server로 전송
			pw.println(names);
			pw.flush();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}







