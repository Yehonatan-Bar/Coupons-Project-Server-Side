package com.bar.coupons.beans;

import org.springframework.stereotype.Repository;

@Repository
public class Customer {
	
	private String custEmail;
	private long custId;
	private String custPhone;
	private String custName;
	private User user;
	
	public Customer() {
		
	}
	
	public Customer(String custEmail, long custId) {
		super();
		this.custEmail = custEmail;
		this.custId = custId;
	}
	
	public Customer(String custEmail, long custId, String custPhone, String custName, User user) {
		super();
		this.custEmail = custEmail;
		this.custId = custId;
		this.custPhone = custPhone;
		this.custName = custName;
		this.user = user;
	}

	public Customer(String custEmail, String custPhone, String custName, User user) {
		super();
		this.custEmail = custEmail;
		this.custPhone = custPhone;
		this.custName = custName;
		this.user = user;
	}

	public Customer(String custEmail) {
		super();
		this.custEmail = custEmail;
	}
	
	public Customer(String custEmail, User user) {
		super();
		this.custEmail = custEmail;
		this.user = user;
	}

	public String getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}


	public String getCustEmail() {
		return custEmail;
	}

	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}

	public long getCustId() {
		return custId;
	}
	
	public void setCustId(long custId) {
		this.custId = custId;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return  "Customer: [customerID=" + custId + ", custName=" + custName + ", contactEmail=" + custEmail
				+ ", contactPhone=" + custPhone + "]";
	}

	
}
