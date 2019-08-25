package com.bar.coupons.beans;

import org.springframework.stereotype.Repository;

import com.bar.coupons.enums.ClientType;

@Repository
public class User {
	
	private long userID;
	private String userEmail;
	private String password;
	private ClientType type;
	private Long companyID;
	
	
	public User(String userEmail, String password, ClientType type, Long companyID) {
		this(userEmail,password,type);
		this.companyID = companyID;
	}


	public User(long userID) {
		this.userID = userID;
	}
	
	

	public User(String userEmail, String password, ClientType type) {
		this.userEmail = userEmail;
		this.password = password;
		this.type = type;
//		this.companyID = 0L;
	}


	public User() {
	}


	public long getUserID() {
		return userID;
	}


	public void setUserID(long userID) {
		this.userID = userID;
	}


	public String getUserEmail() {
		return userEmail;
	}


	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public ClientType getType() {
		return type;
	}


	public void setType(ClientType type) {
		this.type = type;
	}


	public Long getCompanyID() {
		return companyID;
	}


	public void setCompanyID(Long companyID) {
		this.companyID = companyID;
	}


	@Override
	public String toString() {
		return "User [userID=" + userID + ", userEmail=" + userEmail + ", password=" + password + ", type=" + type
				+ ", companyID=" + companyID + "]";
	}

	
	

}
