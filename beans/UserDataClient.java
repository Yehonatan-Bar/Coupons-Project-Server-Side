package com.bar.coupons.beans;

import com.bar.coupons.enums.ClientType;

public class UserDataClient {

	// property

	private ClientType clientType;
	private long id;
	private long companyID;
	private int token;
	

	// constructor

	public UserDataClient() {
		super();
	}

	// getter & setter

	
	
	
	public ClientType getClientType() {
		return clientType;
	}

	public long getCompanyID() {
		return companyID;
	}

	public void setCompanyID(long companyID) {
		this.companyID = companyID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	public int getToken() {
		return token;
	}

	public void setToken(int token) {
		this.token = token;
	}

}
