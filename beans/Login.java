package com.bar.coupons.beans;

public class Login {

	// property

	private String userName;
	private String password;

	// constructor

	public Login(String userName, String password) {
		this();
		this.userName = userName;
		this.password = password;
	}

	public Login() {
		super();
	}

	// getter & setter

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Login [userName=" + userName + ", password=" + password + "]";
	}

}
