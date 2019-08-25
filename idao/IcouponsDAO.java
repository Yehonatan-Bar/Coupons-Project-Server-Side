package com.bar.coupons.idao;

import java.util.Collection;

import com.bar.coupons.beans.Coupon;
import com.bar.coupons.enums.Category;
import com.bar.coupons.exceptions.CouponsProjectExceptions;

public interface IcouponsDAO {

	long createCoupon(Coupon coupon) throws CouponsProjectExceptions;

	void deleteCoupon(long couponID) throws CouponsProjectExceptions;

	void updateCoupon(Coupon coupon) throws CouponsProjectExceptions;

	Coupon getCoupon(long couponID) throws CouponsProjectExceptions;

	Collection<Coupon> getAllCouponsByCompanyId(long companyID) throws CouponsProjectExceptions;

	Collection<Coupon> getCouponsByCustomerId(long companyID) throws CouponsProjectExceptions;

	Collection<Coupon> getCustomerCouponsByCategory(long customerId, Category category) throws CouponsProjectExceptions;

	Collection<Coupon> getCustomerCouponsByMaxPrice(long customerId, double maxPrice) throws CouponsProjectExceptions;

	Collection<Coupon> getAllCouponsByCategory(Category category) throws CouponsProjectExceptions;

	Collection<Coupon> getCompanyCouponsByCategory(long companyId, Category category) throws CouponsProjectExceptions;

	Collection<Coupon> getCouponsByMaxPrice(double maxPrice) throws CouponsProjectExceptions;

	Collection<Coupon> getCouponsByMaxPriceAndCompany(long companyId, double maxPrice) throws CouponsProjectExceptions;

	Collection<Coupon> getAllCoupons() throws CouponsProjectExceptions;

	boolean isCouponExists(long couponId) throws CouponsProjectExceptions;

	boolean isCouponsByCompanyIdExist(long customerID) throws CouponsProjectExceptions;

	boolean isCouponsExistByTitle(String couponEmail) throws CouponsProjectExceptions;
	
	void deleteExpiredCoupon() throws CouponsProjectExceptions;
//	boolean isCouponsByCustomerIdExist(long customerID) throws CouponsProjectExceptions;

}