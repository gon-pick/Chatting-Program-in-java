package main;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import net.ReadThread;
import view.ClientFrame;
import view.RoomAdmin;


public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			Socket socket = new Socket("172.31.96.1", 9000);
			System.out.println("connetion success!!");
			
			RoomAdmin ra = new RoomAdmin(socket);
			new ReadThread(socket, ra).start();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
