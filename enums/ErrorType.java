package com.bar.coupons.enums;
/**
 * This class create an error by names
 */
public enum ErrorType {
	
	GENERAL_ERROR(600,"General error."),
	PROBLEM(600,"Have a problem:\n"),
	COMPANY_IS_ALREADY_EXISTS(600,"The company you chose is already exist."),
	NEW_COMPANY_CANT_HAVE_ID(600,"New company cant have id. The id is generated automatically."),
	COMPANY_IS_NOT_EXISTS(600,"The company you chose isn't exist."),
	COMPANY_HAVE_NO_COUPONS(600,"The company you chose have no coupons."),
	COMPANY_HAVE_NO_COUPONS_IN_CATEGORY(600,"The company you chose have no coupons in this category."),
	NO_COUPONS_IN_THIS_CATEGORY(600,"No coupons in this category."),
	CUSTOMER_IS_ALREADY_EXISTS(600,"The customer you chose is already exist."),
	CUSTOMER_IS_NOT_EXISTS(600,"The customer you chose isn't exist."),
	EMAIL_IS_NOT_EXISTS(600,"The customer's email is not exist in the users list or not compatible with user's email"),
	USER_IS_ALREADY_EXISTS(600,"The user you chose is already exist."),
	USER_IS_NOT_EXISTS(600,"The user you chose isn't exist."),
	USER_IS_CUSTOMER(600,"The user is a customer and can't have a company ID."),
	USER_IS__MUST_BE_CUSTOMER(600,"The user is a company."),
	USER_IS_COMPNAY(600,"The user is a company and must have a company ID."),
	COUPON_IS_ALREADY_EXISTS(600,"The coupon you chose is already exist."),
	COUPON_IS_NOT_EXISTS(600,"The coupon you chose isn't exist."),
	ALL_COUPONS_MORE_EXPENSIVE(600,"There are no coupons at or below this price"),
	ALL_COUPONS_MORE_EXPENSIVE_BY_CONPANY(600,"There are no coupons at or below this price for this company"),
	PURCHASE_IS_ALREADY_EXISTS(600,"The purchase you chose is already exist."),
	PURCHASE_IS_NOT_EXISTS(600,"The purchase you chose is not exist."),
	EMPTY(600,"The input is empty"),
	EMPTY_USER_INPUT(600,"The user input is empty"),
	INVALID_ID(600,"The ID you've enter is invalid."),
	INVALID_ID_UNCOMPATIBLE(600,"The custumer's ID is not compatible with user's id"),
	COMPANY_ID_UNCOMPATIBLE(600,"The company id cannot be updated"),
	INVALID_CATEGORY(600,"The category you've enter is invalid."),
	INVALID_IMAGE_URL(600,"The image url is invalid."),
	INVALID_TYPE(600,"The type you've enter is invalid."),
	INVALID_NAME(600,"The name you've enter is invalid."),
	INVALID_AMOUNT(600,"The amount you've entered is invalid."),
	INVALID_PRICE(600,"The price you've entered is invalid."),
	INVALID_PHONE_NUMBER(600,"The phone number you've entered is invalid."),
	INVALID_EMAIL(600,"The email you've entered is invalid."),
	INVALID_PASSWORD(600,"The password you've entered is invalid. Please try again. Password policy: \n	    At least 8 chars\r\n" + 
			"	\r\n" + 
			"	    Contains at least one digit\r\n" + 
			"	\r\n" + 
			"	    Contains at least one lower alpha char and one upper alpha char\r\n" + 
			"	\r\n" + 
			"	    Contains at least one char within a set of special chars (@#%$^ etc.)\r\n" + 
			"	\r\n" + 
			"	    Does not contain space, tab, etc."),
	INVALID_DATES(600,"The dates you've entered is invalid."),
	
	FIELD_IS_IRREPLACEABLE(600,"You can't change this field."),
	NAME_IS_IRREPLACEABLE(600,"You can't change your name."),
	COUPON_IS_OUT_OF_ORDER(600,"Coupon is out of order."),
	COUPON_EXPIRED(600,"Coupon's date expired."),
	AMOUNT_REQUIRED_MORE_THAN_AVAILABLE(600,"The required coupon amount is bigger than the available coupon amount."),
	LOGIN_FAILED(600,"Login failed. credentials is incorrect, Please try again.");

	private String internalMessage;
	private int internalErrorCode;

	// constructor
	private ErrorType(int internalErrorCode, String internalMessage) {
		this.internalErrorCode=internalErrorCode;
		this.internalMessage=internalMessage;
	}

	// getter
	
	public String getMessage() {
		return internalMessage;
	}
	
	public int getInternalErrorCode() {
		return internalErrorCode;
	}

}
