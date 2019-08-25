package com.bar.coupons.beans;

import org.springframework.stereotype.Repository;

@Repository
public class Company {
	
	//---- Properties of a company --------
	
	private long companyID;
	private String compName;
	private String contactEmail;
	private String contactPhone;
	
	//---- Ctor' with all properties but without companyID  - useful for creating a new company in the database--------
	
	public Company(String compName, String contactEmail, String contactPhone) {
		this.compName = compName;
		this.contactEmail = contactEmail;
		this.contactPhone = contactPhone;
	}

	
	//---- Ctor' with all properties --------
	
	public Company(long companyID, String compName, String contactEmail, String contactPhone) {
		this(compName,contactEmail,contactPhone);
		this.companyID = companyID;
	}

	//----Deafault Ctor'  --------
	
	public Company() {
		
	}


	public long getCompanyID() {
		return companyID;
	}


	public void setCompanyID(long companyID) {
		this.companyID = companyID;
	}


	public String getCompName() {
		return compName;
	}


	public void setCompName(String compName) {
		this.compName = compName;
	}


	public String getContactEmail() {
		return contactEmail;
	}


	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}


	public String getContactPhone() {
		return contactPhone;
	}


	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}


	@Override
	public String toString() {
		return "Company [Company ID=" + companyID + ", Name=" + compName + ", contact email=" + contactEmail
				+ ", contact phone=" + contactPhone + "]";
	}
	
		
	
}
