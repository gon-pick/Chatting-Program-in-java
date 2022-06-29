package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.HashSet;

import threadEx.ServerThread;
import view.Room;
import view.User;

public class MainClass {
	public static ArrayList<Room> roomlist = new ArrayList<>();
	public static ArrayList<User> userlist = new ArrayList<>();
	public static HashSet<String> loginCheck  = new HashSet<>();
	
	public static void main(String[] args) {
		
		Socket clientSocket = null;
		
		try {
			// 문지기 소켓
			ServerSocket serSocket = new ServerSocket(9000);
			// 버전확인, binding, listen
			
			List<Socket> list = new ArrayList<Socket>();
			StringBuilder sb = new StringBuilder();
			
			while(true) {
			
				System.out.println("접속 대기중...");
				clientSocket = serSocket.accept();
				list.add(clientSocket);
				
				// 접속 client 확인
				System.out.println("client IP:" + clientSocket.getInetAddress()
								+ " Port:" + clientSocket.getPort());
				
				new ServerThread(clientSocket, list).start();				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}






