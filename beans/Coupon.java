package com.bar.coupons.beans;



import java.util.Date;

import org.springframework.stereotype.Repository;

import com.bar.coupons.enums.Category;

@Repository
public class Coupon {
	
	private long couponID;
	private long companyID;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private Category category;
	private String description;
	private double price;
	private String image;

	public Coupon() {}

	
	public Coupon(long companyID, String title, Date startDate, Date endDate, int amount, Category category,
			String description, double price, String image) {
		super();
		this.companyID = companyID;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.category = category;
		this.description = description;
		this.price = price;
		this.image = image;
	}


	public Coupon(long couponID, long companyID, String title, Date startDate, Date endDate, int amount,
			Category category, String description, double price, String image) {

		this(companyID,title,startDate,endDate,amount,category,description,price,image);
		this.couponID = couponID;
	}


	public long getCouponID() {
		return couponID;
	}

	public void setCouponID(long couponID) {
		this.couponID = couponID;
	}

	public long getCompanyID() {
		return companyID;
	}

	public void setCompanyID(long companyID) {
		this.companyID = companyID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date date) {
		this.startDate = date;
	}
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Coupon [couponID=" + couponID + ", companyID=" + companyID + ", title=" + title + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", amount=" + amount + ", category=" + category + ", description="
				+ description + ", price=" + price + ", image=" + image + "]";
	}

	
	
}
