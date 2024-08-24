package modelos;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Usuario {

	private int userId = -1;
	private String userName = "null";
	private String namePC = "null";


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getNamePC() {
		return namePC;
	}


	public void setNamePC(String namePC) {
		this.namePC = namePC;
	}


	public static String nameComputer() {

		String name = "null";
		try {
			InetAddress identifPC = InetAddress.getLocalHost();
			name = identifPC.toString();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return name;
	}


	public Usuario(int userId, String userName) {

		this.userId = userId;
		this.userName = userName;
		this.namePC = nameComputer();
	}

}


