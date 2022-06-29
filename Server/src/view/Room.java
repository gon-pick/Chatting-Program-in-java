package view;

import java.util.HashMap;

public class Room{
	/**
	 * 
	 */
	private int key;
	private String name; //방 이름
	public HashMap<Integer,String> users = new HashMap<>();

	public Room(int key, String name) {
		super();
		this.key = key;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name+"-";
	}
	
	
	
}
