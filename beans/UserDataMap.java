
package com.bar.coupons.beans;

public class UserDataMap {

	// property

	private long id;
	private Long companyID;

	// constructor

	public UserDataMap(long id, Long companyID) {
		this();
		this.id = id;
		this.companyID = companyID;
	}

	public UserDataMap() {
		super();
	}

	// getter & setter

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getCompanyID() {
		return companyID;
	}

	public void setCompanyID(Long companyID) {
		this.companyID = companyID;
	}

}
